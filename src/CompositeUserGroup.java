import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class CompositeUserGroup implements UserGroup{
	private String id;
	private List<UserGroup> childUserGroups;
	private UserGroup parent;

	public CompositeUserGroup(String id) {
		this.id = id;
		this.childUserGroups = new ArrayList<UserGroup>();
		parent = null;
	}
	public void add(UserGroup u){
		childUserGroups.add(u);
		u.setParent(this);
	}
	@Override
	public String toString(){
		return id;
	}
	@Override
	public Enumeration<UserGroup> children() {
		return (Enumeration<UserGroup>) childUserGroups;
	}
	@Override
	public boolean getAllowsChildren() {
		return true;
	}
	@Override
	public TreeNode getChildAt(int childIndex) {
		return childUserGroups.get(childIndex);
	}
	@Override
	public int getChildCount() {
		return childUserGroups.size();
	}
	@Override
	public int getIndex(TreeNode node) {
		return childUserGroups.indexOf(node);
	}
	@Override
	public TreeNode getParent() {
		return this.parent;
	}
	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return childUserGroups.isEmpty();
	}
	@Override
	public void setParent(UserGroup u) {
		this.parent = (UserGroup) u.getParent();
		
	}
	
}
