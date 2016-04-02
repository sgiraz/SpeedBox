import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConnectionNetwork extends JDialog implements Runnable {

	private final JPanel contentPanel = new JPanel();
	private int portNumber = 50000;

	public ConnectionNetwork() {
		// print istruction for user to connect ad-hoc network
		// ...
		// waiting for user to connect ad-hoc network
		// ...
		// when he's connected send an hadshake message to Server
		// ...
		// run SendBox
		
		new Thread().start();
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
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	@Override
	public void run() {

		try(Socket clientSocket = new Socket(Utils.getGatewayIP(), portNumber);
				BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
				DataInputStream input = new DataInputStream(clientSocket.getInputStream())){

			System.out.println("CLIENT: connected to " + clientSocket.getInetAddress());

			// send input for request connection type
			System.out.println("CLIENT: Sending handshake...");
			writer.write("handshake");
			writer.newLine();
			writer.flush();

			// receive acceptation
			String line = reader.readLine();

			if((line.equals("handshake"))){
				// your are connected
				System.out.println("CLIENT: connection estabilished correctly");

			}
			else
			{
				// do something...
			}

		}
		catch(IOException e){
			e.printStackTrace();
		}
	}


}
