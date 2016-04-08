package wifi;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Server implements Runnable
{
	private int portNumber = 50000;
	private boolean connected;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private BufferedReader reader;
	private BufferedWriter writer;

	Timer timer;
	private long keepAliveTime;
	private int keepAliveMax = 5000;
	private ClosableWindow window;

	public Server(ClosableWindow ownerWindow)
	{
		window = ownerWindow;
		new Thread(this).start();
	}

	public void run()
	{
		System.out.println("Server.java: waiting for an handshake");

		//accept
		try
		{
			serverSocket = new ServerSocket(portNumber);
			clientSocket = serverSocket.accept();
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

			System.out.println("Server.java: connected to " + clientSocket.getInetAddress());

			// check for input request type
			String line = reader.readLine();

			if(line.equals("handshake"))
			{
				connected = true;
				
				// send acceptation
				System.out.println("Server.java: Sending handshake");
				writer.write("handshake");
				writer.newLine();
				writer.flush();

				// destroy the owner window
				if(window != null)
					window.destroy();

				// create the sand box
				String otherIP = clientSocket.getInetAddress().getHostAddress();

				SendBoxGUI.instance.start(otherIP);
				
				// keep alive timer
				keepAliveTime = System.currentTimeMillis() ;
				timer = new Timer();
				timer.schedule(new TimerTask()
				{
					@Override
					public void run() {
						long diff = System.currentTimeMillis() - keepAliveTime;
						System.out.println("Server.java: timer: " + diff);
						if(diff > keepAliveMax)
						{
							System.out.println("Server.java: Closing reader");
							try { reader.close(); }
							catch (IOException e) { e.printStackTrace(); }
						}
					}
				}, 0, 2000);

				System.out.println("Server.java: timer started");
				while(clientSocket.isConnected() && !clientSocket.isClosed())
				{
					System.out.println("Server.java: under the while");
					if((line = reader.readLine()) != null && line.equals("keepalive"))
					{
						System.out.println("Server.java: keepalive received");
						keepAliveTime = System.currentTimeMillis();
						writer.write("keepalive");
						writer.newLine();
						writer.flush();
					}
					else
					{
						System.out.print("Server.java: not keepalive: ");
						System.out.println(line);
					}

					try{ Thread.sleep(3000);}
					catch (InterruptedException excetption) { excetption.printStackTrace(); }
				}
			}
			else
			{
				System.out.println("Server.java: wrong handshake");
			}


		}
		catch(IOException e)
		{
			System.out.println("Server.java: CATCH (" + e.getMessage() + ")");
		}
		
		if(connected)
		{
			Utils.showWarning("Connection closed");
			System.out.println("Server.java: socket closed by host");
		}

		destroy();
		MainMenu.instance.setEnabled(true);
	}

	public void destroy()
	{ 
		System.out.println("Server.java: destroy()");

		closeStreams();

		if(timer != null)
			timer.cancel();

		SendBoxGUI.instance.free();
	}

	private void closeStreams()
	{
		System.out.println("Server.java: closeStreams()");
		try {
			if(serverSocket != null && !serverSocket.isClosed())
				serverSocket.close(); 
		}
		catch (IOException e) {}

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
