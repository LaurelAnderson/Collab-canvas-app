import java.net.*;
import java.io.*;

class client {

	public static void main(String args[]) {try {

		Socket socket = new Socket("127.0.0.1", 8080);
		System.out.println("Connected");

		DataInputStream stdin = new DataInputStream(System.in);
		DataOutputStream socket_stream = new DataOutputStream(socket.getOutputStream());
		
		String line = "";
		while (!line.equals(".")) {
			System.out.print(">>> ");
			line = stdin.readLine();
			socket_stream.writeUTF(line);
		}

		stdin.close();
		socket_stream.close();
		socket.close();

	} catch(IOException i) {}}
}