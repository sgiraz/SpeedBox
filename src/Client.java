
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
	byte bytes[] = new byte[1024*32];
	
	private final int ERROR = -1;
	
	public boolean SendFile(String path, String ip, int port)
	{
		if(sending)
			return false;
		this.ip = ip;
		this.port = port;
		this.path = path;
		sending = true;
		new Thread(this, "CLIENT: send file thread").start();
		
		return true;
	}
	
	public void run() {
		 
		try(Socket clientSocket = new Socket(ip, port);
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream())){
			
			System.out.println("CLIENT: connected to " + clientSocket.getInetAddress());
			
			// send first message message
			System.out.println("CLIENT: Socket created: " + clientSocket + "\nSending data: " + path +"...");
			writer.write("send_request");
			writer.newLine(); 
			writer.write(Utils.pathGetFilename(path));
			writer.newLine();
			writer.flush();

			System.out.println("CLIENT: Waiting for response..");
			String command = reader.readLine();

			System.out.println("CLIENT: command: " + command);
			if(command.equals("accept_request"))
			{
				System.out.println("CLIENT: Accepted... sending file");
				 
				// send file
				File file = new File(path);
				if(file.exists()){
					
					SendingFile sendingFile = new SendingFile(file);
					// TODO: ADD sendingFile TO THE SENDING FILES LIST
					
					System.out.println("CLIENT: file exists, sending..");
					try(FileInputStream fs = new FileInputStream(file)){
						// send file dimension
						output.writeLong(file.length());
						System.out.println("CLIENT: file length: " + file.length());
						
						int sent;
						while((sent = fs.read(bytes)) > 0 ){
							output.write(bytes,0,sent); 
							sendingFile.update(sent);
						}
					}
					
				}
				else
					output.writeLong(ERROR);
			}
			else if (command.equals("refused_request"))
			{
				System.out.println("CLIENT: File Refused");
				
			}
			else
			{
				System.out.println("CLIENT: other command " + command);
			}			
		} catch (IOException e) {
			System.out.println("CLIENT: Impossible to connect");			
			e.printStackTrace();
		}

		System.out.println("CLIENT: finish");
		sending=false;
	}
	 
}


		
	

			