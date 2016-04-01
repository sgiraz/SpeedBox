import java.awt.EventQueue;

public class Main {
	
	public static void main(String[] args) {

		//prova 
		System.out.println(Utils.getGatewayIP());
		System.out.println(Utils.getLocalIP());
		System.out.println(Utils.windowsSetHostednetwork("NuovaReteWindows", "00000000"));
		System.out.println(Utils.windowsStartHostednetwork());
		System.out.println(Utils.windowsStopHostednetwork());
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new SendBoxGUI();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}	
}