import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class ConnectionNetwork extends JDialog implements Runnable {

	private static final long serialVersionUID = -7111909162789102303L;
	private final JPanel contentPanel = new JPanel();
	private int portNumber = 50000;
	private Socket clientSocket;
	private boolean threadClosed;
	private long keepAliveTime;
	private boolean connected;
	private Timer timer;

	public ConnectionNetwork() {
		setTitle("Waiting for connection...");

		new Thread(this, "try connection..").start();

		setBounds(100, 100, 330, 185);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel labelInstructions = new JLabel("<html>&#8226; switch on your Wi-Fi<br>&#8226;"
					+ " choose the ad-hoc network<br>&#8226;"
					+ " wait for connection...</html>");
			labelInstructions.setBorder(UIManager.getBorder("TextField.border"));
			labelInstructions.setBackground(Color.LIGHT_GRAY);
			labelInstructions.setHorizontalAlignment(SwingConstants.LEFT);
			contentPanel.add(labelInstructions, BorderLayout.NORTH);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						close();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void close(){
		try{
			if(clientSocket != null){
				clientSocket.close();
			}
			threadClosed = true;
			dispose();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		while(!threadClosed)
		{
			try(Socket clientSocket = new Socket()){
				clientSocket.connect(new InetSocketAddress(Utils.getGatewayIP(), portNumber), 1000);
				try(BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
						BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
						DataInputStream input = new DataInputStream(clientSocket.getInputStream())){

					this.clientSocket = clientSocket;

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
						connected = true;
						System.out.println("CLIENT: connection estabilished correctly");
						setVisible(false);
						new SendBoxGUI();

						// starting keepalive
						writer.write("keepalive");
						writer.newLine();
						writer.flush();

						// keep alive timer
						keepAliveTime = System.currentTimeMillis() ;
						timer = new Timer();
						timer.schedule(new TimerTask()
						{
							@Override
							public void run() {
								long diff = System.currentTimeMillis() - keepAliveTime;
								System.out.println("timer: " + diff);
								if(diff > 5000)
								{
									System.out.println("CLOSING READER");
									try { reader.close(); }
									catch (IOException e) { e.printStackTrace(); }
								}
							}
						}, 0, 2000);

						System.out.println("timer started");
						while(clientSocket.isConnected() && !clientSocket.isClosed())
						{
							System.out.println("under the while");
							if((line = reader.readLine()) != null && line.equals("keepalive"))
							{
								keepAliveTime = System.currentTimeMillis();
								writer.write("keepalive");
								writer.newLine();
								writer.flush();

								System.out.println("keepalive received");
							}
							else{
								System.out.print("not keepalive: ");
								System.out.println(line);
							}
							Thread.sleep(1000);
						}
					}
					else
					{
						System.out.println("CLIENT: problem to handshake");
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			catch(IOException e)
			{
				System.out.println("Connection refused from IP: " + Utils.getGatewayIP());
				if(connected){
					close();
					if(SendBoxGUI.instance != null){
						SendBoxGUI.instance.dispose();
					}
					timer.cancel();
					new MainMenu();
				}
				try{ Thread.sleep(3000);}
				catch (InterruptedException excetption) { excetption.printStackTrace(); }
			}

		}
	}


}
