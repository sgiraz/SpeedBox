import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
 
public class WaitConnection extends JFrame {
	private static final long serialVersionUID = -8611725700060761275L;
	
	private JPanel contentPane;
	private int portNumber = 50000;
 
	public WaitConnection() {
		
		setResizable(false);
		setTitle("Connecting...");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 455, 199);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel labelTitle = new JLabel("Waiting for synchronization");
		labelTitle.setBackground(Color.WHITE);
		labelTitle.setFont(new Font("Tahoma", Font.PLAIN, 24));
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle.setBounds(10, 10, 414, 53);
		contentPane.add(labelTitle);
		
		JTextArea textareaDescription = new JTextArea();
		textareaDescription.setWrapStyleWord(true);
		textareaDescription.setLineWrap(true);
		textareaDescription.setEditable(false);
		textareaDescription.setText("In the other pc, connect to the created hostednetwork and wait for the synchronization process.");
		textareaDescription.setBounds(20, 74, 404, 82);
		contentPane.add(textareaDescription);
		
		setVisible(true);
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				startWaitSocket();
			}
		});
		 
	}
	
	private void startWaitSocket()
	{
		System.out.println("SERVERWAIT: waiting for an handshake");
		try(ServerSocket serverSocket = new ServerSocket(portNumber);
				Socket clientSocket = serverSocket.accept();
				BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
				DataInputStream input = new DataInputStream(clientSocket.getInputStream())){

			System.out.println("SERVERWAIT: connected to " + clientSocket.getInetAddress());

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
			System.exit(1);
		}
	}
}
