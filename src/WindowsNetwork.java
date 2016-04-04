import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WindowsNetwork {

	public static String setHostednetwork(String name, String password)
	{
		return executeCommand("netsh wlan set hostednetwork mode=allow ssid=\"" + name + "\" key=\""+password+"\"");
	}

	public static String startHostednetwork()
	{
		return executeCommand("netsh wlan start hostednetwork");
	}

	public static String stopHostednetwork()
	{
		return executeCommand("netsh wlan stop hostednetwork");
	}

	public static boolean checkHostednetwork()
	{ 
		try{
			Process proc = Runtime.getRuntime().exec("netsh wlan show hostednetwork");
			BufferedReader output = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			int lines = 0;
			while(output.readLine() != null) 
				lines ++;

			return lines > 14;
		}
		catch (IOException e) {
			return false;
		}
	}

	/**
	 * Returns the process output (like a pipe in bash)
	 * @param proc  the process
	 * @return
	 */
	public static String executeCommand(String command)
	{

		try {
			Process proc = Runtime.getRuntime().exec(command);
			String line, result = "";
			try (BufferedReader output = new BufferedReader(new InputStreamReader(proc.getInputStream()));){
				while((line = output.readLine()) != null) 
					result += line;

				return result;
			}
		}
		catch (IOException e) {
			return null;
		}	 
	}


}
