import java.awt.EventQueue;

import javax.swing.UIManager;

public class Main {
	
	public static void main(String[] args) {

		//prova 
		//System.out.println(Utils.getGatewayIP());
		//System.out.println(Utils.getLocalIP());
		System.out.println("Application running..");
		
		if(WindowsNetwork.checkHostednetwork())
			System.out.println("ok");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
					catch (Exception e) { e.printStackTrace(); }
					
					new MainMenu();
					//new SendBoxGUI();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}	
}