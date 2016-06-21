package wifi;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class MainMenu extends JFrame 
{

	private static final long serialVersionUID = -3088890058631223710L;
	private JComboBox<String> comboBox;
	private final String infoNetworkType = "LAN NETWORK: choose this if you are connected on internet or local network yet.\n"
			+ "DIRECT WIFI: choose this if you can't connect on internet or local network.\n";

	public static MainMenu instance;
	public MainMenu() 
	{
		setUndecorated(true);
		setTitle("Configuration..");
		instance = this;
		setBounds(100, 100, 462, 288);
		getContentPane().setLayout(new BorderLayout(0, 5));

		JPanel panelNorth = new JPanel();
		getContentPane().add(panelNorth, BorderLayout.NORTH);
		panelNorth.setLayout(new BorderLayout(0, 0));

		JPanel panelMenuITitle = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelMenuITitle.getLayout();
		flowLayout.setVgap(10);
		panelNorth.add(panelMenuITitle, BorderLayout.NORTH);

		JLabel lblMainMenu = new JLabel();
		lblMainMenu.setIcon(new ImageIcon(MainMenu.class.getResource("/img/speedbox.png")));
		panelMenuITitle.add(lblMainMenu);
		lblMainMenu.setHorizontalTextPosition(SwingConstants.CENTER);
		lblMainMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainMenu.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JPanel panelMenuSeparator = new JPanel();
		panelNorth.add(panelMenuSeparator, BorderLayout.SOUTH);

		JSeparator separator = new JSeparator();
		separator.setPreferredSize(new Dimension(350, 12));
		panelMenuSeparator.add(separator);

		JPanel panelCentral = new JPanel();
		panelCentral.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panelCentral.setMinimumSize(new Dimension(5, 5));
		getContentPane().add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

		JLabel lblChooseNetworkType = new JLabel("Choose network type:");
		lblChooseNetworkType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblChooseNetworkType.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelCentral.add(lblChooseNetworkType);

		DefaultListCellRenderer dlcr = new DefaultListCellRenderer(); 
		dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER); 

		comboBox = new JComboBox<String>();
		comboBox.setMaximumRowCount(2);
		comboBox.setPreferredSize(new Dimension(140, 22));
		comboBox.setMinimumSize(new Dimension(28, 22));
		comboBox.setRenderer(dlcr); 
		panelCentral.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"LAN NETWORK", "DIRECT WIFI"}));

		JLabel lblHelplabel = new JLabel();
		lblHelplabel.setIcon(new ImageIcon(MainMenu.class.getResource("/img/help_icon.png")));
		lblHelplabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Utils.showInfo(infoNetworkType);
			}
		});
		lblHelplabel.setToolTipText("click for information about ...");
		panelCentral.add(lblHelplabel);

		JPanel panelButtons = new JPanel();
		panelCentral.add(panelButtons);
		panelButtons.setSize(new Dimension(0, 2));
		FlowLayout fl_panelButtons = new FlowLayout(FlowLayout.CENTER);
		fl_panelButtons.setVgap(10);
		fl_panelButtons.setHgap(10);
		panelButtons.setLayout(fl_panelButtons);

		JButton startServerButton = new JButton("Start Server");
		startServerButton.setPreferredSize(new Dimension(100, 27));
		startServerButton.setMargin(new Insets(2, 14, 4, 14));
		startServerButton.setBounds(new Rectangle(0, 0, 2, 2));
		startServerButton.setHorizontalTextPosition(SwingConstants.LEFT);
		panelButtons.add(startServerButton);
		startServerButton.setBorder(UIManager.getBorder("Button.border"));
		startServerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex() == 0){
					new LanNetwork();
				}
				else{
					new DirectNetwork();
				}
			}
		});
		startServerButton.setActionCommand("create");

		JButton connectButton = new JButton("Connect");
		connectButton.setPreferredSize(new Dimension(100, 27));
		connectButton.setMinimumSize(new Dimension(91, 23));
		connectButton.setMaximumSize(new Dimension(91, 23));
		connectButton.setMargin(new Insets(2, 14, 4, 14));
		connectButton.setBounds(new Rectangle(0, 0, 2, 2));
		connectButton.setHorizontalTextPosition(SwingConstants.RIGHT);
		panelButtons.add(connectButton);
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex() == 0){
					new LanConnection();
				}
				else{
					new DirectConnection();
				}
			}
		});
		connectButton.setActionCommand("Connect");

		JSeparator separator_1 = new JSeparator();
		panelCentral.add(separator_1);
		separator_1.setPreferredSize(new Dimension(350, 12));

		JPanel panelSouth = new JPanel();
		getContentPane().add(panelSouth, BorderLayout.SOUTH);
		panelSouth.setLayout(new BorderLayout(0, 0));

		JPanel panelButtonSeparator = new JPanel();
		panelSouth.add(panelButtonSeparator, BorderLayout.NORTH);
		panelButtonSeparator.setLayout(new BorderLayout(0, 0));

		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		panelButtonSeparator.add(btnQuit, BorderLayout.EAST);

		this.addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				System.out.println("MainMenu Window Activated Event");
				setAlwaysOnTop(true);
			}

			public void windowDeactivated(WindowEvent e) {
				System.out.println("MainMenu Window Deactivated Event");
				setAlwaysOnTop(false);
			}
		});




		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
}
