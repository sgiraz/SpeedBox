import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
	
	private final File file;
	private int totalBytesSent;
	private float percent;
	private JProgressBar pb;
	
	public SendingFile(File file)
	{
		this.file = file;
		pb = new JProgressBar(0, 100);
		pb.setValue(0);
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
		percent = totalBytesSent / file.length();
		pb.setValue((int)(percent * 100));
	}
	
	

}
