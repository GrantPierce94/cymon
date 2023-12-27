package com.example.cymonbattle.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static final int PORT=9147;
    public static Lobby currentLobby=new Lobby();
    public static  ConcurrentHashMap<String, ObjectOutputStream> activeClients = new ConcurrentHashMap<String, ObjectOutputStream>();

    public static void main(String[] args) throws Exception {
        System.out.println("Server is up and running...");
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                Socket socket=listener.accept();
                new Handler(socket).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static class Handler extends Thread {
        private final Socket socket;
        private ObjectInputStream Receiver;
        private ObjectOutputStream Sender;

        /**
         * Constructs a handler thread, squirreling away the socket.
         * All the interesting work is done in the run method.
         */
        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {

                // Create object streams for the socket.
                Sender = new ObjectOutputStream(socket.getOutputStream());
                Receiver = new ObjectInputStream(socket.getInputStream());
                activeClients.put(UUID.randomUUID().toString(),Sender);
                //loop for allowing connections

                while(true){
                    Mapper receipt = (Mapper) Receiver.readObject();
                    System.out.println(receipt.getKey());
                    if (receipt.getKey().equals("CONNECT")) {//player is attempting to connect
                        if(!currentLobby.isFull()){//lobby is not full so allow the player

                        }else{//the lobby is full so continue with the game
                            Mapper mapper=new Mapper("DENY","THE LOBBY IS FULL");
                            Sender.writeObject(mapper);
                        }
                    } else {//the game is on going so take the received game object and broadcast it to other players

                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}


