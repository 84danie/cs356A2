

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import data.User;
import data.UserGroup;
import stats.CountElementUserVisitor;
import stats.CountElementVisitor;


/**
 * This class represents the admin control panel UI. Only one instance of the
 * Admin Control Panel class can be instantiated at a time. (Singleton pattern)
 *
 */
public class AdminControlPanel implements TreeSelectionListener{
	private static AdminControlPanel instance = null;
	private JTree tree;
	private CountElementVisitor v =  new CountElementUserVisitor();
	
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
					//createAndShowGUI();
				}
			}
		}
		return instance;
	}
	/**
	 * Display the AdminControlPanel GUI
	 */
	public  void createAndShowGUI() {
		GridLayout layout = new GridLayout(1,2);
		//Create and set up the window.
		JFrame frame = new JFrame("Admin Control Panel");
		frame.setLayout(layout);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UserGroup root = new UserGroup("Root");
		UserGroup g1 = new UserGroup("CS480");
		User u1 = new User("Joe");
		User u2 = new User("Bob");
		UserGroup g2 = new UserGroup("CS356");
		User u3 = new User("Billy");
		UserGroup g3 = new UserGroup("CS350");
		
		g1.add(u1);
		root.add(g1);
		root.add(u2);
		g2.add(u3);
		g1.add(g2);
		root.add(g3);
		
	       
		tree = new JTree(root);
		for (int i = 0; i < tree.getRowCount(); i++) {
	        tree.expandRow(i);
	}
		tree.setRootVisible(true);
		//Add the ubiquitous "Hello World" label.
		JScrollPane treeView = new JScrollPane(tree);
		frame.add(treeView);
		frame.add(new JButton("Test"));

		//Display the window.
		frame.setPreferredSize(new Dimension(500, 400));
		frame.pack();
		frame.setVisible(true);
		//Where the tree is initialized:
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		//Listen for when the selection changes.
		tree.addTreeSelectionListener(this);
		
		//Enumeration<data.Component> e = root.children();
	
		
		
		root.accept(v);
		
	}
	public void valueChanged(TreeSelectionEvent e) {
	    //Returns the last path element of the selection.
	    //This method is useful only when the selection model allows a single selection.
	    TreeNode node = (TreeNode) tree.getLastSelectedPathComponent();

	    if (node == null)
	    //Nothing is selected.  
	    return;

	    if (node.isLeaf()) {
	    	JOptionPane.showMessageDialog(null, v);
	    } else {
	        
	    }

	}
}
