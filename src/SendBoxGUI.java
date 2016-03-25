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
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import java.awt.ComponentOrientation;
import java.awt.Cursor;

@SuppressWarnings("serial")
public class SendBoxGUI extends JFrame implements ActionListener{

	public static String myIP = "";
	public static String otherIP = "88.147.113.67";
	public static int myPort = 16000;
	public static int otherPort = 16000;

	// list of sending files
	// http://stackoverflow.com/questions/14615888/list-of-jpanels-that-eventually-uses-a-scrollbar

	private JPanel p;
	private JLabel lb;
	private MenuBar mb;
	private Menu m1,m2, m3;
	private MenuItem mi1, mi2, mi3, mi4, mi5;
	private DropListener listener;
	private Client client;
	private Server server;
	private JProgressBar progressBarDown;


	public SendBoxGUI(){	
		p = new JPanel();
		lb = new JLabel("Drop your file here to send ");
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
		setup();
	}

	private void setup() {
		
		lb.setHorizontalAlignment(SwingConstants.CENTER);
		lb.setBounds(51, 119, 197, 39);
		
		mi4.addActionListener(this);
		mi5.addActionListener(this);
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
		setMenuBar(mb);
		p.setLayout(null);
		p.add(lb);
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setSize(300, 300);
		setTitle("SendBox");
		getContentPane().add(p);
		p.add(getProgressBarDown());

		// Connect the label with a drag and drop listener
		new DropTarget(this, listener);							
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		MenuItem mi = (MenuItem) e.getSource();
		System.out.println("In menu', you have clicked " + mi.getLabel());
		
		// USANDO I THREAD VOLEVO METTERE IN WAIT SANDBOX FINO A QUANDO L'UTENTE
		// NON CLICCA SUL BOTTONE SAVE IN PREFERENCES MA NON RIESCO
		switch(mi.getLabel()){
		case "Preferences":										
			new Thread(new Preferences(), "Preferences thread").start();
			break;  			
		case "Quit":			 
			System.exit(0);
			break;
		default:
			System.out.println("Pressed: " + mi.getLabel());
			break;
		}
		
	}
	
	public void drop(File file) {
		System.out.println("Drop: " + file.getPath());
		client.SendFile(file.getPath(), otherIP, otherPort);
	}
	
	public JPanel getPane(){
		return p;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	private JProgressBar getProgressBarDown() {
		if (progressBarDown == null) {
			progressBarDown = new JProgressBar();
			progressBarDown.setStringPainted(true);
			progressBarDown.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			progressBarDown.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			progressBarDown.setBounds(77, 170, 146, 20);
		}
		return progressBarDown;
	}
}