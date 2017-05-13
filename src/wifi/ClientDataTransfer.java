package wifi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;

public class ClientDataTransfer implements Runnable {

	private Socket clientSocket;
	private String ip;
	private String path;
	private String chatText;
	private int port;
	boolean sendingFile = false;
	boolean sendingText = false;
	byte bytes[] = new byte[1024*10];

	private final int ERROR = -1;

	public ClientDataTransfer(String ip, int port) {
		this.port = port;
		this.ip = ip;
	}


	public boolean SendFile(String path)
	{
		if(sendingFile)
			return false;
		this.path = path;
		sendingFile = true;
		new Thread(this, "CLIENT: send file thread").start();

		return true;
	}

	public boolean SendText(String chatText) {
		if(sendingText)
			return false;
		this.chatText = chatText;
		sendingText = true;
		new Thread(this, "CLIENT: send file thread").start();

		return true;

	}

	public void destroy(){
		if(clientSocket != null && !clientSocket.isClosed())
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public void run() {

		try(Socket clientSocket = new Socket(ip, port);
				BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
				DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
				PrintStream chatOutputStream = new PrintStream(clientSocket.getOutputStream());)
		{
			this.clientSocket = clientSocket;
			System.out.println("ClientDataTransfer: connected to " + clientSocket.getInetAddress());
			
			// chat
			if(port == 17000){
				System.out.println("Sending: " + chatText + " to: " + ip + " on port: " + port);
				chatOutputStream.print(chatText);
			}
			// file
			else{

				// send first message message
				System.out.println("CLIENT: Socket created: " + clientSocket + "\nSending data: " + path +"...");
				writer.write("send_request");
				writer.newLine(); 
				writer.write(Utils.pathGetFilename(path));
				writer.newLine();
				writer.flush();

				System.out.println("ClientDataTransfer: Waiting for response..");
				String command = reader.readLine();

				System.out.println("ClientDataTransfer: command: " + command);
				if(command.equals("accept_request"))
				{
					System.out.println("ClientDataTransfer: Accepted... sendingFile file");

					// send file
					File file = new File(path);
					if(file.exists()){

						SendingFile sendingFile = new SendingFile(file.length());
						// TODO: ADD sendingFile TO THE SENDING FILES LIST

						System.out.println("ClientDataTransfer: file exists, sendingFile..");
						Utils.data_transfering = true;

						try(FileInputStream fs = new FileInputStream(file)){
							// send file dimension
							output.writeLong(file.length());
							System.out.println("ClientDataTransfer: file length: " + file.length());

							int sent;
							while((sent = fs.read(bytes)) > 0 ){
								output.write(bytes,0,sent); 
								sendingFile.update(sent);
							}
							Utils.data_transfering = false;
						}

					}
					else
						output.writeLong(ERROR);
				}
				else if (command.equals("refused_request"))
				{
					System.out.println("ClientDataTransfer: File Refused");

				}
				else
				{
					System.out.println("ClientDataTransfer: other command " + command);
				}	
			}
		} catch (IOException e) {
			System.out.println("ClientDataTransfer: Impossible to connect");			
			e.printStackTrace();
		}

		System.out.println("ClientDataTransfer: finish");
		sendingFile=false;
		sendingText=false;
	}

}





