package com.example.bidirectional;

import java.net.SocketAddress;

import com.example.chat.Message;
import com.example.chat.BiChatServiceGrpc.BiChatServiceImplBase;

import io.grpc.Metadata;
import io.grpc.ServerBuilder;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.stub.StreamObserver;
import io.grpc.ServerCallHandler;

public class Server {
    public static void main(String[] args) throws Exception {

        io.grpc.Server server = ServerBuilder.forPort(8081)
                .intercept(new ClientIntercepter())
                .addService(new ChatService())
                .build();

        server.start();

        server.awaitTermination();
        server.shutdown();

    }

}

class ChatService extends BiChatServiceImplBase {

    @Override
    public StreamObserver<Message> chat(StreamObserver<Message> responseObserver) {

        return new StreamObserver<Message>() {

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }

            @Override
            public void onError(Throwable arg0) {
                System.out.println("Error occurred in chat: " + arg0.getMessage());
            }

            @Override
            public void onNext(Message message) {
                System.out.println("Server received message: " + message.getContent());
                Message reply = Message.newBuilder()
                        .setContent("Ack: " + message.getContent())
                        .setSender("Server")
                        .setTimestamp(message.getTimestamp())
                        .build();
                responseObserver.onNext(reply);
            }
        };
    }

}

class ClientIntercepter implements io.grpc.ServerInterceptor {

    @Override
    public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata metadata,
            ServerCallHandler<ReqT, RespT> callHandler) {

        SocketAddress remoteAddress = call.getAttributes().get(io.grpc.Grpc.TRANSPORT_ATTR_REMOTE_ADDR);
        System.out.println("Remote Address: " + remoteAddress.toString());
        return callHandler.startCall(call, metadata);
    }

}