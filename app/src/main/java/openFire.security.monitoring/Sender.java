package openFire.security.monitoring;

import com.google.protobuf.Empty;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import iroha.protocol.Block;
import iroha.protocol.CommandServiceGrpc;
import iroha.protocol.Commands;
import iroha.protocol.Primitive;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sender {
    private static final Logger logger = Logger.getLogger(Sender.class.getName());

    private final ManagedChannel channel;
    private final CommandServiceGrpc.CommandServiceBlockingStub blockingStub;

    /**
     * Construct client connecting to HelloWorld server at {@code host:port}.
     */
    public Sender(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build());
    }

    private Sender(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = CommandServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void sendVerifierUpdate(String verifierId, String sensorId, Boolean sensorIsWorking, String status) throws StatusRuntimeException {
        logger.info("Will try to send verifier update...");
        Commands.Command command =
                Commands.Command.newBuilder().setTransferAsset(
                        Commands.TransferAsset.newBuilder()
                                .setSrcAccountId(verifierId)
                                .setDestAccountId(sensorId)
                                .setAssetId("checks#test")
                                .setDescription((sensorIsWorking ? "1" : "0") + status)
                                .setAmount(Primitive.Amount.newBuilder()
                                        .setValue(Primitive.uint256.newBuilder().setFirst(1).build())
                                        .setPrecision(0)
                                        .build())
                                .setAssetId("checks#test")
                                .build())
                        .build();
        Block.Transaction.Payload payload =
                Block.Transaction.Payload.newBuilder()
                        .setCreatedTime(System.currentTimeMillis())
                        .setCreatorAccountId(verifierId)
                        .setTxCounter(1)
                        .addCommands(command)
                        .build();
        Block.Transaction request = Block.Transaction.newBuilder().setPayload(payload).build();
        Empty response;
        response = blockingStub.torii(request);
    }

    public void sendSensorUpdate(String sensorId, String parameter, Object value) {
        logger.info("Will try to send sensor update...");

        long timestamp = System.currentTimeMillis();

        Commands.Command command =
                Commands.Command.newBuilder().setSetAccountDetail(
                        Commands.SetAccountDetail.newBuilder()
                                .setAccountId(sensorId)
                                .setKey(parameter)
                                .setValue(value.toString()))
                        .build();

        Block.Transaction.Payload payload =
                Block.Transaction.Payload.newBuilder()
                        .setCreatedTime(timestamp)
                        .setCreatorAccountId(sensorId)
                        .setTxCounter(1)
                        .addCommands(command)
                        .build();
        Block.Transaction request = Block.Transaction.newBuilder().setPayload(payload).build();
        Empty response;
        try {
            response = blockingStub.torii(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
        }
    }
}
