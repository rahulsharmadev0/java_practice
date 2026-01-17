package com.example;
import com.google.protobuf.InvalidProtocolBufferException;

public class StudentApp {

    static void print(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) throws Exception {
        print("=== Story: A Profile's Journey ===");

        Client client = new Client();
        Server server = new Server();

        print("Client crafts a V1 parcel...");
        com.example.v1.StudentProfile v1Profile = com.example.v1.StudentProfile.newBuilder()
                .setId("101")
                .setEmail("rahulsharma@gmail.com")
                .setPhone("23456789")
                .build();

        client.setProfile(v1Profile);
        client.printProfile();

        byte[] v1bytes = client.toByte();

        print("--- The parcel travels to the server (parseWithV2) ---");
        server.setProfileFromBytes(v1bytes);
        server.printProfile();

        print("Server updates email...");
        server.updateEmail("rahul@gmail.com");
        server.printProfile();

        byte[] newV1bytes = server.reserializeToV1();
        print("--- The updated parcel travels back to the client (parseWithV1) ---");
        client.setProfileFromBytes(newV1bytes);
        client.printProfile();
        print("=== The End ===");

    }

}

class Client {

    com.example.v1.StudentProfile profile;

    public void setProfile(com.example.v1.StudentProfile profile) {
        this.profile = profile;
    }

    public void setProfileFromBytes(byte[] bytes) throws com.google.protobuf.InvalidProtocolBufferException {
        this.profile = com.example.v1.StudentProfile.parseFrom(bytes);
    }

    public byte[] toByte() {
        return profile.toByteArray();
    }

    public void printProfile() {
        System.out.println("======Client reads profile=====\n" + profile);
    }

}

class Server {

    public com.example.v2.StudentProfile profile;

    public void updateEmail(String newEmail) throws IllegalStateException {
        if (this.profile == null) {
            throw new IllegalStateException("Profile not set");
        }
        this.profile = profile.toBuilder().setEmail(newEmail).build();
    }

    public void setProfile(com.example.v2.StudentProfile profile) {
        this.profile = profile;
    }

    public void setProfileFromBytes(byte[] bytes) throws com.google.protobuf.InvalidProtocolBufferException {
        this.profile = com.example.v2.StudentProfile.parseFrom(bytes);
    }

    public byte[] toByte() {
        return profile.toByteArray();
    }

    public byte[] reserializeToV1() throws InvalidProtocolBufferException {
        return com.example.v1.StudentProfile.parseFrom(toByte()).toByteArray();
    }

    public void printProfile() {
        StringBuilder builder = new StringBuilder();
        for (com.google.protobuf.Descriptors.FieldDescriptor field : profile.getDescriptorForType().getFields()) {
            boolean isSet = profile.getAllFields().containsKey(field);
            Object value = isSet ? profile.getField(field) : field.getDefaultValue();
            builder.append(field.getName()).append(": ").append(value).append(isSet ? "" : " (default)").append("\n");
        }
        System.out.println("======Server reads profile=====\n" + builder.toString());
    }

}
