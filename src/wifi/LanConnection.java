package wifi;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LanConnection extends JDialog implements ClosableWindow
{
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private Client client;
	private JButton cancelButton;
	private JButton okButton;

	private void startClient(String ip)
	{
		client = new Client(this, ip);
	}

	public LanConnection() 
	{
		setTitle("Connect");
		setBounds(100, 100, 313, 167);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.NORTH);
			panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JLabel lblText = new JLabel("Insert the server IP ");
				panel.add(lblText);
				lblText.setFont(new Font("Tahoma", Font.PLAIN, 14));
			}
		}
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		{
			textField = new JTextField();
			contentPanel.add(textField);
			textField.setColumns(20);
		}

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		{
			okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("textfiel of ip: " + textField.getText());
					startClient(textField.getText());

					//created, wait
					okButton.setText("Waiting for connection");
					okButton.setEnabled(false);
				}
			});
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
		}

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.instance.setVisible(true);

				System.out.println("Cancel clicked");
				if(client != null)
				{
					System.out.println("Destroying client");
					client.destroy();
				}
				destroy();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.out.println("NetworkConfig.java: Closed");
				cancelButton.doClick();
			}
		});


		setVisible(true);
	}

	@Override
	public void destroy() {
		dispose();
	}

	@Override
	public void setVisibility(boolean visible) {
		setVisible(visible);
	}

}
