package wifi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WindowsNetwork {

	public static String startHostednetwork(String name, String password)
	{
		String result = executeCommand("netsh wlan set hostednetwork mode=allow ssid=\"" + name + "\" key=\""+password+"\"");
		result += "\n" +  executeCommand("netsh wlan start hostednetwork");
		return result;
	}

	public static String stopHostednetwork()
	{
		executeCommand("netsh wlan stop hostednetwork");
		return executeCommand("netsh wlan set hostednetwork mode=disallow ");
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
