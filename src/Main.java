import java.awt.EventQueue;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("Test");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new SendBoxGUI();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	
}