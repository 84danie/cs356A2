package data;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.tree.TreeNode;

import stats.Visitor;

/**
 * User class. Users can follow and be followed by other users.
 *
 */
public class User extends Observable implements Observer, Component{
	private String id;
	private String visibleID;
	private List<User> followers;
	private List<User> followings;
	private DefaultListModel<Message> newsFeed; 
	private Component parent;

	/**
	 * Constructor.
	 * @param visibleID the visible ID of this User.
	 * @throws NullPointerException if visibleID is null.
	 * @throws IllegalArgumentException if visibleID is empty.
	 */
	public User(String visibleID) {
		setId(visibleID);
		this.followers = new ArrayList<User>();
		this.followings = new ArrayList<User>();
		this.newsFeed = new DefaultListModel<Message>();
		parent = null;
	}
	/** 
	 * Add a new Observer (follower) to observe this User, unless
	 * o is already following this User.
	 * 
	 * @param o the User that will follow this User
	 * @throws NullPointerException if o is null
	 */
	public void addObserver(User o){
		if(o==null){
			throw new NullPointerException();
		}
		if(!o.equals(this)&&!followers.contains(o)){
			super.addObserver(o);
			followers.add((User)o);
			((User)o).followings.add(this); 
		}
	}
	/**
	 * Post a message to this User's newsfeed.
	 * 
	 * @param message the message to be posted.
	 * @throws NullPointerException is message is null
	 */
	public void postMessage(Message message){
		if(message==null){
			throw new NullPointerException();
		}
		message.setContent("@"+visibleID+" "+message.getContent());
		newsFeed.add(0,message);
		setChanged();
		notifyObservers(message);

	}
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Message){
			newsFeed.add(0,(Message)arg);
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
	 * @return the List of Users following this User
	 */
	public List<User> getFollowers() {
		return followers;
	}
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
	public ListModel<Message> getNewsFeed() {
		return newsFeed;
	}
	@Override
	public void setParent(Component u) {
		if(u==null)
			throw new IllegalArgumentException();
		parent = (Component) u.getParent();

	}
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);	
	}
	@Override
	public User getUser(String id) {
		if(this.visibleID.equalsIgnoreCase(id.trim())){
			return this;
		}
		return null;
	}
	@Override
	public boolean contains(Component c) {
		if(c instanceof User){
			return ((User)c).equals(this);
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((visibleID == null) ? 0 : visibleID.hashCode());
		return result;
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
		User other = (User) obj;
		if (visibleID == null) {
			if (other.visibleID != null){
				return false;
			}
		} else if (!visibleID.equals(other.visibleID)){
			return false;
		}
		return true;
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
	private void setId(String id){
		if(id==null){
			throw new NullPointerException();
		}
		else if(id.isEmpty()){
			throw new IllegalArgumentException();
		}
		id = id.toLowerCase().trim();
		id = id.substring(0, 1).toUpperCase()+id.substring(1);
		this.visibleID = id;
	}
	
}
