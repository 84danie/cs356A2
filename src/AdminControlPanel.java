

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import data.User;
import data.UserGroup;
import stats.CountElementUserGroupVisitor;
import stats.CountElementUserVisitor;
import stats.CountElementVisitor;


/**
 * This class represents the admin control panel UI. Only one instance of the
 * Admin Control Panel class can be instantiated at a time. (Singleton pattern)
 *
 */
public class AdminControlPanel implements ActionListener, FocusListener{
	private static AdminControlPanel instance = null;
	private JTree tree;
	private JButton GetTotalUsers;
	private JButton GetTotalUserGroups;
	private JButton OpenUserView;
	private JButton GetTotalMessages;
	private JButton GetPositivePercent;
	private UserGroup root;
	private JPanel rightPanel;
	private JPanel createPanel;
	private JPanel contentPanel;
	private JTextField userId;
	private JTextField groupId;
	private JButton AddUser;
	private JButton AddGroup;
	private String newUserId = "";
	private String newUserGroupId = "";
	private DefaultTreeModel treeModel;

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
				}
			}
		}
		return instance;
	}
	/**
	 * Display the AdminControlPanel GUI
	 */
	public  void createAndShowGUI() {
		root = new UserGroup("Root");
		JFrame mainFrame = new JFrame("Admin Control Panel");	
		mainFrame.setLayout(new GridLayout(1,2));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//UserGroup root = new UserGroup("Root");
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

		treeModel = new DefaultTreeModel(root);
		
		tree = new JTree(treeModel);
		
		tree.setRootVisible(true);
		//Add the ubiquitous "Hello World" label.
		JScrollPane treeView = new JScrollPane(tree);
		mainFrame.add(treeView);
		
		buildRightPanel();
		mainFrame.add(rightPanel);

		//Display the window.
		mainFrame.setPreferredSize(new Dimension(500, 400));
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	private void buildRightPanel(){
		rightPanel = new JPanel(new GridLayout(3,1));
		buildCreatePanel();
		rightPanel.add(createPanel);
		OpenUserView = new JButton("Open User View");
		OpenUserView.addActionListener(this);
		rightPanel.add(OpenUserView);
		buildContentPanel();
		rightPanel.add(contentPanel);
		
	}
	private void buildCreatePanel(){
		createPanel = new JPanel(new GridLayout(2,2));
		userId = new JTextField();
		groupId = new JTextField();
		AddUser = new JButton("Add User");
		AddGroup = new JButton("Add Group");
		AddUser.addActionListener(this);
		AddGroup.addActionListener(this);
		userId.addFocusListener(this);
		groupId.addActionListener(this);
		
		
		createPanel.add(userId);
		createPanel.add(AddUser);
		createPanel.add(groupId);
		createPanel.add(AddGroup);
		
		
	}
	private void buildContentPanel(){
		contentPanel = new JPanel(new GridLayout(2,2));
		GetTotalUsers = new JButton("Get Total Users");
		GetTotalUserGroups = new JButton("Get Total User Groups");
		GetTotalMessages = new JButton("Get Total Messages");
		GetPositivePercent = new JButton("Get Positive Percent");
		GetTotalUsers.addActionListener(this);
		GetTotalUserGroups.addActionListener(this);
		GetTotalMessages.addActionListener(this);
		GetPositivePercent.addActionListener(this);
		contentPanel.add(GetTotalUsers);
		contentPanel.add(GetTotalUserGroups);
		contentPanel.add(GetTotalMessages);
		contentPanel.add(GetPositivePercent);
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == GetTotalUsers){
			CountElementVisitor users = new CountElementUserVisitor();
			root.accept(users);
			JOptionPane.showMessageDialog(null,((CountElementUserVisitor)users).getUserCount());
		}
		else if(arg0.getSource() == GetTotalUserGroups){
			CountElementVisitor userGroups = new CountElementUserGroupVisitor();
			root.accept(userGroups);
			JOptionPane.showMessageDialog(null,((CountElementUserGroupVisitor)userGroups).getUserGroupCount());

		}
		else if(arg0.getSource() == OpenUserView){
			TreeNode node = (TreeNode) tree.getLastSelectedPathComponent();

			if (node == null || !node.isLeaf()){
				JOptionPane.showMessageDialog(null,"Please Select a User");
				return;
			}
			else if(node.isLeaf() && node instanceof User){
				//open user view
				JOptionPane.showMessageDialog(null,"goodr");
			}
		}
		else if(arg0.getSource() == AddUser){
			TreeNode node = (TreeNode) tree.getLastSelectedPathComponent();

			if (node == null){
				root.add(new User(newUserId));
				treeModel.reload(root);
				expandAll();
			}
			else if(!node.isLeaf()){
				//open user view
				((UserGroup) node).add((new User(newUserId)));
				JOptionPane.showMessageDialog(null,"goodr");
				treeModel.reload(root);
				expandAll();
			}
		}

	}
	private void expandAll(){
		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		//nothing to be done here
	}
	@Override
	public void focusLost(FocusEvent arg0) {
		if(arg0.getSource() == userId){
			newUserId = userId.getText();
		}
		else if(arg0.getSource() == groupId){
			newUserGroupId = groupId.getText();
		}
		
	}
}
