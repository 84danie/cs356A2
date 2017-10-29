

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

	public User(String visibleID) {
		this.id = UUID.randomUUID().toString();
		this.visibleID = visibleID;
		this.followers = new ArrayList<User>();
		this.followings = new ArrayList<User>();
	}
	public void addObserver(Observer o){
		if(o instanceof User){
			super.addObserver(o);
			followers.add((User)o);
			((User)o).followings.add(this);
		}
		else
			System.out.println("NOT A USER!");
	}
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
	public String getId() {
		return id;
	}
	public String getVisibleID() {
		return visibleID;
	}
	public List<User> getFollowers() {
		return followers;
	}
	@Override
	public String toString() {
		return visibleID;
	}
	public List<User> getFollowings() {
		return followings;
	}
	public List<String> getNewsFeed() {
		return newsFeed;
	}
}
