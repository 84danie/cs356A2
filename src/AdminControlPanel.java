

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;


/**
 * This class represents the admin control panel UI. Only one instance of the
 * Admin Control Panel class can be instantiated at a time. (Singleton pattern)
 *
 */
public class AdminControlPanel {
	private static AdminControlPanel instance = null;
	private static JTextArea tree;
	private static CompositeUserGroup Root = new CompositeUserGroup("Root");
	
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
		GridLayout layout = new GridLayout(1,2);
		//Create and set up the window.
		JFrame frame = new JFrame("Admin Control Panel");
		frame.setLayout(layout);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		CompositeUserGroup Group1 = new CompositeUserGroup("Group 1");
		CompositeUserGroup Group2 = new CompositeUserGroup("Group 2");

		User u1 = new User("Joe");
		User u2 = new User("Joe2");
		Group1.add(u2);
		User u3 = new User("Joe2");
		Root.add(u1);
		Group2.add(u3);
		Root.add(Group1);
		Group1.add(Group2);
		Root.add(u1);
		Root.add(Group2);

		
	       

		//Add the ubiquitous "Hello World" label.
		tree = new JTextArea(Root.print(""));
		frame.add(tree);
		frame.add(new JButton("Test"));

		//Display the window.
		frame.setPreferredSize(new Dimension(500, 400));
		frame.pack();
		frame.setVisible(true);
	}
}
