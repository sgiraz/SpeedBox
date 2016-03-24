
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {

	private String ip;
	private String filename;
	private int port;
	boolean sending = false;
	Thread t;
	
	public boolean SendFile(String filename, String ip, int port)
	{
		if(sending)
			return false;
		
		this.ip = ip;
		this.port = port;
		this.filename = filename;
		sending = true;
		
		t = new Thread(this, "Send file thread");
		t.start();
		
		return true;
	}
	
	public void run() {
		 
		try(Socket clientSocket = new Socket(ip, port);
			BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)){
			
			// send first message message
			System.out.println("CLIENT: Socket created: " + clientSocket + "\nSending data: " + filename +"...");
			writer.println("send_request");
			writer.println(filename);
						
			System.out.println("CLIENT: Waiting for response.."); 
			
			String command = input.readLine();
			if(command == "accept_request")
			{
				System.out.println("CLIENT: Accepted.."); 
			}
			
		} catch (IOException e) {
			System.out.println("CLIENT: Impossible to connect");			
			e.printStackTrace();
		}

		System.out.println("CLIENT: finish");
	}
	 
}


		
	

			