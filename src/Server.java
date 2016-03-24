import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/*
 * Il client invia al server il nome di un file (di testo), preso dalla riga di comando.
 * Il server risponde spedendo al client il contenuto, riga per riga, di tale file.
 * Sono gestite le situazioni particolari (file not found, etc.)
 */ 
public class Server implements Runnable {
	private int portNumber;

	public Server(int portNumber){
		this.portNumber = portNumber;
		new Thread(this, "Server Thread").start();
	}

	public void startServer() {
		System.out.println("SERVER: Server started...");
		while(true){
			try(ServerSocket serverSocket = new ServerSocket(portNumber);
					Socket clientSocket = serverSocket.accept();
					BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
					DataInputStream input = new DataInputStream(clientSocket.getInputStream())){

				System.out.println("SERVER: create" + clientSocket);

				// check for input request type
				String typeOfInputClient = reader.readLine();
				System.out.println("TYPE OF INPUT: "+ typeOfInputClient);

				if(typeOfInputClient.equals("send_request")){
					// send acceptation
					System.out.println("SERVER: Sending confirm...");
					String fileName = reader.readLine();
					if(checkResponse(fileName)){
						writer.println("accept_request");

						// receiving data
						receiveData(input, fileName);
					}

				}
				else
					System.out.println("faiulure...");

			}
			catch(IOException e){
				e.printStackTrace();
				System.exit(1);
			}
		} 
	}

	private void receiveData(DataInputStream reader, String fileName) throws IOException {
		Path path = Paths.get("/Users/Simonegirardi/Desktop/"+ fileName);
		int count;
		byte[] bytes = new byte[1024]; //this is my personal buffer (10KB)
		long size = reader.readLong();
		int received = 0;
		if(size >= 0){
			try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(path, CREATE, APPEND))) {
				while((count = reader.read(bytes)) > 0 ){
					received += count;
					out.write(bytes, 0 , count);
					System.out.println(received/(float)size * 100 + "%");
				}  
				System.out.println("Data saved in: " + path);
			} catch (IOException x) {
				System.err.println(x);
			}
		}
		else
			System.out.println("Error: returned -1, impossible to receive" + fileName);	
	}

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

