import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.Console;
import java.net.InetAddress;

public class ClientSpeaker{

	//close sockets !
	public static void main(String[] args){

		byte[] host;
		int port;
		int userPort = 2049;
		InetAddress direction;
		Socket socket = null;
		Socket socketConnection = null;
		PrintWriter outPrinter;
		BufferedReader inReader;
		String user;
		String userIP;
		String message;

		try {
			// Conexion with server
			host = "tututututututututu".getBytes();
			port = 2048;
			direction = InetAddress.getByAddress(host);
			socket = new Socket(direction, port);

			// Hand shaking
			outPrinter = new PrintWriter(socket.getOutputStream(), true);
			outPrinter.println("01");
			inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// Choose user to talk with
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			user = br.readLine();
			outPrinter.println(user);

			// Get user IP
			userIP = inReader.readLine();
			if(!userIP.equals("-1")){
				socketConnection = new Socket(userIP, userPort);
				outPrinter = new PrintWriter(socketConnection.getOutputStream(), true);

				//Sending messages
				while(true){
					message = br.readLine();
					outPrinter.println(message);
				}
			}
		}
		catch (IOException e) {
			System.err.println("Error");
		}
	}
}