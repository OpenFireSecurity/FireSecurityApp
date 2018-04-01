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
public final class QueryServiceGrpc {

  private QueryServiceGrpc() {}

  public static final String SERVICE_NAME = "iroha.protocol.QueryService";

  // Static method descriptors that strictly reflect the proto.
  private static final int ARG_IN_METHOD_FIND = 0;
  private static final int ARG_OUT_METHOD_FIND = 1;
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getFindMethod()} instead.
  public static final io.grpc.MethodDescriptor<Queries.Query,
      Responses.QueryResponse> METHOD_FIND = getFindMethodHelper();

  private static volatile io.grpc.MethodDescriptor<Queries.Query,
      Responses.QueryResponse> getFindMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<Queries.Query,
      Responses.QueryResponse> getFindMethod() {
    return getFindMethodHelper();
  }

  private static io.grpc.MethodDescriptor<Queries.Query,
      Responses.QueryResponse> getFindMethodHelper() {
    io.grpc.MethodDescriptor<Queries.Query, Responses.QueryResponse> getFindMethod;
    if ((getFindMethod = QueryServiceGrpc.getFindMethod) == null) {
      synchronized (QueryServiceGrpc.class) {
        if ((getFindMethod = QueryServiceGrpc.getFindMethod) == null) {
          QueryServiceGrpc.getFindMethod = getFindMethod = 
              io.grpc.MethodDescriptor.<Queries.Query, Responses.QueryResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "iroha.protocol.QueryService", "Find"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.nano.NanoUtils.<Queries.Query>marshaller(
                  new NanoFactory<Queries.Query>(ARG_IN_METHOD_FIND)))
              .setResponseMarshaller(io.grpc.protobuf.nano.NanoUtils.<Responses.QueryResponse>marshaller(
                  new NanoFactory<Responses.QueryResponse>(ARG_OUT_METHOD_FIND)))
              .build();
        }
      }
    }
    return getFindMethod;
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
      case ARG_IN_METHOD_FIND:
        o = new Queries.Query();
        break;
      case ARG_OUT_METHOD_FIND:
        o = new Responses.QueryResponse();
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
  public static QueryServiceStub newStub(io.grpc.Channel channel) {
    return new QueryServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static QueryServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new QueryServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static QueryServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new QueryServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class QueryServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void find(Queries.Query request,
        io.grpc.stub.StreamObserver<Responses.QueryResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getFindMethodHelper(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getFindMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                Queries.Query,
                Responses.QueryResponse>(
                  this, METHODID_FIND)))
          .build();
    }
  }

  /**
   */
  public static final class QueryServiceStub extends io.grpc.stub.AbstractStub<QueryServiceStub> {
    private QueryServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private QueryServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected QueryServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new QueryServiceStub(channel, callOptions);
    }

    /**
     */
    public void find(Queries.Query request,
        io.grpc.stub.StreamObserver<Responses.QueryResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getFindMethodHelper(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class QueryServiceBlockingStub extends io.grpc.stub.AbstractStub<QueryServiceBlockingStub> {
    private QueryServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private QueryServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected QueryServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new QueryServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public Responses.QueryResponse find(Queries.Query request) {
      return blockingUnaryCall(
          getChannel(), getFindMethodHelper(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class QueryServiceFutureStub extends io.grpc.stub.AbstractStub<QueryServiceFutureStub> {
    private QueryServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private QueryServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected QueryServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new QueryServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Responses.QueryResponse> find(
        Queries.Query request) {
      return futureUnaryCall(
          getChannel().newCall(getFindMethodHelper(), getCallOptions()), request);
    }
  }

  private static final int METHODID_FIND = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final QueryServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(QueryServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_FIND:
          serviceImpl.find((Queries.Query) request,
              (io.grpc.stub.StreamObserver<Responses.QueryResponse>) responseObserver);
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
      synchronized (QueryServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .addMethod(getFindMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
