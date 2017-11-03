package data;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import stats.CountElementVisitor;

public class UserGroup implements Component{
	private String id;
	private List<Component> childUserGroups;
	private Component parent;

	public UserGroup(String id) {
		this.id = id;
		this.childUserGroups = new ArrayList<Component>();
		parent = null;
	}
	public void add(Component u){
		childUserGroups.add(u);
		u.setParent(this);
	}
	@Override
	public String toString(){
		return id;
	}
	public List<Component> getChildUserGroup(){
		return childUserGroups;
	}
	@Override
	public Enumeration<Component> children() {
		return (Enumeration<Component>) childUserGroups;
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
	public void setParent(Component u) {
		this.parent = (Component) u.getParent();
		
	}
	@Override
	public void accept(CountElementVisitor visitor) {
		visitor.visit(this);
		for(Component u : childUserGroups)
			u.accept(visitor);		
	}
	
}
