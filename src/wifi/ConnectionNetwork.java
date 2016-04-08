package wifi;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class ConnectionNetwork extends JDialog implements ClosableWindow
{
	private static final long serialVersionUID = -7111909162789102303L;
	private final JPanel contentPanel = new JPanel();

	private static Client client;

	public ConnectionNetwork()
	{
		setTitle("Waiting for connection...");
		MainMenu.instance.setVisible(false);

		client = new Client(this);
		setBounds(100, 100, 330, 185);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel labelInstructions = new JLabel("<html>&#8226; switch on your Wi-Fi<br>&#8226;"
					+ " choose the ad-hoc network<br>&#8226;"
					+ " wait for connection...</html>");
			labelInstructions.setBorder(UIManager.getBorder("TextField.border"));
			labelInstructions.setBackground(Color.LIGHT_GRAY);
			labelInstructions.setHorizontalAlignment(SwingConstants.LEFT);
			contentPanel.add(labelInstructions, BorderLayout.NORTH);
		}

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		{
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					MainMenu.instance.setVisible(true);
					client.destroy();
					destroy();
				}
			});
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);
		}

		setLocationRelativeTo(null);
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

