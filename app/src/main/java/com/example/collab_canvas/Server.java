package com.example.collab_canvas;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Server {
    public static void main(String args[]) {try {
        ServerSocket server = new ServerSocket(8080);
        ArrayList<Socket> clients = new ArrayList<Socket>();
        while (true) {
            System.out.println("Server: Waiting for a client...");
            Socket socket = server.accept();
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

                        System.out.println("Received client");
                        for (int i = 0; i < clients.size(); i++) {
                            if (i == ci) continue;
                            Socket client = clients.get(i);
                            try {
                                ObjectOutputStream o = new ObjectOutputStream(client.getOutputStream());

                                // rewrite this
                                // send same packet back over other stream
                                o.writeObject(packet);

                            } catch(SocketException e) { continue; }
                        }
                    }
                    // System.out.println("Closing connection with client #"+ci+"...");
                    // try { clients.remove(ci); } catch(IndexOutOfBoundsException e) { }
                    // socket.close(); in.close();
                } catch (Exception e) { e.printStackTrace(); }}
            }).start();

        }
    } catch(Exception e) { e.printStackTrace(); }}
}
