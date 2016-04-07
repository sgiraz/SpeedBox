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

	ServerSocket serverSocket;
	Socket clientSocket;
	BufferedReader reader;
	BufferedWriter writer;

	Timer timer;
	private long keepAliveTime;
	private ClosableWindow window;

	public Server(ClosableWindow ownerWindow)
	{
		window = ownerWindow;
		new Thread(this).start();
	}

	public void run()
	{
		System.out.println("SERVERWAIT: waiting for an handshake");

		//accept
		try
		{
			serverSocket = new ServerSocket(portNumber);
			clientSocket = serverSocket.accept();
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

			System.out.println("SERVERWAIT: connected to " + clientSocket.getInetAddress());

			// check for input request type
			String line = reader.readLine();

			if(line.equals("handshake"))
			{
				// send acceptation
				System.out.println("SERVER: Sending handshake");
				writer.write("handshake");
				writer.newLine();
				writer.flush();

				// destroy the owner window
				if(window != null)
					window.destroy();

				// create the sand box
				String otherIP = clientSocket.getInetAddress().getHostAddress();
				new SendBoxGUI(otherIP);

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
						System.out.println("keepalive received");
						keepAliveTime = System.currentTimeMillis();
						writer.write("keepalive");
						writer.newLine();
						writer.flush();
					}
					else
					{
						System.out.print("not keepalive: ");
						System.out.println(line);
					}

					try{ Thread.sleep(3000);}
					catch (InterruptedException excetption) { excetption.printStackTrace(); }
				}
			}
			else
			{
				System.out.println("wrong handshake");
			}


		}
		catch(IOException e)
		{
			System.out.println("catch executed");
			e.printStackTrace();
		}

		destroy();
		System.out.println("socket closed by host");
		new MainMenu();
	}

	public void destroy()
	{ 
		System.out.println("Server.java: destroy()");

		closeStreams();

		if(timer != null)
			timer.cancel();

		if(SendBoxGUI.instance != null)
			SendBoxGUI.instance.dispose();
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
