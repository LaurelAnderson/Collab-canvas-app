package com.example.server;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

import com.example.networking.Packet;

public class Server {
    public static void main(String args[]) {try {
        ServerSocket server = new ServerSocket(8080);
        final ArrayList<Socket> clients = new ArrayList<Socket>();
        while (true) {
            System.out.println("Server: Waiting for a client...");
            final Socket socket = server.accept();
            new Thread(new Runnable() {
                public void run() {try {
                    long id = Thread.currentThread().getId();
                    int ci = clients.size();
                    clients.add(socket);
                    System.out.println("client "+ci+" is connected! (thread = "+id+")");

                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    while (true) {
                        System.out.println("waiting for packet from client #"+ci+"...");

                        // rewrite this
                        // get packet over socket
                        Packet packet = (Packet) in.readObject();

                        System.out.println("Received client " + clients.size());
                        for (int i = 0; i < clients.size(); i++) {
                            System.out.println("Hello");
                            if (i == ci) continue;
                            System.out.println("Goodbye");
                            Socket client = clients.get(i);

                            try {
                                ObjectOutputStream o = new ObjectOutputStream(client.getOutputStream());

                                // send same packet back over other stream
                                o.writeObject(packet);
                                System.out.println("We are sending a packet");

                            } catch(SocketException e) { continue; }
                        }
                    }
                    // System.out.println("Closing connection with client #"+ci+"...");
//                    try { } catch(IndexOutOfBoundsException e) {  }
//                    socket.close(); in.close();
                } catch (Exception e) { e.printStackTrace(); }}
            }).start();

        }
    } catch(Exception e) { e.printStackTrace(); }}
}
