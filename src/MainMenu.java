import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainMenu extends JFrame 
{

	private static final long serialVersionUID = -3088890058631223710L;

	public static MainMenu instance;
	public MainMenu() 
	{
		instance = this;
		setBounds(100, 100, 338, 156);	
		getContentPane().setLayout(null);
		{
			JButton connectButton = new JButton("Connect");
			connectButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new ConnectionNetwork();
				}
			});
			connectButton.setBounds(173, 60, 131, 23);
			getContentPane().add(connectButton);
			connectButton.setActionCommand("Connect");
		}
		{
			JButton createButton = new JButton("Create Network");
			createButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new NetworkConfig();
				}
			});
			createButton.setBounds(29, 60, 131, 23);
			getContentPane().add(createButton);
			createButton.setActionCommand("create");
		}
		
		JLabel lblChooseAnOption = new JLabel("Choose an option");
		lblChooseAnOption.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblChooseAnOption.setBounds(29, 25, 163, 23);
		getContentPane().add(lblChooseAnOption);
		

		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
		setVisible(true);
	}

}
