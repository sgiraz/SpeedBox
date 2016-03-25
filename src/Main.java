import java.awt.EventQueue;

public class Main {
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String winScopeAddress = Utils.windowsReadRegistry("HKEY_LOCAL_MACHINE\\SYSTEM\\CurrentControlSet\\Services\\SharedAccess\\Parameters", "ScopeAddress");
					System.out.println(winScopeAddress);
					new SendBoxGUI();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
