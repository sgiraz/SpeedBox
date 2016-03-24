import java.awt.GridBagLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SendBox {

	public static String myIP = "";
	public static String otherIP = "88.147.113.67";
	public static int myPort = 16000;
	public static int otherPort = 16000;
	
	// list of sending files
	// http://stackoverflow.com/questions/14615888/list-of-jpanels-that-eventually-uses-a-scrollbar
	
	private JPanel p;
	private JLabel lb;
	private JFrame f;
	private MenuBar mb;
	private Menu m1,m2, m3;
	private MenuItem mi1, mi2, mi3, mi4, mi5;
	private DropListener listener;
	private Client client;
	private Server server;
	
	

	public SendBox(){	
		f = new JFrame("Send Box");
		p = new JPanel();
		lb = new JLabel("Drop your file here");
		mb = new MenuBar();
		m1 = new Menu("File");
		m2 = new Menu("Edit");
		m3 = new Menu("Help");
		mi1 = new MenuItem("New");
		mi2 = new MenuItem("Open");
		mi3 = new MenuItem("Save");
		mi4 = new MenuItem("Preferences");
		mi5 = new MenuItem("Quit");
		listener = new DropListener(this);
		client = new Client();
		server = new Server(myPort);
		setup();
	}
	 

	public void drop(File file) {
		System.out.println("Drop: " + file.getPath());
		client.SendFile(file.getPath(), otherIP, otherPort);
	}

	private void setup() {
		/*
		mi5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {		QUESTO FUNZIONA, MA BISOGNA FARE UNA FUNZIONE A PARTE 
				System.exit(0);									NON TROPPO PORCOSA CHE LAVORI CON TUTTI I MENU' ITEM
				
			}
		});
		*/
		mb.add(m1);
		mb.add(m2);
		m1.add(mi1);
		m1.add(mi2);
		m1.add(mi3);
		m1.addSeparator();
		m1.add(mi4);
		m1.addSeparator();
		m1.add(mi5);
		mb.setHelpMenu(m3);
		f.setMenuBar(mb);
		
		p.setLayout(new GridBagLayout());
		p.add(lb);
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		f.add(p);

		// Connect the label with a drag and drop listener
		new DropTarget(f, listener);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(300, 300);
		f.setLocationRelativeTo(null);
		f.setVisible(true);	
	}
	
}