package admin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import data.User;
import data.UserGroup;
import stats.ComponentVisitor;
import stats.ContentVisitor;
import stats.UpdateVisitor;
import stats.ValidateVisitor;

/**
 * This class represents the admin control panel UI. Only one instance of the
 * Admin Control Panel class can be instantiated at a time. (Singleton pattern)
 *
 */
public class AdminControlPanel implements ActionListener {
	private static AdminControlPanel instance = null;
	private JTree tree;
	private JButton GetTotalUsers;
	private JButton GetTotalUserGroups;
	private JButton OpenUserView;
	private JButton GetTotalMessages;
	private JButton GetPositivePercent;
	private JButton CheckValidity;
	private JButton GetLastUpdated;
	private UserGroup root;
	private JPanel rightPanel;
	private JPanel createPanel;
	private JPanel contentPanel;
	private JTextField userId;
	private JTextField groupId;
	private JButton AddUser;
	private JButton AddGroup;
	private DefaultTreeModel treeModel;

	private AdminControlPanel() {
	}

	/**
	 * Get the instance of this AdminControlPanel.
	 * 
	 * Also displays the GUI for this instance.
	 * 
	 * @return the current (and only) instance
	 */
	public static AdminControlPanel getInstance() {
		if (instance == null) {
			synchronized (AdminControlPanel.class) {
				if (instance == null) {
					instance = new AdminControlPanel();
					instance.createAndShowGUI();
				}
			}
		}
		return instance;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() == GetTotalUsers) {
			totalUsersButtonAction();
		} 
		else if (arg0.getSource() == GetTotalUserGroups) {
			totalUserGroupsButtonAction();
		} 
		else if (arg0.getSource() == OpenUserView) {
			openUserViewButtonAction();
		} 
		else if (arg0.getSource() == AddUser) {
			addUserButtonAction();
		}
		else if (arg0.getSource() == AddGroup) {
			addUserGroupButtonAction();
		} 
		else if (arg0.getSource() == GetTotalMessages) {
			getTotalMessagesButtonAction();
		} 
		else if (arg0.getSource() == GetPositivePercent) {
			getPositivePercentButtonAction();
		}
		else if (arg0.getSource() == CheckValidity){
			checkValidityButtonAction();
		}
		else if(arg0.getSource() == GetLastUpdated){
			getLastUpdatedUserButtonAction();
		}
	}

	/**
	 * Protected method for UserView.
	 * 
	 * @return the root UserGroup of this AdminControlPanel.
	 */
	protected UserGroup getRoot() {
		return root;
	}

	private void createAndShowGUI() {
		setUpLookAndFeel();
		root = new UserGroup("Root");
		JFrame mainFrame = new JFrame("Admin Control Panel");
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		treeModel = new DefaultTreeModel(root);

		tree = new JTree(treeModel);

		tree.setRootVisible(true);
		updateTree();

		JScrollPane treeView = new JScrollPane(tree);
		mainFrame.add(treeView, BorderLayout.CENTER);
		
		
		
		GetLastUpdated = new JButton("Get Last Updated User");
		GetLastUpdated.addActionListener(this);
		mainFrame.add(GetLastUpdated,BorderLayout.SOUTH);

		buildRightPanel();
		mainFrame.add(rightPanel, BorderLayout.EAST);

		mainFrame.setPreferredSize(new Dimension(500, 300));
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	private void setUpLookAndFeel() {
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

	private void buildRightPanel() {
		rightPanel = new JPanel(new GridLayout(4, 1));

		buildCreatePanel();
		buildContentPanel();

		CheckValidity = new JButton("Check Validity");
		CheckValidity.addActionListener(this);
		
		OpenUserView = new JButton("Open User View");
		OpenUserView.addActionListener(this);

		rightPanel.add(createPanel);
		rightPanel.add(CheckValidity);
		rightPanel.add(OpenUserView);
		rightPanel.add(contentPanel);
		
	}

	private void buildCreatePanel() {
		createPanel = new JPanel(new GridLayout(2, 2));

		userId = new JTextField();
		groupId = new JTextField();
		AddUser = new JButton("Add User");
		AddGroup = new JButton("Add Group");

		AddUser.addActionListener(this);
		AddGroup.addActionListener(this);

		createPanel.add(userId);
		createPanel.add(AddUser);
		createPanel.add(groupId);
		createPanel.add(AddGroup);
	}

	private void buildContentPanel() {
		contentPanel = new JPanel(new GridLayout(2, 2));

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

	private void totalUsersButtonAction() {
		ComponentVisitor visitor = new ComponentVisitor();
		root.accept(visitor);
		JOptionPane.showMessageDialog(null, visitor.getUserCount());
	}

	private void totalUserGroupsButtonAction() {
		ComponentVisitor visitor = new ComponentVisitor();
		root.accept(visitor);
		JOptionPane.showMessageDialog(null, visitor.getUserGroupCount());
	}

	private void openUserViewButtonAction() {
		TreeNode node = (TreeNode) tree.getLastSelectedPathComponent();

		if (node == null || !node.isLeaf()) {
			JOptionPane.showMessageDialog(null, "Please Select a User", "User View Error", JOptionPane.ERROR_MESSAGE);
			return;
		} else if (node.isLeaf()) { // always a user
			UserView view = new UserView((User) node, this);
			view.display();
		}
	}

	private void addUserButtonAction() {
		TreeNode node = (TreeNode) tree.getLastSelectedPathComponent();
		String newUserId = userId.getText();
		if (newUserId.isEmpty()) {
			return;
		}
		if (node == null) { // add to root by default
			if (root.add(new User(newUserId))) {
				updateTree();
				userId.setText("");
			} else
				JOptionPane.showMessageDialog(null, "User already exists.", "Admin Error", JOptionPane.ERROR_MESSAGE);
		} else if (!node.isLeaf()) {
			if (((UserGroup) node).add((new User(newUserId)))) {
				updateTree();
				userId.setText("");
			} else {
				JOptionPane.showMessageDialog(null, "User already exists.", "Admin Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Users can only be added to user groups.", "Admin Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addUserGroupButtonAction() {
		TreeNode node = (TreeNode) tree.getLastSelectedPathComponent();
		String newUserGroupId = groupId.getText();
		if (newUserGroupId.isEmpty()) {
			return;
		}
		if (node == null) { // add to root by default
			if (root.add(new UserGroup(newUserGroupId))) {
				updateTree();
				groupId.setText("");
			} else {
				JOptionPane.showMessageDialog(null, "User group already exists.", "Admin Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (!node.isLeaf()) {
			if (((UserGroup) node).add((new UserGroup(newUserGroupId)))) {
				updateTree();
				groupId.setText("");
			} else {
				JOptionPane.showMessageDialog(null, "User group already exists.", "Admin Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Cannot add a user group to a user. " + "Please select a user group.",
					"Admin Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void getTotalMessagesButtonAction() {
		ContentVisitor visitor = new ContentVisitor();
		root.accept(visitor);
		JOptionPane.showMessageDialog(null, visitor.getMessagesCount());
	}

	private void getPositivePercentButtonAction() {
		DecimalFormat percent = new DecimalFormat("0.00");
		ContentVisitor visitor = new ContentVisitor();
		root.accept(visitor);
		JOptionPane.showMessageDialog(null, percent.format(visitor.getPositivePercent()) + "%");
	}
	private void checkValidityButtonAction(){
		ValidateVisitor visitor = new ValidateVisitor();
		root.accept(visitor);
		if(visitor.isValid()){
			JOptionPane.showMessageDialog(null, "All user groups and users have valid names.");
		}
		else{
			JOptionPane.showMessageDialog(null, "Not all user groups and users have valid names.",
													"Admin Alert", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void getLastUpdatedUserButtonAction(){
		UpdateVisitor visitor = new UpdateVisitor();
		root.accept(visitor);
		User lastUpdated = visitor.getLastUpdatedUser();
		if(lastUpdated!=null){
			JOptionPane.showMessageDialog(null,"The last user updated was "+lastUpdated );
		}
		else{
			JOptionPane.showMessageDialog(null, "Cannot fetch last updated user "
										+ "because no users have been added.",
										"Admin Alert", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void updateTree() {
		treeModel.reload(root);
		for (int i = 0; i < tree.getRowCount(); i++) // keeps all paths visible
			// by default
			tree.expandRow(i);
	}
}
