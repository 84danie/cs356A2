package stats;

import java.util.HashSet;
import java.util.Set;

import data.User;
import data.UserGroup;

/**
 * This visitor visits users and usergroups and obtains the total number of users
 * and user groups. Sets are used in order to eliminate the need to "reset" any counters.
 *
 */
public class ComponentVisitor implements Visitor{
	private Set<UserGroup> userGroups = new HashSet<UserGroup>();
	private Set<User> users = new HashSet<User>();
	
	@Override
	public void visit(UserGroup u) {
		userGroups.add(u);
	}
	@Override
	public void visit(User u) {
		users.add(u);	
	}
	/**
	 * @return the total number of UserGroups
	 */
	public int getUserGroupCount(){
		return userGroups.size();
	}
	/**
	 * @return the total number of Users
	 */
	public int getUserCount(){
		return users.size();
	}
}
