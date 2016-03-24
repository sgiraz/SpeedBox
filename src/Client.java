
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client implements Runnable {

	private String ip;
	private String path;
	private int port;
	boolean sending = false;
	byte bytes[] = new byte[1024*16];
	Thread t;
	
	private final int ERROR = -1;
	public boolean SendFile(String path, String ip, int port)
	{
		if(sending)
			return false;
		
		this.ip = ip;
		this.port = port;
		this.path = path;
		sending = true;
		
		t = new Thread(this, "Send file thread");
		t.start();
		
		return true;
	}
	
	public void run() {
		 
		try(Socket clientSocket = new Socket(ip, port);
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream())){
			
			// send first message message
			System.out.println("CLIENT: Socket created: " + clientSocket + "\nSending data: " + path +"...");
			writer.write("send_request");
			writer.newLine(); 
			writer.write(Utils.pathGetFilename(path));
			writer.newLine();
			writer.flush();
			
			System.out.println("CLIENT: Waiting for response..");
			String command = reader.readLine();
			if(command.equals("accept_request"))
			{
				System.out.println("CLIENT: Accepted... sending file");
				
				// send file
				System.out.println("File request: " + path);
				File file = new File(path);
				int pos;
				if(file.exists()){
					System.out.println("CLIENT: file exists, sending");
					try(FileInputStream fs = new FileInputStream(file)){
						// send for dimension
						output.writeLong(file.length());

						System.out.println("CLIENT: length" + file.length());
						
						// send data
						while((pos = fs.read(bytes)) > 0 ){
							output.write(bytes,0,pos); 
						}
					}
					
				}
				else
					output.writeLong(ERROR);
			}
			
		} catch (IOException e) {
			System.out.println("CLIENT: Impossible to connect");			
			e.printStackTrace();
		}

		System.out.println("CLIENT: finish");
	}
	 
}


		
	

			