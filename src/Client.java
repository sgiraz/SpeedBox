
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Client implements Runnable
{
	private int portNumber = 50000;
	private Socket clientSocket;
	private long keepAliveTime;
	private boolean connected;
	private Timer timer;
	private ClosableWindow window;
	private BufferedWriter writer;
	private BufferedReader reader;

	public Client(ClosableWindow ownerWindow)
	{
		window = ownerWindow;
		new Thread(this).start();
	}

	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				clientSocket = new Socket();
				clientSocket.connect(new InetSocketAddress(Utils.getGatewayIP(), portNumber), 1000);
				reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

				System.out.println("Client.java: connected to " + clientSocket.getInetAddress());

				// send input for request connection type
				System.out.println("Client.java: Sending handshake...");
				writer.write("handshake");
				writer.newLine();
				writer.flush();

				// receive acceptation
				String line = reader.readLine();

				if((line.equals("handshake")))
				{
					connected = true;
					System.out.println("Client.java: connection estabilished correctly");

					// close the previous window and start the sending box
					if(window != null)
						window.destroy();

					// create sendbox GUI
					String otherIP = Utils.getGatewayIP();
					new SendBoxGUI(otherIP);

					// begin keepalive
					writer.write("keepalive");
					writer.newLine();
					writer.flush();

					// keep alive timer
					keepAliveTime = System.currentTimeMillis() ;
					timer = new Timer();
					timer.schedule(new TimerTask()
					{
						@Override
						public void run()
						{
							long diff = System.currentTimeMillis() - keepAliveTime;
							System.out.println("Client.java: timer: " + diff);
							if(diff > 5000)
							{
								System.out.println("Client.java: Closing Reader");
								try { reader.close(); }
								catch (IOException e) { e.printStackTrace(); }
							}
						}
					}, 0, 2000);

					System.out.println("Client.java: Timer started");
					while(clientSocket.isConnected() && !clientSocket.isClosed())
					{
						System.out.println("Client.java: under the while");
						if((line = reader.readLine()) != null && line.equals("keepalive"))
						{
							keepAliveTime = System.currentTimeMillis();
							writer.write("keepalive");
							writer.newLine();
							writer.flush();

							System.out.println("Client.java: keepalive received");
						}
						else
						{
							System.out.print("Client.java: not keepalive: ");
							System.out.println(line);
						}

						Thread.sleep(1000);
					}
				}
				else
				{
					System.out.println("Client.java: problem to handshake");
				}
			}
			catch(Exception e)
			{
				if(connected)
					break;

				closeStreams();

				System.out.println("Client.java: Connection refused from IP: " + Utils.getGatewayIP());
				try{ Thread.sleep(3000);}
				catch (InterruptedException excetption) { excetption.printStackTrace(); }
			}
		}

		destroy();
		if(connected)
		{
			Utils.showWarning("Connection closed");
			System.out.println("Client.java: socket closed by host");
		}
		new MainMenu();
	}

	public void destroy()
	{
		System.out.println("Client.java: destroy()");

		closeStreams();

		if(timer != null)
			timer.cancel();

		if(SendBoxGUI.instance != null)
			SendBoxGUI.instance.dispose();
	}

	private void closeStreams()
	{
		try {
			if(clientSocket != null && !clientSocket.isClosed())
				clientSocket.close();
		}
		catch (IOException e) {}

		try {
			if(reader != null)
				reader.close();
		}
		catch (IOException e) {}

		try {
			if(writer != null)
				writer.close();
		}
		catch (IOException e) {}
	}
}

