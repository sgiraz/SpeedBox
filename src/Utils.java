import java.io.InputStream;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

/*
git add --all   //aggiunge i files
git commit -m "messaggio di commit"  //li salva in locale
git push  //li carica online
git pull //per ricevere
*/

public class Utils {

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
	 * this function should be return the ip address of Ad-hoc network (for client)
	 */
	public void getNetworkIp(){
		// .......
	}
	
	/**
	 * Display some information about network as ifconfig command
	 */
	public boolean ifConfig(){
		 
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
	private void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
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
	public String getData(){
		try {
			InetAddress ip = InetAddress.getLocalHost();
			System.out.println("Current IP address : " + ip.getHostAddress());
			return ip.getHostAddress().toString();	
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return ("Host Unknow");
		}
		
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
}