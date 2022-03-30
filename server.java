import java.net.*;
import java.io.*;

class client_handler implements Runnable {

	Socket socket = null;

	public client_handler(Socket socket) {
		this.socket = socket;
	}

	public void run() {try {

		System.out.println("client handler " + Thread.currentThread().getId() + " is running!");

		DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());

		String line = "";
		while (!line.equals(".")) {
			line = in.readUTF();
			System.out.println("client said: " + line);
		}
		System.out.println("Closing connection...");

		socket.close();
		in.close();

	 } catch (Exception e) {}}

	public int get_id() {
		return Thread.currentThread().getId();
	}
}

class server {

	public static void main(String args[]) {try {

		ServerSocket server = new ServerSocket(8080);
		System.out.println("Server started.");

	

		while (true) {
			System.out.println("Waiting for a client ...");

			Socket socket = server.accept();
			client_handler h = new client_handler(socket);
			Thread t = new Thread(h);
            		t.start();
		}

	} catch(IOException i) {}}
}



// // Java code for thread creation by extending
// // the Thread class
// class MultithreadingDemo extends Thread {
//     public void run()
//     {
//         try {
//             // Displaying the thread that is running
//             System.out.println(
//                 "Thread " + Thread.currentThread().getId()
//                 + " is running");
//         }
//         catch (Exception e) {
//             // Throwing an exception
//             System.out.println("Exception is caught");
//         }
//     }
// }
 
// // Main Class
// public class Multithread {
//     public static void main(String[] args)
//     {
//         int n = 1; // Number of threads
//         for (int i = 0; i < n; i++) {
//             MultithreadingDemo object
//                 = new MultithreadingDemo();
//             object.start();
//         }
//     }
// }