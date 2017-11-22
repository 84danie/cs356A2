package data;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;

import stats.Visitor;

/**
 * This Composite class represents a User group. A user group can have any number of
 * other user groups or users.
 *
 */
public class UserGroup implements Component{
	private String id;
	private List<Component> childUserGroups;
	private Component parent;
	private long creationTime;

	/**
	 * Constructor.
	 * 
	 * @param id the id of this User Group.
	 * @throws NullPointerException if id is null.
	 * @throws IllegalArgumentException if id is empty.
	 */
	public UserGroup(String id) {
		setId(id);
		this.childUserGroups = new ArrayList<Component>();
		parent = null;
		creationTime = System.currentTimeMillis();
	}
	/**
	 * Adds another component (User or User group) to this User Group,
	 * if it has not already been added.
	 * 
	 * @param u the component to be added
	 * @return true if the component was successfully added, false otherwise
	 */
	public boolean add(Component u){
		if(!getRoot(this).contains(u)){
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
	public String getId(){
		return id;
	}
	public long getCreationTime(){
		return creationTime;
	}
	/**
	 * @return all child components of this User Group
	 */
	public List<Component> getChildUserGroup(){
		return childUserGroups;
	}
	
	@Override
	public void setParent(Component u) {
		this.parent = (Component) u;

	}
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		for(Component u : childUserGroups)
			u.accept(visitor);		
	}
	@Override
	public User getUser(String id){
		if(!childUserGroups.isEmpty()){
			for(Component u : childUserGroups){
				User c = u.getUser(id);
				if(c!=null)
					return c;
			}
		}
		return null;
	}
	
	@Override
	public boolean contains(Component c) {
		if(this.equals(c)){
			return true;
		}
		for(Component u : childUserGroups){
			if(u.contains(c)){
				return true;
			}
		}
		return false;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		UserGroup other = (UserGroup) obj;
		if (id == null) {
			if (other.id != null){
				return false;
			}
		} else if (!id.equals(other.id)){
			return false;
		}
		return true;
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
	/**
	 * To maintain the composite design pattern logic,
	 * a UserGroup is never considered a leaf, even if
	 * it has no children.
	 * 
	 * @return false
	 */
	@Override
	public boolean isLeaf() {
		return false;
	}
	private void setId(String id){
		if(id==null){
			throw new NullPointerException();
		}
		else if(id.isEmpty()){
			throw new IllegalArgumentException();
		}
		id = id.toLowerCase().trim();
		id = id.substring(0, 1).toUpperCase()+id.substring(1);
		this.id = id;
	}
	private UserGroup getRoot(UserGroup c){
		if(c.getParent()==null){
			return c;
		}
		return getRoot((UserGroup) c.getParent());
	}

}
