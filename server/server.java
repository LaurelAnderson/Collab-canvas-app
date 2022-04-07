import java.net.*;
import java.io.*;
import java.util.ArrayList; 

class server {
	public static void main(String args[]) {try {
		ServerSocket server = new ServerSocket(8080);
		System.out.println("Server started.");
		ArrayList<Socket> clients = new ArrayList<Socket>();

		while (true) {
			System.out.println("Server: Waiting for a client...");
			Socket socket = server.accept();
			new Thread(new Runnable() {
				public void run() {try {
					long id = Thread.currentThread().getId();
					int ci = clients.size();
					clients.add(socket);
					System.out.println("thread "+id+" is running! "+"(ci = "+ci+")");
					DataInputStream in = new DataInputStream(socket.getInputStream());
					char c = 0; int s = 0;
					float x = 0, y = 0;
					boolean running = true;
					while (running) {
						System.out.println("waiting to receive draw-update from client #"+ci+"...");
						try {c = in.readChar(); } catch(EOFException eof) {
							System.out.println("---> client #"+ci+" disconnected!");
							break;
						}
						x = in.readFloat(); y = in.readFloat(); s = in.readInt();
						System.out.println("Received from client #"+ci+" draw-update = ["+c+"]: @("+x+", "+y+") [s = "+s+"]");
						System.out.println("Broadcasting to other clients...");
						for (int i = 0; i < clients.size(); i++) { 
							if (i == ci) continue;
							System.out.println("\tbroadcasting to client #"+i+": ["+c+"]: @("+x+", "+y+") [s = "+s+"]");
							Socket client = clients.get(i);
							try { 
								DataOutputStream o = new DataOutputStream(client.getOutputStream());
								o.writeChar(c); o.writeFloat(x); o.writeFloat(y); o.writeInt(s); 
							} catch(SocketException e) { continue; }
							
						}
					}
					System.out.println("Closing connection with client #" + ci + "...");
					try { clients.remove(ci); } catch(IndexOutOfBoundsException e) { }
					socket.close();
					in.close();
	 			} catch (Exception e) { e.printStackTrace(); }}
			}).start();
		}
	} catch(Exception e) { e.printStackTrace(); }}
}
