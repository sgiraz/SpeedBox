import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenu extends JDialog {
   
	private static final long serialVersionUID = 1L;

	public MainMenu() {
		setBounds(100, 100, 338, 156);
		getContentPane().setLayout(null);
		{
			JButton cancelButton = new JButton("Connect");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//TODO: add connection
				}
			});
			cancelButton.setBounds(173, 60, 113, 23);
			getContentPane().add(cancelButton);
			cancelButton.setActionCommand("Cancel");
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
			createButton.setActionCommand("OK");
			getRootPane().setDefaultButton(createButton);
		}
		
		JLabel lblChooseAnOption = new JLabel("Choose an option");
		lblChooseAnOption.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblChooseAnOption.setBounds(29, 25, 163, 23);
		getContentPane().add(lblChooseAnOption);
		

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
