import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class StartConfig extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldSSID;
	private JLabel lblNetworkName;
	private JLabel lblPassword;
	private JTextField textFieldPassword;

	/**
	 * Create the dialog.
	 */
	public StartConfig() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 395, 205);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblCreateNetwork = new JLabel("CREATE A NEW NETWORK");
			lblCreateNetwork.setBounds(115, 19, 165, 16);
			contentPanel.add(lblCreateNetwork);
		}


		textFieldSSID = new JTextField(System.getProperty("user.name"));
		textFieldSSID.setBounds(177, 52, 130, 26);
		textFieldSSID.setColumns(10);
		textFieldSSID.addKeyListener(new KeyAdapter(){
			@Override
			public void keyTyped(KeyEvent e) {
				if(checkSSID(textFieldSSID.getText())){
					textFieldSSID.setBackground(Color.green);
				}
				else{
					textFieldSSID.setBackground(Color.red);
				}
			}
		});
		
		textFieldPassword = new JTextField();
		textFieldPassword.setBounds(177, 85, 130, 26);
		textFieldPassword.setColumns(10);
		textFieldPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(checkPassword(textFieldPassword.getText())){
					textFieldSSID.setBackground(Color.green);
				}
				else{
					textFieldSSID.setBackground(Color.red);
				}
			}
		});
		
		contentPanel.add(textFieldSSID);
		contentPanel.add(textFieldPassword);
		contentPanel.add(getLblNetworkName());
		contentPanel.add(getLblPassword());
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnCreate = new JButton("create");
				buttonPane.add(btnCreate);
				btnCreate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// QUA CI VA LA FUNZIONE CHE CREA IL NETWORK
					}
				});
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	/**
	 * 
	 * @param SSID
	 * @return true only if SSID is alphanumeric and ha minimum 6 characters
	 */
	private boolean checkSSID(String SSID) {
		return SSID.matches("[a-zA-Z0-9]+") && SSID.length() >= 6; 
	}
	
	private boolean checkPassword(String password) {
		return password.matches("[a-zA-Z0-9]+") && password.length() >= 8;
	}

	private JLabel getLblNetworkName() {
		if (lblNetworkName == null) {
			lblNetworkName = new JLabel("Network name");
			lblNetworkName.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNetworkName.setBounds(74, 57, 91, 16);
		}
		return lblNetworkName;
	}
	private JLabel getLblPassword() {
		if (lblPassword == null) {
			lblPassword = new JLabel("Password");
			lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
			lblPassword.setBounds(104, 90, 61, 16);
		}
		return lblPassword;
	}
}
