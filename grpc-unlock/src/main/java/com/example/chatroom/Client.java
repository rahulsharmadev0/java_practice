package com.example.chatroom;

import java.util.Scanner;

import com.example.chat.Message;
import com.example.chat.MessageServiceGrpc;
import com.example.chat.MessageServiceGrpc.MessageServiceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

// Unary RPC
// Client → sendMessage(request) → Server → onNext(response) + onCompleted()
public class Client {

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8081)
                .usePlaintext()
                .build();

        MessageServiceBlockingStub stub = MessageServiceGrpc.newBlockingStub(channel);

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

            Message response = stub.sendMessage(request);

            System.out.println(response.getSender() + ": " + response.getContent());
            System.out.println("Timestamp: " + response.getTimestamp().getSeconds());
        }
        scanner.close();
        channel.shutdown();

    }

}
