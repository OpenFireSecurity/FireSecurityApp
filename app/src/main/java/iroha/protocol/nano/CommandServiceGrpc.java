package iroha.protocol.nano;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

import java.io.IOException;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: endpoint.proto")
public final class CommandServiceGrpc {

  private CommandServiceGrpc() {}

  public static final String SERVICE_NAME = "iroha.protocol.CommandService";

  // Static method descriptors that strictly reflect the proto.
  private static final int ARG_IN_METHOD_TORII = 0;
  private static final int ARG_OUT_METHOD_TORII = 1;
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getToriiMethod()} instead.
  public static final io.grpc.MethodDescriptor<Block.Transaction,
      com.google.protobuf.nano.Empty> METHOD_TORII = getToriiMethodHelper();

  private static volatile io.grpc.MethodDescriptor<Block.Transaction,
      com.google.protobuf.nano.Empty> getToriiMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<Block.Transaction,
      com.google.protobuf.nano.Empty> getToriiMethod() {
    return getToriiMethodHelper();
  }

  private static io.grpc.MethodDescriptor<Block.Transaction,
      com.google.protobuf.nano.Empty> getToriiMethodHelper() {
    io.grpc.MethodDescriptor<Block.Transaction, com.google.protobuf.nano.Empty> getToriiMethod;
    if ((getToriiMethod = CommandServiceGrpc.getToriiMethod) == null) {
      synchronized (CommandServiceGrpc.class) {
        if ((getToriiMethod = CommandServiceGrpc.getToriiMethod) == null) {
          CommandServiceGrpc.getToriiMethod = getToriiMethod = 
              io.grpc.MethodDescriptor.<Block.Transaction, com.google.protobuf.nano.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "iroha.protocol.CommandService", "Torii"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.nano.NanoUtils.<Block.Transaction>marshaller(
                  new NanoFactory<Block.Transaction>(ARG_IN_METHOD_TORII)))
              .setResponseMarshaller(io.grpc.protobuf.nano.NanoUtils.<com.google.protobuf.nano.Empty>marshaller(
                  new NanoFactory<com.google.protobuf.nano.Empty>(ARG_OUT_METHOD_TORII)))
              .build();
        }
      }
    }
    return getToriiMethod;
  }
  private static final int ARG_IN_METHOD_STATUS = 2;
  private static final int ARG_OUT_METHOD_STATUS = 3;
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getStatusMethod()} instead.
  public static final io.grpc.MethodDescriptor<iroha.protocol.nano.Endpoint.TxStatusRequest,
      iroha.protocol.nano.Endpoint.ToriiResponse> METHOD_STATUS = getStatusMethodHelper();

  private static volatile io.grpc.MethodDescriptor<iroha.protocol.nano.Endpoint.TxStatusRequest,
      iroha.protocol.nano.Endpoint.ToriiResponse> getStatusMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<iroha.protocol.nano.Endpoint.TxStatusRequest,
      iroha.protocol.nano.Endpoint.ToriiResponse> getStatusMethod() {
    return getStatusMethodHelper();
  }

  private static io.grpc.MethodDescriptor<iroha.protocol.nano.Endpoint.TxStatusRequest,
      iroha.protocol.nano.Endpoint.ToriiResponse> getStatusMethodHelper() {
    io.grpc.MethodDescriptor<iroha.protocol.nano.Endpoint.TxStatusRequest, iroha.protocol.nano.Endpoint.ToriiResponse> getStatusMethod;
    if ((getStatusMethod = CommandServiceGrpc.getStatusMethod) == null) {
      synchronized (CommandServiceGrpc.class) {
        if ((getStatusMethod = CommandServiceGrpc.getStatusMethod) == null) {
          CommandServiceGrpc.getStatusMethod = getStatusMethod = 
              io.grpc.MethodDescriptor.<iroha.protocol.nano.Endpoint.TxStatusRequest, iroha.protocol.nano.Endpoint.ToriiResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "iroha.protocol.CommandService", "Status"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.nano.NanoUtils.<iroha.protocol.nano.Endpoint.TxStatusRequest>marshaller(
                  new NanoFactory<iroha.protocol.nano.Endpoint.TxStatusRequest>(ARG_IN_METHOD_STATUS)))
              .setResponseMarshaller(io.grpc.protobuf.nano.NanoUtils.<iroha.protocol.nano.Endpoint.ToriiResponse>marshaller(
                  new NanoFactory<iroha.protocol.nano.Endpoint.ToriiResponse>(ARG_OUT_METHOD_STATUS)))
              .build();
        }
      }
    }
    return getStatusMethod;
  }
  private static final int ARG_IN_METHOD_STATUS_STREAM = 4;
  private static final int ARG_OUT_METHOD_STATUS_STREAM = 5;
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getStatusStreamMethod()} instead.
  public static final io.grpc.MethodDescriptor<iroha.protocol.nano.Endpoint.TxStatusRequest,
      iroha.protocol.nano.Endpoint.ToriiResponse> METHOD_STATUS_STREAM = getStatusStreamMethodHelper();

  private static volatile io.grpc.MethodDescriptor<iroha.protocol.nano.Endpoint.TxStatusRequest,
      iroha.protocol.nano.Endpoint.ToriiResponse> getStatusStreamMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<iroha.protocol.nano.Endpoint.TxStatusRequest,
      iroha.protocol.nano.Endpoint.ToriiResponse> getStatusStreamMethod() {
    return getStatusStreamMethodHelper();
  }

  private static io.grpc.MethodDescriptor<iroha.protocol.nano.Endpoint.TxStatusRequest,
      iroha.protocol.nano.Endpoint.ToriiResponse> getStatusStreamMethodHelper() {
    io.grpc.MethodDescriptor<iroha.protocol.nano.Endpoint.TxStatusRequest, iroha.protocol.nano.Endpoint.ToriiResponse> getStatusStreamMethod;
    if ((getStatusStreamMethod = CommandServiceGrpc.getStatusStreamMethod) == null) {
      synchronized (CommandServiceGrpc.class) {
        if ((getStatusStreamMethod = CommandServiceGrpc.getStatusStreamMethod) == null) {
          CommandServiceGrpc.getStatusStreamMethod = getStatusStreamMethod = 
              io.grpc.MethodDescriptor.<iroha.protocol.nano.Endpoint.TxStatusRequest, iroha.protocol.nano.Endpoint.ToriiResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "iroha.protocol.CommandService", "StatusStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.nano.NanoUtils.<iroha.protocol.nano.Endpoint.TxStatusRequest>marshaller(
                  new NanoFactory<iroha.protocol.nano.Endpoint.TxStatusRequest>(ARG_IN_METHOD_STATUS_STREAM)))
              .setResponseMarshaller(io.grpc.protobuf.nano.NanoUtils.<iroha.protocol.nano.Endpoint.ToriiResponse>marshaller(
                  new NanoFactory<iroha.protocol.nano.Endpoint.ToriiResponse>(ARG_OUT_METHOD_STATUS_STREAM)))
              .build();
        }
      }
    }
    return getStatusStreamMethod;
  }

  private static final class NanoFactory<T extends com.google.protobuf.nano.MessageNano>
      implements io.grpc.protobuf.nano.MessageNanoFactory<T> {
    private final int id;

    NanoFactory(int id) {
      this.id = id;
    }

    @Override
    public T newInstance() {
      Object o;
      switch (id) {
      case ARG_IN_METHOD_TORII:
        o = new Block.Transaction();
        break;
      case ARG_OUT_METHOD_TORII:
        o = new com.google.protobuf.nano.Empty();
        break;
      case ARG_IN_METHOD_STATUS:
        o = new iroha.protocol.nano.Endpoint.TxStatusRequest();
        break;
      case ARG_OUT_METHOD_STATUS:
        o = new iroha.protocol.nano.Endpoint.ToriiResponse();
        break;
      case ARG_IN_METHOD_STATUS_STREAM:
        o = new iroha.protocol.nano.Endpoint.TxStatusRequest();
        break;
      case ARG_OUT_METHOD_STATUS_STREAM:
        o = new iroha.protocol.nano.Endpoint.ToriiResponse();
        break;
      default:
        throw new AssertionError();
      }
      @SuppressWarnings("unchecked")
      T t = (T) o;
      return t;
    }
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CommandServiceStub newStub(io.grpc.Channel channel) {
    return new CommandServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CommandServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new CommandServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CommandServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new CommandServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class CommandServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void torii(Block.Transaction request,
        io.grpc.stub.StreamObserver<com.google.protobuf.nano.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getToriiMethodHelper(), responseObserver);
    }

    /**
     */
    public void status(iroha.protocol.nano.Endpoint.TxStatusRequest request,
        io.grpc.stub.StreamObserver<iroha.protocol.nano.Endpoint.ToriiResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getStatusMethodHelper(), responseObserver);
    }

    /**
     */
    public void statusStream(iroha.protocol.nano.Endpoint.TxStatusRequest request,
        io.grpc.stub.StreamObserver<iroha.protocol.nano.Endpoint.ToriiResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getStatusStreamMethodHelper(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getToriiMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                Block.Transaction,
                com.google.protobuf.nano.Empty>(
                  this, METHODID_TORII)))
          .addMethod(
            getStatusMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                iroha.protocol.nano.Endpoint.TxStatusRequest,
                iroha.protocol.nano.Endpoint.ToriiResponse>(
                  this, METHODID_STATUS)))
          .addMethod(
            getStatusStreamMethodHelper(),
            asyncServerStreamingCall(
              new MethodHandlers<
                iroha.protocol.nano.Endpoint.TxStatusRequest,
                iroha.protocol.nano.Endpoint.ToriiResponse>(
                  this, METHODID_STATUS_STREAM)))
          .build();
    }
  }

  /**
   */
  public static final class CommandServiceStub extends io.grpc.stub.AbstractStub<CommandServiceStub> {
    private CommandServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CommandServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected CommandServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CommandServiceStub(channel, callOptions);
    }

    /**
     */
    public void torii(Block.Transaction request,
        io.grpc.stub.StreamObserver<com.google.protobuf.nano.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getToriiMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void status(iroha.protocol.nano.Endpoint.TxStatusRequest request,
        io.grpc.stub.StreamObserver<iroha.protocol.nano.Endpoint.ToriiResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getStatusMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void statusStream(iroha.protocol.nano.Endpoint.TxStatusRequest request,
        io.grpc.stub.StreamObserver<iroha.protocol.nano.Endpoint.ToriiResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getStatusStreamMethodHelper(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CommandServiceBlockingStub extends io.grpc.stub.AbstractStub<CommandServiceBlockingStub> {
    private CommandServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CommandServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected CommandServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CommandServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.nano.Empty torii(Block.Transaction request) {
      return blockingUnaryCall(
          getChannel(), getToriiMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public iroha.protocol.nano.Endpoint.ToriiResponse status(iroha.protocol.nano.Endpoint.TxStatusRequest request) {
      return blockingUnaryCall(
          getChannel(), getStatusMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<iroha.protocol.nano.Endpoint.ToriiResponse> statusStream(
        iroha.protocol.nano.Endpoint.TxStatusRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getStatusStreamMethodHelper(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CommandServiceFutureStub extends io.grpc.stub.AbstractStub<CommandServiceFutureStub> {
    private CommandServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CommandServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected CommandServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CommandServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.nano.Empty> torii(
        Block.Transaction request) {
      return futureUnaryCall(
          getChannel().newCall(getToriiMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<iroha.protocol.nano.Endpoint.ToriiResponse> status(
        iroha.protocol.nano.Endpoint.TxStatusRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getStatusMethodHelper(), getCallOptions()), request);
    }
  }

  private static final int METHODID_TORII = 0;
  private static final int METHODID_STATUS = 1;
  private static final int METHODID_STATUS_STREAM = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CommandServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CommandServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_TORII:
          serviceImpl.torii((Block.Transaction) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.nano.Empty>) responseObserver);
          break;
        case METHODID_STATUS:
          serviceImpl.status((iroha.protocol.nano.Endpoint.TxStatusRequest) request,
              (io.grpc.stub.StreamObserver<iroha.protocol.nano.Endpoint.ToriiResponse>) responseObserver);
          break;
        case METHODID_STATUS_STREAM:
          serviceImpl.statusStream((iroha.protocol.nano.Endpoint.TxStatusRequest) request,
              (io.grpc.stub.StreamObserver<iroha.protocol.nano.Endpoint.ToriiResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CommandServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .addMethod(getToriiMethodHelper())
              .addMethod(getStatusMethodHelper())
              .addMethod(getStatusStreamMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
