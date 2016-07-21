package wifi;
import java.awt.EventQueue;
import javax.swing.UIManager;

/**
 * NOTA DI PROMEMORIA PER RICORDARE LA NECESSITÀ DI AVERE 2 SOCKET E MANTENERE UN MECCANISMO DI KEEPALIVE:
 * Questo tipo di implementazione è necessaria per rilevare una chiusura inaspettata dell'applicazione 
 * quando non vi è alcun traferimento di file in corso.
 * @author simonegirardi
 *
 */

public class Main
{
	public static void main(String[] args) {

		
		
		System.out.println("Application running..");
		//System.out.println(Utils.getGatewayIP());
		//System.out.println(Utils.getLocalIP());
		//System.out.println("Application running.." + LinuxNetwork.getWirelessInteraface());
		
		if(WindowsNetwork.checkHostednetwork())
			System.out.println("check hostednetwork: ok");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
					catch (Exception e) { e.printStackTrace(); }
					
					for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
					    if ("com.sun.java.swing.plaf.gtk.GTKLookAndFeel".equals(info.getClassName())) {   
					       javax.swing.UIManager.setLookAndFeel(info.getClassName());
					       break;
					     } 
					}
					
					new SendBoxGUI();
					new MainMenu();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
	}	
}