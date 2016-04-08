package wifi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class LanNetwork extends JDialog {

	private static final long serialVersionUID = 8237689034968088432L;
	private final JPanel contentPanel = new JPanel();

	public LanNetwork() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 325, 147);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblYourIpIs = new JLabel("Your IP is: " + Utils.getLocalIP());
			contentPanel.add(lblYourIpIs, BorderLayout.NORTH);
			lblYourIpIs.setHorizontalAlignment(SwingConstants.CENTER);
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JSeparator separator = new JSeparator();
				panel.add(separator, BorderLayout.NORTH);
			}
			{
				JLabel lblNewLabel = new JLabel("Waiting for establishing a connection...");
				panel.add(lblNewLabel, BorderLayout.CENTER);
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
