import java.awt.BorderLayout;
import java.awt.dnd.DropTarget;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Application extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args)
	{
		new Application();
	}

	public Application() 
	{
		//title
		super("Test");

		this.setSize(250, 150);

		JLabel myLabel = new JLabel("Drop a file", SwingConstants.CENTER);

		// Create the drag and drop listener
		DropListener listener = new DropListener();

		// Connect the label with a drag and drop listener
		new DropTarget(myLabel, listener);

		// Add the label to the content
		this.getContentPane().add(BorderLayout.CENTER, myLabel);


		this.setVisible(true);

	}
}