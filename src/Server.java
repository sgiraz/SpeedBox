import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * @author Simonegirardi
 * This Server start listeninig  for clients request to send file, 
 * if the user accept the request, Server send a message to Client and it prepare to receive the file
 * else the Server send a message to refuse request.
 */
public class Server implements Runnable
{
	private int portNumber;

	public Server(int portNumber){
		this.portNumber = portNumber;
		new Thread(this, "Server receive file Thread").start();
	}

	public void startServer() 
	{
		System.out.println("SERVER: Server started...");
		while(true){
			try(ServerSocket serverSocket = new ServerSocket(portNumber);
					Socket clientSocket = serverSocket.accept();
					BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
					DataInputStream input = new DataInputStream(clientSocket.getInputStream())){

				System.out.println("SERVER: create" + clientSocket);

				// check for input request type
				String typeOfInputClient = reader.readLine();
				System.out.println("TYPE OF INPUT: "+ typeOfInputClient);

				if(typeOfInputClient.equals("send_request"))
				{
					// send acceptation
					System.out.println("SERVER: Sending confirm...");
					String fileName = reader.readLine();
					if(checkResponse(fileName))
					{
						System.out.println("check response successed!");
						writer.write("accept_request");
						writer.newLine();
						writer.flush();

						// receiving data
						receiveData(input, fileName);
					}
					else
					{
						writer.write("refused_request");
						writer.newLine();
						writer.flush();
					}
						

				}
				else
					System.out.println("faiulure...");

			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		} 
	}

	/**
	 * Receive data from client ad save it on a file in local machine
	 * @param reader : a DataImputStream reader
	 * @param fileName: name of file to receive with its extension
	 * @throws IOException
	 */
	private void receiveData(DataInputStream reader, String fileName) throws IOException
	{
		Path path = Paths.get(System.getProperty("user.home") + "/Desktop/" + fileName);
		int count;
		byte[] bytes = new byte[1024*32]; // my personal buffer (32KB)
		long size = reader.readLong();
		int received = 0;
		if(size >= 0){
			try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(path, CREATE, APPEND))) {
				while((count = reader.read(bytes)) > 0 ){
					received += count;
					out.write(bytes, 0 , count);
					System.out.format("Dowload: %.1f %% %n", received/(float)size * 100);
				}  
				System.out.println("Data saved in: " + path);
			} catch (IOException x) {
				System.err.println(x);
			}	
		}
		else
			System.out.println("Error: returned -1, impossible to receive" + fileName);	
	}

	/**
	 * A Dialog window that ask confirm for accept to receive file  
	 * @param fileName: name of file to receive
	 * @return true only if the user say yes
	 */
	private boolean checkResponse(String fileName) {
		JDialog.setDefaultLookAndFeelDecorated(true);
		int response = JOptionPane.showConfirmDialog(null, "Do you want to receive " + fileName + "?", "Confirm",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (response == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		startServer();
	}
}

