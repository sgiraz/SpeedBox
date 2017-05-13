package wifi;
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
public class ServerDataTransfer implements Runnable
{
	private int portNumber;
	public ServerSocket serverSocket;
	public Socket clientSocket;

	public ServerDataTransfer(int portNumber)
	{
		this.portNumber = portNumber;
		new Thread(this, "Server receive data Thread").start();
	}

	public void destroy()
	{
		try {
			if(clientSocket != null && !clientSocket.isClosed())
				clientSocket.close();
			if(serverSocket != null && !serverSocket.isClosed())
				serverSocket.close();
		}
		catch (IOException e) {
			e.printStackTrace();
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
		byte[] bytes = new byte[1024*20]; // my personal buffer (20KB)
		long size = reader.readLong();
		if(size >= 0){
			SendingFile sendingFile = new SendingFile(size);

			try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(path, CREATE, APPEND))) {
				while((count = reader.read(bytes)) > 0 ){
					out.write(bytes, 0 , count);
					sendingFile.update(count);
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
	public void run() 
	{
		System.out.println("SERVER: Server started...");
		while(true)
		{
			try(ServerSocket serverSocket = new ServerSocket(portNumber);
					Socket clientSocket = serverSocket.accept();
					BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
					DataInputStream input = new DataInputStream(clientSocket.getInputStream())){

				this.serverSocket = serverSocket;
				this.clientSocket = clientSocket;

				System.out.println("SERVER: socket create" + clientSocket);

				// chat --> DA SISTEMARE, FORSE CAMBIANDI ANCHE IL TIPO DI PANNELLO USATO,
				// IL PROBLEMA E' LEGGERE TUTTA LA STRINGA CHE VIENE RICEVUTA E PRINTARLA NELLO SPAZIO APPOSITO DELLA CHAT NELLA GUI
				if(portNumber == 17000 ){
					String inputText;
					while((inputText = reader.readLine() ) != null){
						System.out.println("Appending to TextArea the follow text:\n" + inputText);
						SendBoxGUI.instance.chatArea.setText(inputText + "\n");
					}
				}
				else{

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
			}
			catch(IOException e)
			{
				System.out.println("SERVER: client disconnected.");
				e.printStackTrace();
				return;
			}
		} 
	}
}

