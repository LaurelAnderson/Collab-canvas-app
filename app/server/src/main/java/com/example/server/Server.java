package com.example.server;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

import com.example.networking.Packet;

public class Server {
    public static void main(String args[]) {try {
        ServerSocket server = new ServerSocket(8080); // create server bound to port 8080
        final ArrayList<Socket> clients = new ArrayList<Socket>(); // keep track of clients who connect
        while (true) {

            if (clients.size() > 5) Thread.sleep(50);
            else {
                System.out.println("Server: Waiting for a client...");
                final Socket socket = server.accept(); // wait for another client, accept when receive conn request

                new Thread(new Runnable() { // spawn and run new thread
                    public void run() {try {
                        long id = Thread.currentThread().getId();
                        int ci = clients.size(); // number of client sockets
                        clients.add(socket); // add new client to client socket Array
                        System.out.println("client "+ci+" is connected! (thread = "+id+")");

                        while (true) {
                            System.out.println("waiting for packet from client #"+ci+"...");

                            // client has made changes, receiving a Packet
                            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                            Packet packet = (Packet) in.readObject(); // read in new Packet received

                            System.out.println("Received client " + clients.size()); // successfully read
                            for (int i = 0; i < clients.size(); i++) { // clients server will send packets to
                                System.out.println("Hello"); // also flushes
                                if (i == ci) continue; // don't send ourselves a broadcast packet, bc already know
                                System.out.println("Goodbye");
                                Socket client = clients.get(i); // get sockets of (each) client in client Array

                                try {
                                    ObjectOutputStream o = new ObjectOutputStream(client.getOutputStream());
                                    o.writeObject(packet); // send same packet back over other stream
                                    System.out.println("We are sending a packet");

                                } catch(SocketException e) { continue; }
                            }
                        }

                    } catch (Exception e) { e.printStackTrace(); }} // errors in thread
                }).start(); // start thread
            }

        }
    } catch(Exception e) { e.printStackTrace(); }}
}
