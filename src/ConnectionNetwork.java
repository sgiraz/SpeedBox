import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ConnectionNetwork extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6L;
	private final JPanel contentPanel = new JPanel();
	private int portNumber = 50000;

	
	/**
	 * Create the dialog.
	 */
	public ConnectionNetwork() {
		
		// print istruction for user to connect ad-hoc network
		// ...
		// waiting for user to connect ad-hoc network
		// ...
		// when he's connected send an hadshake message to Server
		// ...
		// run SendBox
		
		ConnectionNetwork dialog = new ConnectionNetwork();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				// if user click on this button, turn on the last JDialog
			}
		}
	}
	
	private void makeHandshake(){
		System.out.println("SERVERWAIT: waiting for an handshake");
		try(Socket clientSocket = new Socket(Utils.getGatewayIP(), portNumber);
				BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
				DataInputStream input = new DataInputStream(clientSocket.getInputStream())){

			System.out.println("CLIENT: connected to " + clientSocket.getInetAddress());

			// check for input request type
			String line = reader.readLine(); 

			if(line.equals("handshake")){
				// send acceptation
				System.out.println("SERVER: Sending handshake");

				writer.write("handshake");
				writer.newLine();
				writer.flush();
			}
			else
			{
				System.out.println("faiulure...");
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

}
