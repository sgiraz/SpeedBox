package wifi;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
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
import java.awt.Toolkit;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JEditorPane;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;


public class SendBoxGUI extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 7014623789915977930L;
	
	public static String otherIP;
	public int port = 16000;
	private MenuBar mb;
	private Menu m1,m2, m3;
	private MenuItem mi1, mi2, mi3, mi4, mi5;
	private DropListener listener;
	private ClientDataTransfer clientData;
	private ServerDataTransfer serverData;
	private JProgressBar progressBarItem;
	private JSplitPane splitPane;
	private JPanel panelDrop;
	private JLabel lblDropFileHere;

	public static SendBoxGUI instance;
	private JEditorPane chatArea;
	private JPanel itemsPanel;
	private JTable table;


	public SendBoxGUI()
	{
		setFont(new Font("CMU Classical Serif", Font.PLAIN, 12));
		setIconImage(Toolkit.getDefaultToolkit().getImage(SendBoxGUI.class.getResource("/img/icons/16x16.png")));
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
		serverData = new ServerDataTransfer(port);
		// Connect the label with a drag and drop listener
		
		setEnabled(true);
		new DropTarget(this, listener);		
	}
	
	public void free()
	{
		if(clientData != null)
			clientData.destroy();

		if(serverData != null)
			serverData.destroy(); 
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
		progressBarItem = new JProgressBar();
		getContentPane().add(progressBarItem, BorderLayout.SOUTH);
		progressBarItem.setBackground(new Color(255, 255, 255));
		progressBarItem.setForeground(new Color(51, 204, 0));
		progressBarItem.setStringPainted(true);
		progressBarItem.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		progressBarItem.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		progressBarItem.setBounds(77, 170, 146, 20);

		splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setResizeWeight(1.0);
		getContentPane().add(splitPane, BorderLayout.CENTER);

		panelDrop = new JPanel();
		panelDrop.setForeground(Color.GRAY);
		splitPane.setLeftComponent(panelDrop);
		panelDrop.setLayout(new BorderLayout(5, 5));

		lblDropFileHere = new JLabel("Drop file here");
		lblDropFileHere.setForeground(Color.GRAY);
		lblDropFileHere.setHorizontalAlignment(SwingConstants.CENTER);
		lblDropFileHere.setFont(new Font("Calibri Light", Font.PLAIN, 24));
		panelDrop.add(lblDropFileHere);
		
		JPanel rightPanel = new JPanel();
		splitPane.setRightComponent(rightPanel);
		rightPanel.setLayout(new BorderLayout(5, 5));
		
		itemsPanel = new JPanel();
		rightPanel.add(itemsPanel, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_itemsPanel = new GroupLayout(itemsPanel);
		gl_itemsPanel.setHorizontalGroup(
			gl_itemsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_itemsPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(18, Short.MAX_VALUE))
		);
		gl_itemsPanel.setVerticalGroup(
			gl_itemsPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_itemsPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(12, Short.MAX_VALUE))
		);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name","Size","Progress"
			}
		) {
			Class[] columnTypes = new Class[] {
				Object.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane.setViewportView(table);
		itemsPanel.setLayout(gl_itemsPanel);
		
		chatArea = new JEditorPane();
		chatArea.setText("write a message and click on Send");
		rightPanel.add(chatArea, BorderLayout.CENTER);
		
		JButton btnSend = new JButton("Send");
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// SEND THE TEXT STRING THROUGH THE SOCKET
			}
		});
		btnSend.setToolTipText("");
		rightPanel.add(btnSend, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);	
	}
	
	/* IS TEMPORARY */
	public JProgressBar getProgressBar(){
		return this.progressBarItem;
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
		clientData.SendFile(file.getPath(), otherIP, port);
	}

	// this is for backgroud color during the drag and drop
	public JPanel getPane()
	{
		return panelDrop;
	}
}