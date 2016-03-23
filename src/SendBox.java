import java.awt.GridBagLayout;
import java.awt.dnd.DropTarget;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*per inviare
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

		// Create the drag and drop listener
		listener = new DropListener();
	}
	
	private void setup() {

		p.setLayout(new GridBagLayout());
		p.add(lb);
		p.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
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
	}


}