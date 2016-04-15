package wifi;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class LanConnection extends JDialog implements ClosableWindow
{
	private static final long serialVersionUID = 1L;
	private final JPanel panelNorth = new JPanel();
	private JTextField textField;
	private Client client;
	private JButton cancelButton;
	private JButton okButton;
	private JPanel panel;
	private JPanel panelCenter;

	private void startClient(String ip)
	{
		client = new Client(this, ip);
	}

	public LanConnection()
	{
		MainMenu.instance.setVisible(false);
		setTitle("Connect");
		setBounds(100, 100, 288, 140);
		getContentPane().setLayout(new BorderLayout());
		panelNorth.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panelNorth, BorderLayout.NORTH);
		panelNorth.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblText = new JLabel("Insert the server IP ");
			panelNorth.add(lblText, BorderLayout.NORTH);
			lblText.setHorizontalAlignment(SwingConstants.CENTER);
			lblText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		{
			panel = new JPanel();
			panelNorth.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JSeparator separator = new JSeparator();
				panel.add(separator, BorderLayout.NORTH);
			}
		}
		{
			panelCenter = new JPanel();
			getContentPane().add(panelCenter, BorderLayout.CENTER);
			{
				textField = new JTextField();
				panelCenter.add(textField);
				textField.setColumns(20);
			}
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
		setAlwaysOnTop(true);
		setUndecorated(true);
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
