import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
		System.out.println("Server started...");
		while(true){
			try(ServerSocket serverSocket = new ServerSocket(portNumber);
					Socket clientSocket = serverSocket.accept();
					BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					PrintWriter writer = new PrintWriter(clientSocket.getOutputStream())){

				System.out.println("Client: " + clientSocket);

				// check for input request type
				String typeOfInputClient = input.readLine();
				
				if(typeOfInputClient == "send_request"){
					// send acceptation
					System.out.println("Sending confirm...");
					String fileName = input.readLine();
					if(checkResponse(fileName)){
						writer.println("accept_request");
					}
					
					// receiving data
					
					
				}
				
				
				
			}
			catch(IOException e){
				e.printStackTrace();
				System.exit(1);
			}
		} 
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

