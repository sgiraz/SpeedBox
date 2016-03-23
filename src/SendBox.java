import java.awt.GridBagLayout;
import java.awt.dnd.DropTarget;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
  git add --all   //aggiunge i files
  git commit -m "messaggio di commit"  //li salva in locale
  git push  //li carica online
  git pull //per ricevere
 */
public class SendBox {
	private JPanel p;
	private JLabel lb;
	private JFrame f;
	private DropListener listener;

	public SendBox(){
		f = new JFrame("Send Box");
		p = new JPanel();
		lb = new JLabel("Drop your file here");
		listener = new DropListener(); // Create the drag and drop listener
	}
	
	private void setup() {

		p.setLayout(new GridBagLayout());
		p.add(lb);
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		f.add(p);

		// Connect the label with a drag and drop listener
		new DropTarget(f, listener);
		
		//cardLayout.show(f, "uno");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(300, 300);
		f.setLocationRelativeTo(null);
		f.setVisible(true);	
	}

	public static void main(String[] args) {
		SendBox sendBox = new SendBox();
		sendBox.setup();
		new Server(16000);
	}
	
	/*
	 * private void getData(){
		  try {
			ip = InetAddress.getLocalHost();
			System.out.println("Current IP address : " + ip.getHostAddress());

		  } catch (UnknownHostException e) {
			e.printStackTrace();
		  }
	}
	 * 
	 */
	


}