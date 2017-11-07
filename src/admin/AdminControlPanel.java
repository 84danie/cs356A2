package admin;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import data.User;
import data.UserGroup;
import stats.ComponentVisitor;
import stats.ContentVisitor;


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
	 * Get the instance of this AdminControlPanel.
	 * 
	 * Also displays the GUI for this instance.
	 * 
	 * @return the current (and only) instance
	 */
	public static AdminControlPanel getInstance(){
		if(instance==null){
			synchronized(AdminControlPanel.class){
				if(instance==null){
					instance = new AdminControlPanel();
					instance.createAndShowGUI();
				}
			}
		}
		return instance;
	}
	/**
	 * Display the AdminControlPanel GUI
	 */
	private  void createAndShowGUI() {
		setUpLookAndFeel();
		root = new UserGroup("Root");
		JFrame mainFrame = new JFrame("Admin Control Panel");	
		mainFrame.setLayout(new GridLayout(1,2));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		treeModel = new DefaultTreeModel(root);

		tree = new JTree(treeModel);

		tree.setRootVisible(true);
		updateTree();

		JScrollPane treeView = new JScrollPane(tree);
		mainFrame.add(treeView);

		buildRightPanel();
		mainFrame.add(rightPanel);

		mainFrame.setPreferredSize(new Dimension(800, 300));
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	/**
	 * Sets look and feel to Nimbus, if available.
	 */
	private void setUpLookAndFeel(){
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, uses the default (ugly) look and feel
		}
	}
	/**
	 * Build the right panel.
	 */
	private void buildRightPanel(){
		rightPanel = new JPanel(new GridLayout(3,1));

		buildCreatePanel();
		buildContentPanel();

		OpenUserView = new JButton("Open User View");
		OpenUserView.addActionListener(this);

		rightPanel.add(createPanel);
		rightPanel.add(OpenUserView);
		rightPanel.add(contentPanel);
	}
	/**
	 * Build the panel that contains the components used to create
	 * and submit new users and user groups.
	 */
	private void buildCreatePanel(){
		createPanel = new JPanel(new GridLayout(2,2));

		userId = new JTextField();
		groupId = new JTextField();
		AddUser = new JButton("Add User");
		AddGroup = new JButton("Add Group");

		AddUser.addActionListener(this);
		AddGroup.addActionListener(this);
		userId.addFocusListener(this);
		groupId.addFocusListener(this);

		createPanel.add(userId);
		createPanel.add(AddUser);
		createPanel.add(groupId);
		createPanel.add(AddGroup);
	}
	/**
	 * Build the panel that contains the buttons that display
	 * the total users, user groups, messages and percent of
	 * positive messages
	 */
	private void buildContentPanel(){
		contentPanel = new JPanel(new GridLayout(2,2));

		GetTotalUsers = new JButton("Total Users");
		GetTotalUserGroups = new JButton("Total User Groups");
		GetTotalMessages = new JButton("Total Messages");
		GetPositivePercent = new JButton("Positive Percentage");

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
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				if(arg0.getSource() == GetTotalUsers)
					totalUsersButtonAction();

				else if(arg0.getSource() == GetTotalUserGroups)
					totalUserGroupsButtonAction();

				else if(arg0.getSource() == OpenUserView)
					openUserViewButtonAction();

				else if(arg0.getSource() == AddUser)
					addUserButtonAction();

				else if(arg0.getSource() == AddGroup)
					addUserGroupButtonAction();

				else if(arg0.getSource() == GetTotalMessages)
					getTotalMessagesButtonAction();

				else if(arg0.getSource() == GetPositivePercent)
					getPositivePercentButtonAction();
			}
		});
	}
	/**
	 * Displays the total number of users.
	 */
	private void totalUsersButtonAction(){
		ComponentVisitor visitor = new ComponentVisitor();
		root.accept(visitor);
		JOptionPane.showMessageDialog(null,visitor.getUserCount());
	}
	/**
	 * Displays the total number of user groups.
	 */
	private void totalUserGroupsButtonAction(){
		ComponentVisitor visitor = new ComponentVisitor();
		root.accept(visitor);
		JOptionPane.showMessageDialog(null,visitor.getUserGroupCount());
	}
	/**
	 * Opens a new user view based on the current selection
	 * in the JTree.
	 */
	private void openUserViewButtonAction(){
		TreeNode node = (TreeNode) tree.getLastSelectedPathComponent();

		if (node == null || !node.isLeaf()){
			JOptionPane.showMessageDialog(null,"Please Select a User");
			return;
		}
		else if(node.isLeaf()){ //always a user
			UserView view = new UserView((User) node,this);
			view.display();
		}
	}
	/**
	 * Adds a new user based on the current selection in the JTree.
	 * 
	 * If nothing is selected, the user is added to Root by default.
	 */
	private void addUserButtonAction(){
		TreeNode node = (TreeNode) tree.getLastSelectedPathComponent();
		if(newUserId.isEmpty())
			return;	
		if (node == null){
			if(root.add(new User(newUserId))){
				updateTree();
				userId.setText("");
				newUserId="";
			}
			else
				JOptionPane.showMessageDialog(null,"User already exists.");
		}
		else if(!node.isLeaf()){
			if(((UserGroup) node).add((new User(newUserId)))){
				updateTree();
				userId.setText("");
				newUserId="";
			}
			else
				JOptionPane.showMessageDialog(null,"User already exists.");
		}
	}
	/**
	 * Adds a new user group based on the current selection in the JTree.
	 * 
	 * If nothing is selected, the user group is added to Root by default.
	 */
	private void addUserGroupButtonAction(){
		TreeNode node = (TreeNode) tree.getLastSelectedPathComponent();
		if(newUserGroupId.isEmpty())
			return;
		if (node == null){ //add to root by default
			if(root.add(new UserGroup(newUserGroupId))){
				updateTree();
				groupId.setText("");
				newUserGroupId="";
			}
			else
				JOptionPane.showMessageDialog(null,"User group already exists.");
		}
		else if(!node.isLeaf()){
			if(((UserGroup) node).add((new UserGroup(newUserGroupId)))){
				updateTree();
				groupId.setText("");
				newUserGroupId="";
			}
			else
				JOptionPane.showMessageDialog(null,"User group already exists.");
		}
		else
			JOptionPane.showMessageDialog(null,"Cannot add a user group to a user. "
					+ "Please select a user group.");
	}
	/**
	 * Displays the total number of messages tweeted.
	 */
	private void getTotalMessagesButtonAction(){
		ContentVisitor visitor = new ContentVisitor();
		root.accept(visitor);
		JOptionPane.showMessageDialog(null,visitor.getMessagesCount());
	}
	/**
	 * Displays the percentage of messages that are "positive".
	 * 
	 * See the ContentVisitor class for specifics on a "positive"
	 * message.
	 */
	private void getPositivePercentButtonAction(){
		DecimalFormat percent = new DecimalFormat("0.00");
		ContentVisitor visitor = new ContentVisitor();
		root.accept(visitor);
		JOptionPane.showMessageDialog(null,percent.format(visitor.getPositivePercent())+"%");
	}
	/**
	 * Updates the JTree display and auto expands all paths.
	 */
	private void updateTree(){
		treeModel.reload(root);
		for (int i = 0; i < tree.getRowCount(); i++) 
			tree.expandRow(i);
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
	/**
	 * Protected method for UserView.
	 * 
	 * @return the root UserGroup of this AdminControlPanel.
	 */
	protected UserGroup getRoot(){
		return root;
	}
}
