import java.net.InetAddress;
import java.net.UnknownHostException;

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
	
	public void getData(){
		try {
			InetAddress ip = InetAddress.getLocalHost();
			System.out.println("Current IP address : " + ip.getHostAddress());

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	} 


}
