
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
}
