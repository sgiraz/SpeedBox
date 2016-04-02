import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OSXNetwork {
	
	public static String setHostednetwork(String name, String password){
		try {
			Process p = Runtime.getRuntime().exec("netsh wlan set hostednetwork mode=allow ssid=\"" + name + "\" key=\""+password+"\"");

			BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line, result = "";
			while((line = output.readLine()) != null) 
				result += line;

			return result;
		}
		catch (IOException e) {
			return null;
		}
	}

	public static String startHostednetwork()
	{
		try {
			Process p = Runtime.getRuntime().exec("netsh wlan start hostednetwork");
			return Utils.getProcessOutput(p);
		}
		catch (IOException e) {
			return null;
		}
	}

	public static String stopHostednetwork()
	{
		try {
			Process p = Runtime.getRuntime().exec("netsh wlan stop hostednetwork");
			return Utils.getProcessOutput(p);
		}
		catch (IOException e) {
			return null;
		}
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
}
