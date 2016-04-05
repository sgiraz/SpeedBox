import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class Preferences extends JFrame implements Runnable {
	private JList<Object> listCategoriesPref;
	private JTextField textFieldNetworkIP;
	private JLabel lblNetworkIp;
	private JButton btnAdd;
	private JLabel lblListOfNetworks;
	
	private JTextField textFieldUsername;
	private JLabel lblUsername;
	private JList<String> JListUsersIP;
	private DefaultListModel<String> listModel;
	private JButton btnConfirm;
	private JButton btnRemoveUserIP;
	private JScrollPane scrollPane;

	/**
	 * Launch the application (Thread).
	 */
	@Override
	public void run() {
		try {
			Preferences frame = new Preferences();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public Preferences() {
		setDefaultListUserIPModel();
		setType(Type.UTILITY);
		setTitle("Preferences");
		setBounds(100, 100, 450, 312);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().add(getListCategoriesPref());
		getContentPane().add(getTextFieldNetworkIP());
		getContentPane().add(getLblNetworkIp());
		getContentPane().add(getBtnAdd());
		getContentPane().add(getLblListOfNetworks());
		getContentPane().add(getTextFieldUsername());
		getContentPane().add(getLblUsername());
		getContentPane().add(getBtnConfirm());
		getContentPane().add(getBtnRemoveUserIP());
		getContentPane().add(getScrollPane());

	}
	
	private void setDefaultListUserIPModel(){
		listModel = new DefaultListModel<String>();
	}
	
	/**
	 * Main list of preferences type
	 * @return
	 */
	private JList<Object> getListCategoriesPref() {
		if (listCategoriesPref == null) {
			listCategoriesPref = new JList<Object>();
			listCategoriesPref.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listCategoriesPref.setBorder(UIManager.getBorder("TextField.border"));
			listCategoriesPref.setModel(new AbstractListModel<Object>() {
				String[] values = new String[] {"Generals", "Network"};
				public int getSize() {
					return values.length;
				}
				public Object getElementAt(int index) {
					return values[index];
				}
			});
			listCategoriesPref.setBounds(17, 19, 92, 246);
		}
		return listCategoriesPref;
	}
	private JTextField getTextFieldNetworkIP() {
		if (textFieldNetworkIP == null) {
			textFieldNetworkIP = new JTextField();
			textFieldNetworkIP.setToolTipText("es. 192.168.0.1");
			textFieldNetworkIP.setName("");
			textFieldNetworkIP.setBounds(290, 19, 140, 26);
			textFieldNetworkIP.setColumns(10);
		}
		return textFieldNetworkIP;
	}
	private JLabel getLblNetworkIp() {
		if (lblNetworkIp == null) {
			lblNetworkIp = new JLabel("Network IP:");
			lblNetworkIp.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNetworkIp.setBounds(213, 24, 75, 16);
		}
		return lblNetworkIp;
	}
	
	/**
	 * when this button is pressed, check for IP validate and return it
	 * @return 
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton("add");
			btnAdd.setPreferredSize(new Dimension(75, 30));
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!textFieldNetworkIP.getText().isEmpty() && Utils.isIPValid(textFieldNetworkIP.getText()) && !textFieldUsername.getText().isEmpty()){
						String ip = textFieldNetworkIP.getText();
						String username = textFieldUsername.getText();
						listModel.addElement(username + " " + ip);
						// QUI POSSIAMO RITORNARE L'IP INSERITO DALL'UTENTE DA SETTARE COME IP DA DARE AL CLIENT PER COMUNICARE CON IL SERVER
					}
				}
			});
			btnAdd.setBounds(355, 90, 75, 29);
		}
		return btnAdd;
	}
	
	/**
	    * Validate ip address with regular expression
	    * @param ip ip address for validation
	    * @return true valid ip address, false invalid ip address
	    */
		
	   
	
	private JLabel getLblListOfNetworks() {
		if (lblListOfNetworks == null) {
			lblListOfNetworks = new JLabel("Networks list:");
			lblListOfNetworks.setBounds(121, 113, 118, 16);
		}
		return lblListOfNetworks;
	}
	private JTextField getTextFieldUsername() {
		if (textFieldUsername == null) {
			textFieldUsername = new JTextField();
			textFieldUsername.setColumns(10);
			textFieldUsername.setBounds(290, 52, 140, 26);
		}
		return textFieldUsername;
	}
	private JLabel getLblUsername() {
		if (lblUsername == null) {
			lblUsername = new JLabel("Username:");
			lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
			lblUsername.setBounds(213, 57, 75, 16);
		}
		return lblUsername;
	}
	private JList<String> getJListUsersIP() {
		if (JListUsersIP == null) {
			JListUsersIP = new JList<String>(listModel);
		}
		return JListUsersIP;
	}
	private JButton getBtnConfirm() {
		if (btnConfirm == null) {
			btnConfirm = new JButton("confirm");
			btnConfirm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// salva i dati in un file e chiudi la finestra delle preferences
					dispose();
				}
			});
			btnConfirm.setPreferredSize(new Dimension(93, 30));
			btnConfirm.setBounds(355, 236, 75, 29);
		}
		return btnConfirm;
	}
	
	/**
	 * Remove the selected item and delete from file
	 * @return
	 */
	private JButton getBtnRemoveUserIP() {
		if (btnRemoveUserIP == null) {
			btnRemoveUserIP = new JButton("remove");
			btnRemoveUserIP.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!JListUsersIP.isSelectionEmpty()){
						//String[] user_IP = ((String)JListUsersIP.getSelectedValue()).split(" "); // [0] user [1] IP
						listModel.remove(JListUsersIP.getSelectedIndex());
					}
				}
			});
			btnRemoveUserIP.setPreferredSize(new Dimension(90, 30));
			btnRemoveUserIP.setBounds(121, 236, 75, 29);
		}
		return btnRemoveUserIP;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(121, 131, 309, 103);
			scrollPane.setViewportView(getJListUsersIP());
		}
		return scrollPane;
	}
}
