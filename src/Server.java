import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Il client invia al server il nome di un file (di testo), preso dalla riga di comando.
 * Il server risponde spedendo al client il contenuto, riga per riga, di tale file.
 * Sono gestite le situazioni particolari (file not found, etc.)
 */ 
public class Server {
	private int portNumber;
	private final int FILE_NOT_EXIST = -1;
	
	public Server(int portNumber){
		this.portNumber = portNumber;
		start();
	}
	
	public void start() {
		System.out.println("Server started...");
		while(true){
			try(ServerSocket serverSocket = new ServerSocket(portNumber);
					Socket clientSocket = serverSocket.accept();
					BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					BufferedOutputStream output = new BufferedOutputStream(clientSocket.getOutputStream());
					DataOutputStream writer = new DataOutputStream(output)){

				System.out.println("Client: " + clientSocket);

				// check for existing file
				String path = input.readLine();
				byte bytes[] = new byte[1024*16];

				// send file
				System.out.println("File request: " + path);
				File file = new File(path);
				int pos;
				if(file.exists()){
					try(FileInputStream fs = new FileInputStream(file)){
						// send for dimension
						writer.writeLong(file.length());
						
						// send data
						while((pos = fs.read(bytes)) > 0 ){
							output.write(bytes,0,pos); 
						} 
					}
				}
				else
					writer.writeLong(FILE_NOT_EXIST);
			}
			catch(IOException e){
				e.printStackTrace();
				System.exit(1);
			}
		} 
	}
}

