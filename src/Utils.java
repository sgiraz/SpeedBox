import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.StringTokenizer;

public class Utils {

	/**
	 * 
	 * @param path
	 * @return file name sent from client to server
	 */
	public static String pathGetFilename(String path)
	{
		int pos = path.lastIndexOf("\\");
		if(pos <= 0)
			pos = path.lastIndexOf("/");

		if (pos > 0)
			return path.substring(pos + 1);

		return "";
	}
	
	/**
	 * Display some information about network as ifconfig command
	 */
	public static boolean ifConfig(){
	        Enumeration<NetworkInterface> nets;
			try {
				nets = NetworkInterface.getNetworkInterfaces();
				for (NetworkInterface netint : Collections.list(nets))
		            displayInterfaceInformation(netint);
			} catch (SocketException e) {
				return false;
			} 
			return true;

	}

	/**
	 * You can discover if a network interface is x (that is, running) with the isUP() method.
	 * The following methods indicate the network interface type:
	 * - isLoopback() indicates if the network interface is a loopback interface.
	 * - isPointToPoint() indicates if the interface is a point-to-point interface.
	 * - isVirtual() indicates if the interface is a virtual interface.
	 * @param netint
	 * @throws SocketException
	 */
	private static void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
		System.out.printf("Display name: %s\n", netint.getDisplayName());
		System.out.printf("Name: %s\n", netint.getName());
		Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();

		for (InetAddress inetAddress : Collections.list(inetAddresses)) {
			System.out.printf("InetAddress: %s\n", inetAddress);
		}

		System.out.printf("Up? %s\n", netint.isUp());
		System.out.printf("Loopback? %s\n", netint.isLoopback());
		System.out.printf("PointToPoint? %s\n", netint.isPointToPoint());
		System.out.printf("Supports multicast? %s\n", netint.supportsMulticast());
		System.out.printf("Virtual? %s\n", netint.isVirtual());
		System.out.printf("Hardware address: %s\n",
				Arrays.toString(netint.getHardwareAddress()));
		System.out.printf("MTU: %s\n", netint.getMTU());
		System.out.printf("\n");
	}

	/**
	 * @return the string of current assigned ip address
	 */
	public static String getLocalIP(){
		try {
			InetAddress ip = InetAddress.getLocalHost();
			return ip.getHostAddress();	
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return ("Host Unknow");
		}		
	}

	public static String getGatewayIP()
	{
		try{
			Process result = Runtime.getRuntime().exec("netstat -rn");
			BufferedReader output = new BufferedReader(new InputStreamReader(result.getInputStream()));

			String line;
			int found = 0;
			while((line = output.readLine()) != null)
			{
				line = line.trim();
				if ( line.startsWith("0.0.0.0"))
				{
					found = 1;
					break;		 
				}
				else if(line.startsWith("default")){
					found = 2;
					break;	
				}
			} 
			if(found != 0)
			{
				StringTokenizer st = new StringTokenizer( line );

				st.nextToken();
				if(found == 1){
					st.nextToken();
				}
				return st.nextToken(); //gateway 
			}

		}catch( Exception e ) { 
			e.printStackTrace();
		}
		return null;
	}

	
	
	public static final String windowsReadRegistry(String location, String key){
		try {
			// Run reg query, then read output with StreamReader (internal class)
			Process process = Runtime.getRuntime().exec("reg query " + 
					'"'+ location + "\" /v " + key);

			InputStream is = process.getInputStream();
			StringWriter sw = new StringWriter();
			int c;
			while ((c = is.read()) != -1)
				sw.write(c);

			process.waitFor(); 
			String output = sw.toString();

			int i = output.indexOf("REG_SZ");
			if (i == -1) 
				return null;

			StringBuilder sb = new StringBuilder();
			i += 6; // skip REG_SZ

			// skip spaces or tabs
			for (;;)
			{
				if (i > output.length())
					break;
				char c1 = output.charAt(i);
				if (c1 != ' ' && c1 != '\t')
					break;
				++i;
			}

			// take everything until end of line
			for (;;)
			{
				if (i > output.length())
					break;
				char c1 = output.charAt(i);
				if (c1 == '\r' || c1 == '\n')
					break;
				sb.append(c1);
				++i;
			}

			return sb.toString();
		}
		catch (Exception e) {
			return null;
		}

	}

	private final static java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(
			"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

	public static boolean isIPValid(final String ipv4)
	{ 
		java.util.regex.Matcher matcher = pattern.matcher(ipv4);
		return matcher.matches();             
	}

	public static final String getPublicIP()
	{
		URL url = null;
		final String[] websites = new String[]{
				"http://checkip.amazonaws.com",
				"http://myexternalip.com/raw",
				"https://wtfismyip.com/text",
				"https://api.ipify.org/"};

		for(String website : websites)
		{
			try {
				url = new URL(website);
			}
			catch (MalformedURLException e) {
				continue;
			}

			try(BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))){
				String line = in.readLine();
				if(isIPValid(line))
					return line;
			}
			catch (IOException e) {
				continue;		
			}
		}
		return null;
	}
}
