import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LinuxNetwork 
{
	private static String wirelessInterface = null;

	public static String getWirelessInteraface()
	{
		if(wirelessInterface == null)
		{
			wirelessInterface = executeCommand("iwconfig 2>&1 | grep IEEE | cut -f 1 -d \" \"");
			System.out.println("Linu wireless interface: " +wirelessInterface);
		}
		return wirelessInterface;
	}

	//*turn of connection
	// ifconfig wlp3s0 down

	//*start network interface
	// sudo iwconfig wlp3s0 mode Ad-Hoc

	//*set the name
	// sudo iwconfig wlp3s0 essid NOME

	//*set the password
	// sudo iwconfig wlp3s0 key PASSWORD

	//

	// sudo dhcclient wlp3s0

	private static final String networkIP = "192.168.1.1";

	public static String startHostednetwork(String name, String password)
	{
		String result = executeCommand("ifconfig "+getWirelessInteraface()+" down");
		result += "\n" + executeCommand("iwconfig "+getWirelessInteraface()+" mode ad-hoc");
		result += "\n" + executeCommand("iwconfig "+getWirelessInteraface()+" essid " + name);
		result += "\n" + executeCommand("iwconfig "+getWirelessInteraface()+" key " + password);
		result += "\n" + executeCommand("ifconfig "+getWirelessInteraface()+" " + networkIP);

		return result;
	}

	public static String stopHostednetwork()
	{
		String result = executeCommand("ifconfig "+getWirelessInteraface()+" down");
		result += "\n" + executeCommand("iwconfig "+getWirelessInteraface()+" mode managed");
		result += executeCommand("ifconfig "+getWirelessInteraface()+" up");
		return result;
	}

	public static boolean checkHostednetwork()
	{ 
		throw new RuntimeException("Not implemented");
	}

	public static String executeCommand(String command)
	{	
		String[] cmd = { "/bin/sh", "-c", command };

		try {

			Process p = Runtime.getRuntime().exec(cmd);

			try(BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));){
				String line, result = "";
				while((line = output.readLine()) != null) 
				{
					result += line;

				}
				return result;
			}

		}
		catch (IOException e) {
			return null;
		}

	}


}