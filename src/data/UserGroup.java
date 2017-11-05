package data;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import stats.CountElementVisitor;

public class UserGroup implements MyComponent{
	private String id;
	private List<MyComponent> childUserGroups;
	private MyComponent parent;

	public UserGroup(String id) {
		if(id.isEmpty())
			throw new IllegalArgumentException();
		this.id = id;
		this.childUserGroups = new ArrayList<MyComponent>();
		parent = null;
	}
	public boolean add(MyComponent u){
		if(!contains(u)){
			childUserGroups.add(u);
			u.setParent(this);
			return true;
		}
		return false;
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
		return false;
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
	@Override
	public User getUser(String id){
		if(!childUserGroups.isEmpty()){
			for(MyComponent u : childUserGroups){
				User c = u.getUser(id);
				if(c!=null)
					return c;
			}
		}
		return null;
	}
	@Override
	public boolean contains(MyComponent c) {
		if(this.equals(c))
			return true;
		if(!childUserGroups.isEmpty()){
			for(MyComponent u : childUserGroups){
				if(u.contains(c))
					return true;
			}
		}
		return false;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserGroup other = (UserGroup) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
