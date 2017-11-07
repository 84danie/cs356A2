import admin.AdminControlPanel;

/**
 * Driver class. Starts an AdminControlPanel instance.
 */
public class Driver {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AdminControlPanel.getInstance();  
			}
		});	
	}
}
