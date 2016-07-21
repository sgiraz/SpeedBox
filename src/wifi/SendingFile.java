package wifi;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JProgressBar;

/**
 * @author adriano
 *	This class is used to encapsulate the file in the sending queue
 *	it contains all the informations about the file and the percentage
 */
public class SendingFile extends JButton{
	
	private static final long serialVersionUID = 1L;
	
	// TODO: dobbiamo aggiungere il vero layout
	// Ad esempio avremo un mini pannello che contiene:
	// - nome file
	// - progress bar di completamento sulla destra
	// - bottone con icona pausa download
	// - bottone con icona cancella download (ti chiedone conferma)
	
	private final long fileLength;
	private float totalBytesSent;
	private int counter;
	private JProgressBar pb;
	private final int minValue = 0;
	private final int maxValue = 100;
	
	public SendingFile(long file)
	{   
		this.fileLength = file;
		//pb = new JProgressBar(0, 100);
		pb = SendBoxGUI.instance.getProgressBar();	/* IS TEMPORARY */
		pb.setMinimum(minValue);
		pb.setMaximum(maxValue);
		pb.setStringPainted(true);
		
		this.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Sending file: clicked");
			}
		});
	}

	/**
	 * Update the sendingfile status and the progress bar
	 * @param sent the bytes sent
	 */
	public void update(float sent)
	{
		this.totalBytesSent += sent;
		counter = (int)((totalBytesSent / fileLength*100));
		pb.setString("Processing " + counter + "%");
		pb.setValue(counter);
		
		if(counter == 100){
			pb.setString("complete");
		}
	}
	
	

}
