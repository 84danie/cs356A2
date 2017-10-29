

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 * This class represents the admin control panel UI. Only one instance of the
 * Admin Control Panel class can be instantiated at a time. (Singleton pattern)
 *
 */
public class AdminControlPanel {
	private static AdminControlPanel instance = null;
	
	/**
	 * Private constructor
	 */
	private AdminControlPanel() {}
	
	/**
	 * Get the current instance of this AdminControlPanel
	 * @return the current (and only) instance
	 */
	public static AdminControlPanel getInstance(){
		if(instance==null){
			synchronized(AdminControlPanel.class){
				if(instance==null){
					instance = new AdminControlPanel();
					createAndShowGUI();
				}
			}
		}
		return instance;
	}
	/**
	 * Display the AdminControlPanel GUI
	 */
	private static void createAndShowGUI() {
		//Create and set up the window.
		JFrame frame = new JFrame("Admin Control Panel");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Add the ubiquitous "Hello World" label.
		JLabel label = new JLabel("Hello World");
		frame.getContentPane().add(label);

		//Display the window.
		frame.setPreferredSize(new Dimension(500, 400));
		frame.pack();
		frame.setVisible(true);
	}



}
