import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LinuxNetwork 
{
	

	//*get network interface
	// iwconfig 2>&1 | grep IEEE
	
	//*turn of connection
	// ifconfig wlp3s0 down

	//*start network interface
	// sudo iwconfig wlp3s0 mode Ad-Hoc

	//*set the name
	// sudo iwconfig wlp3s0 essid NOME

	//*set the password
	// sudo iwconfig wlp3s0 key PASSWORD
	
	//
	// sudo ifconfig wlp3s0 192.168.1.1

	// sudo dhcclient wlp3s0

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
