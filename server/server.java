import java.net.*;
import java.io.*;
import java.util.ArrayList; 

class server {
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
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    char c = 0; int s = 0; float x = 0, y = 0;
                    while (true) {
                        System.out.println("waiting for packet from client #"+ci+"...");
                        try {c = in.readChar(); } catch(EOFException eof) { break; }
                        x = in.readFloat(); y = in.readFloat(); s = in.readInt();
                        System.out.println("Received client #"+ci+" draw-update ["+c+"]: @("+x+", "+y+") [s = "+s+"]");
                        for (int i = 0; i < clients.size(); i++) { 
                            if (i == ci) continue;
                            System.out.println("\tBroadcasting to client #"+i+": ["+c+"]: @("+x+", "+y+") [s = "+s+"]");
                            Socket client = clients.get(i);
                            try { 
                                DataOutputStream o = new DataOutputStream(client.getOutputStream());
                                o.writeChar(c); o.writeFloat(x); o.writeFloat(y); o.writeInt(s); 
                            } catch(SocketException e) { continue; }
                        }
                    }
                    System.out.println("Closing connection with client #"+ci+"...");
                    try { clients.remove(ci); } catch(IndexOutOfBoundsException e) { }
                    socket.close(); in.close();
                } catch (Exception e) { e.printStackTrace(); }}
            }).start();
        }
    } catch(Exception e) { e.printStackTrace(); }}
}
