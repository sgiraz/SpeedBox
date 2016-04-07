
public class HostedNetwork 
{
	public static String startHostednetwork(String name, String password)
	{
		switch(Utils.getOS())
		{
		case Windows:
			return WindowsNetwork.startHostednetwork(name, password);
		case OSX:
			return OSXNetwork.startHostednetwork(name, password);
		case Linux:
			return LinuxNetwork.startHostednetwork(name, password);
		default:
			return null;
		}
	}

	public static String stopHostednetwork()
	{
		switch(Utils.getOS())
		{
		case Windows:
			return WindowsNetwork.stopHostednetwork();
		case OSX:
			return OSXNetwork.stopHostednetwork();
		case Linux:
			return LinuxNetwork.stopHostednetwork();
		default:
			return null;
		}
	}

	public static boolean checkHostednetwork()
	{ 
		switch(Utils.getOS())
		{
		case Windows:
			return WindowsNetwork.checkHostednetwork();
		case OSX:
			return OSXNetwork.checkHostednetwork();
		case Linux:
			return LinuxNetwork.checkHostednetwork();
		default:
			return false;
		}
	}


}
