package stats;

import java.util.HashSet;
import java.util.Set;

import data.User;
import data.UserGroup;

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
	public int getUserGroupCount(){
		return userGroups.size();
	}
	public int getUserCount(){
		return users.size();
	}
}
