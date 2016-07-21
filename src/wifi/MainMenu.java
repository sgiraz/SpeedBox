package wifi;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class MainMenu extends JDialog 
{

	private static final long serialVersionUID = -3088890058631223710L;
	private JButton startServerButton;
	private JComboBox<String> comboBox;
	private final String infoNetworkType = "LAN NETWORK: choose this if you are connected on internet or local network yet.\n"
			+ "DIRECT WIFI: choose this if you can't connect on internet or local network.\n";

	public static MainMenu instance;
	public MainMenu() 
	{
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setTitle("Configuration..");
		instance = this;
		setBounds(100, 100, 462, 288);
		getContentPane().setLayout(new BorderLayout(0, 5));

		JPanel panelNorth1 = new JPanel();
		getContentPane().add(panelNorth1, BorderLayout.NORTH);
		panelNorth1.setLayout(new BorderLayout(0, 0));

		JPanel panelMenuITitle = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelMenuITitle.getLayout();
		flowLayout.setVgap(10);
		panelNorth1.add(panelMenuITitle, BorderLayout.NORTH);

		JLabel lblMainMenu = new JLabel();
		lblMainMenu.setIcon(new ImageIcon(MainMenu.class.getResource("/img/speedbox.png")));
		panelMenuITitle.add(lblMainMenu);
		lblMainMenu.setHorizontalTextPosition(SwingConstants.CENTER);
		lblMainMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainMenu.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JPanel panelMenuSeparator = new JPanel();
		panelNorth1.add(panelMenuSeparator, BorderLayout.SOUTH);

		JSeparator separator = new JSeparator();
		separator.setPreferredSize(new Dimension(350, 12));
		panelMenuSeparator.add(separator);

		JPanel panelCentral2 = new JPanel();
		panelCentral2.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panelCentral2.setMinimumSize(new Dimension(5, 5));
		getContentPane().add(panelCentral2, BorderLayout.CENTER);
		panelCentral2.setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorth2 = new JPanel();
		panelCentral2.add(panelNorth2, BorderLayout.NORTH);

		JLabel lblChooseNetworkType = new JLabel("Choose network type:");
		panelNorth2.add(lblChooseNetworkType);
		lblChooseNetworkType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblChooseNetworkType.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
				comboBox = new JComboBox<String>();
				panelNorth2.add(comboBox);
				comboBox.setMaximumRowCount(2);
				comboBox.setPreferredSize(new Dimension(140, 22));
				comboBox.setMinimumSize(new Dimension(28, 22));
				comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"LAN NETWORK", "DIRECT WIFI"}));
				
						JLabel lblHelplabel = new JLabel();
						panelNorth2.add(lblHelplabel);
						lblHelplabel.setIcon(new ImageIcon(MainMenu.class.getResource("/img/help_icon.png")));
						lblHelplabel.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent arg0) {
								Utils.showInfo(infoNetworkType);
							}
						});
						lblHelplabel.setToolTipText("click for information about ...");
				comboBox.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						if(comboBox.getSelectedIndex() == 1 && Utils.getOS() == Utils.OSType.OSX){
							startServerButton.setVisible(false);
						}
						else if(comboBox.getSelectedIndex() == 0 && Utils.getOS() == Utils.OSType.OSX && (startServerButton.isVisible() == false)){
							startServerButton.setVisible(true);
						}
					}
				});

		DefaultListCellRenderer dlcr = new DefaultListCellRenderer(); 
		dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER); 

		JPanel panelButtons2 = new JPanel();
		panelCentral2.add(panelButtons2, BorderLayout.CENTER);
		panelButtons2.setSize(new Dimension(0, 2));
		FlowLayout fl_panelButtons2 = new FlowLayout(FlowLayout.CENTER);
		fl_panelButtons2.setVgap(10);
		fl_panelButtons2.setHgap(10);
		panelButtons2.setLayout(fl_panelButtons2);

		startServerButton = new JButton("Start Server");
		startServerButton.setPreferredSize(new Dimension(100, 27));
		startServerButton.setMargin(new Insets(2, 14, 4, 14));
		startServerButton.setBounds(new Rectangle(0, 0, 2, 2));
		startServerButton.setHorizontalTextPosition(SwingConstants.LEFT);
		panelButtons2.add(startServerButton);
		startServerButton.setBorder(UIManager.getBorder("Button.border"));
		startServerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("MainMenu: button \"Start Server\" clicked");
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
		panelButtons2.add(connectButton);
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("MainMenu: button \"Connect\" clicked");
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
		panelButtons2.add(separator_1);
		separator_1.setPreferredSize(new Dimension(350, 12));

		JPanel panelSouth3 = new JPanel();
		getContentPane().add(panelSouth3, BorderLayout.SOUTH);
		panelSouth3.setLayout(new BorderLayout(0, 0));

		JPanel panelButtonSeparator = new JPanel();
		panelSouth3.add(panelButtonSeparator, BorderLayout.NORTH);
		panelButtonSeparator.setLayout(new BorderLayout(0, 0));

		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		panelButtonSeparator.add(btnQuit, BorderLayout.EAST);
		
		getRootPane().setBorder( BorderFactory.createLineBorder(Color.LIGHT_GRAY) );
		setUndecorated(true);
		setLocationRelativeTo(SendBoxGUI.instance);
		setResizable(false);
		setVisible(true);
	}
}
