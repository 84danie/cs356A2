

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

/**
 * User class. Users can follow and be followed by other users.
 *
 */
public class User extends Observable implements Observer{
	private String id;
	private String visibleID;
	private List<User> followers;
	private List<User> followings;
	private List<String> newsFeed;

	/**
	 * Constructor for User.
	 * @param visibleID the visible ID of this User.
	 */
	public User(String visibleID) {
		this.id = UUID.randomUUID().toString();
		this.visibleID = visibleID;
		this.followers = new ArrayList<User>();
		this.followings = new ArrayList<User>();
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
		newsFeed.add(message);
		setChanged();
		notifyObservers(message);
	}

	public void update(Observable o, Object arg) {
		if(arg instanceof String){
			newsFeed.add((String)arg);
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
	public List<String> getNewsFeed() {
		return newsFeed;
	}
}
