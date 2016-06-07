package wifi;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.FlowLayout;


public class SendBoxGUI extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 7014623789915977930L;
	
	public static String otherIP;
	public int myPort = 16000;
	public int otherPort = 16000;
	private MenuBar mb;
	private Menu m1,m2, m3;
	private MenuItem mi1, mi2, mi3, mi4, mi5;
	private DropListener listener;
	private ClientDataTransfer clientData;
	private ServerDataTransfer serverData;
	private JProgressBar progressBarDown;
	private JSplitPane splitPane;
	private JScrollPane scrollPane;
	private JPanel panelDrop;
	private JLabel lblDropFileHere;

	public static SendBoxGUI instance;
	private JPanel panel;

	public SendBoxGUI()
	{
		this.setEnabled(false);
		instance = this;
		
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
	

	public void start(String ip)
	{
		System.out.println("SendBoxGUI.java: Started!");
		
		MainMenu.instance.setVisible(false);
		
		otherIP = ip;
		clientData = new ClientDataTransfer();
		serverData = new ServerDataTransfer(myPort);
		// Connect the label with a drag and drop listener
		new DropTarget(this, listener);		

		setEnabled(true);
	}
	
	public void free()
	{
		if(clientData != null)
			clientData.destroy();

		if(serverData != null)
			serverData.destroy(); 

		setEnabled(false);
	}
 
	
	private void setup() 
	{
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
		setSize(640, 480);
		setTitle("SpeedBox");

		splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.7);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		{
			scrollPane = new JScrollPane();
			splitPane.setRightComponent(scrollPane);
		}

		panelDrop = new JPanel();
		panelDrop.setForeground(Color.GRAY);
		splitPane.setLeftComponent(panelDrop);
		panelDrop.setLayout(null);

		lblDropFileHere = new JLabel("Drop file here");
		lblDropFileHere.setForeground(Color.GRAY);
		lblDropFileHere.setHorizontalAlignment(SwingConstants.CENTER);
		lblDropFileHere.setFont(new Font("Calibri Light", Font.PLAIN, 24));

		lblDropFileHere.setBounds(145, 185, 163, 65);
		panelDrop.add(lblDropFileHere);
		
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		progressBarDown = new JProgressBar();
		panel.add(progressBarDown);
		progressBarDown.setStringPainted(true);
		progressBarDown.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		progressBarDown.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		progressBarDown.setBounds(77, 170, 146, 20);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);	
	}
	
	/* IS TEMPORARY */
	public JProgressBar getProgressBar(){
		return this.progressBarDown;
	}

	// TODO: Preferences of application
	@Override
	public void actionPerformed(ActionEvent e)
	{
		MenuItem mi = (MenuItem) e.getSource();
		System.out.println("In menu', you have clicked " + mi.getLabel());

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

	public void drop(File file) 
	{
		System.out.println("Drop: " + file.getPath() + " to " + otherIP);
		clientData.SendFile(file.getPath(), otherIP, otherPort);
	}

	// this is for backgroud color during the drag and drop
	public JPanel getPane()
	{
		return panelDrop;
	}
}