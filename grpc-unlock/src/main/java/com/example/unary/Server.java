package com.example.unary;

import com.example.chat.Message;
import com.example.chat.MessageServiceGrpc.MessageServiceImplBase;

import io.grpc.Metadata;
import io.grpc.ServerBuilder;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.stub.StreamObserver;

public class Server {
    public static void main(String[] args) throws Exception {

        io.grpc.Server server = ServerBuilder.forPort(8081)
                .addService(new MessageServiceImp())
                .intercept(new ClientIntercepter())
                .build().start();

        server.awaitTermination();

    }

}

class ClientIntercepter implements io.grpc.ServerInterceptor {

    @Override
    public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata metadata,
            ServerCallHandler<ReqT, RespT> nextHandler) {

        call.getAttributes().keys().forEach(key -> {
             System.out.println(key+" : " + call.getAttributes().get(key));
        });

        return nextHandler.startCall(call, metadata);
    }

}

class MessageServiceImp extends MessageServiceImplBase {

    @Override
    public void sendMessage(Message request, StreamObserver<Message> responseObserver) {

        System.out.println(request.getSender() + ": " + request.getContent());
        System.out.println("Timestamp: " + request.getTimestamp().getSeconds());

        // server creates response (better practice)
        Message response = Message.newBuilder()
                .setSender("Server")
                .setContent(request.getContent())
                .setTimestamp(request.getTimestamp())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

}
