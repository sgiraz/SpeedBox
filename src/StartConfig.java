import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class StartConfig extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			StartConfig dialog = new StartConfig();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public StartConfig() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblCreateNetwork = new JLabel("create a new network");
			lblCreateNetwork.setBounds(205, 16, 133, 16);
			contentPanel.add(lblCreateNetwork);
		}
		{
			JButton btnCreate = new JButton("create");
			btnCreate.setBounds(350, 11, 94, 29);
			contentPanel.add(btnCreate);
		}
		{
			JLabel lblClickHereTo = new JLabel("connect to a network");
			lblClickHereTo.setBounds(205, 44, 133, 16);
			contentPanel.add(lblClickHereTo);
		}
		{
			JButton btnConnect = new JButton("connect");
			btnConnect.setBounds(350, 39, 94, 29);
			contentPanel.add(btnConnect);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
