package data;


import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

import javax.swing.DefaultListModel;
import javax.swing.tree.TreeNode;

import stats.CountElementVisitor;

/**
 * User class. Users can follow and be followed by other users.
 *
 */
public class User extends Observable implements Observer, MyComponent{
	private String id;
	private String visibleID;
	private List<User> followers;
	private List<User> followings;
	private DefaultListModel<String> newsFeed;
	private MyComponent parent;

	/**
	 * Constructor for User.
	 * @param visibleID the visible ID of this User.
	 */
	public User(String visibleID) {
		this.id = UUID.randomUUID().toString();
		this.visibleID = visibleID;
		this.followers = new ArrayList<User>();
		this.followings = new ArrayList<User>();
		this.newsFeed = new DefaultListModel<String>();
		parent = null;
	}
	/** 
	 * Add a new Observer (follower) to observe this User.
	 * 
	 * @param o the User that will follow this User
	 */
	public void addObserver(User o){
		super.addObserver(o);
		followers.add((User)o);
		((User)o).followings.add(this); 
	}
	/**
	 * Post a message to this User's newsfeed.
	 * 
	 * @param message the message to be posted.
	 */
	public void postMessage(String message){
		if(message!=null){
			newsFeed.add(0,message);
			setChanged();
			notifyObservers(message);
		}
	}

	public void update(Observable o, Object arg) {
		if(arg instanceof String){
			newsFeed.add(0,(String)arg);
		}
	}

	/**
	 * @return the unique id of this User
	 */
	public String getId() {
		return id;
	}
	/**
	 * @return the visible id of this User
	 */
	public String getVisibleID() {
		return visibleID;
	}
	/**
	 * @return the list of Users following this User
	 */
	public List<User> getFollowers() {
		return followers;
	}
	/** 
	 * @return String representation of this User
	 */
	@Override
	public String toString() {
		return visibleID;
	}

	/**
	 * @return the Users this User is following
	 */
	public List<User> getFollowings() {
		return followings;
	}
	/**
	 * @return the newsfeed of this User
	 */
	public DefaultListModel<String> getNewsFeed() {
		return newsFeed;
	}
	

	/*==============TreeNode Methods==============*/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((visibleID == null) ? 0 : visibleID.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (visibleID == null) {
			if (other.visibleID != null)
				return false;
		} else if (!visibleID.equals(other.visibleID))
			return false;
		return true;
	}
	@Override
	public Enumeration<MyComponent> children() {
		return null;
	}
	@Override
	public boolean getAllowsChildren() {
		return false;
	}
	@Override
	public TreeNode getChildAt(int childIndex) {
		if(childIndex!=0)
			return null;
		else
			return this;
	}
	@Override
	public int getChildCount() {
		return 0;
	}
	@Override
	public int getIndex(TreeNode node) {
		if(node.equals(this))
			return 0;
		else
			return -1;
	}
	@Override
	public TreeNode getParent() {
		return parent;
	}
	@Override
	public boolean isLeaf() {
		return true;
	}
	@Override
	public void setParent(MyComponent u) {
		parent = (MyComponent) u.getParent();

	}
	@Override
	public void accept(CountElementVisitor visitor) {
		visitor.visit(this);	
	}



}
