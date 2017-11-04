package data;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import stats.CountElementVisitor;

public class UserGroup implements MyComponent{
	private String id;
	private List<MyComponent> childUserGroups;
	private MyComponent parent;

	public UserGroup(String id) {
		this.id = id;
		this.childUserGroups = new ArrayList<MyComponent>();
		parent = null;
	}
	public void add(MyComponent u){
		childUserGroups.add(u);
		u.setParent(this);
	}
	@Override
	public String toString(){
		return id;
	}
	public List<MyComponent> getChildUserGroup(){
		return childUserGroups;
	}
	@Override
	public Enumeration<MyComponent> children() {
		return (Enumeration<MyComponent>) childUserGroups;
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
	public void setParent(MyComponent u) {
		this.parent = (MyComponent) u.getParent();
		
	}
	@Override
	public void accept(CountElementVisitor visitor) {
		visitor.visit(this);
		for(MyComponent u : childUserGroups)
			u.accept(visitor);		
	}
	
}
