package com.example.bidirectional;

import java.util.Scanner;


import com.example.chat.BiChatServiceGrpc.BiChatServiceStub;
import com.example.chat.BiChatServiceGrpc;
import com.example.chat.Message;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class Client {

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8081)
                .usePlaintext()
                .build();

        BiChatServiceStub asyncStub = BiChatServiceGrpc.newStub(channel);
        StreamObserver<Message> response = asyncStub.chat(new StreamObserver<Message>() {
            @Override
            public void onNext(Message value) {
                System.out.println(value.getSender() + ": " + value.getContent());
                System.out.println("Timestamp: " + value.getTimestamp().getSeconds());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error receiving message: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Chat ended by server.");
            }
        });

        Scanner scanner = new Scanner(System.in);

        String msg = "";

        while (!(msg = scanner.nextLine()).equals("exit")) {

            Message request = Message.newBuilder()
                    .setContent(msg)
                    .setSender("Client")
                    .setTimestamp(com.google.protobuf.Timestamp.newBuilder()
                            .setSeconds(System.currentTimeMillis() / 1000)
                            .build())
                    .build();

            response.onNext(request);
        }
        scanner.close();
        channel.shutdown();

    }

}
