package stats;

import data.Component;
import data.User;
import data.UserGroup;

/**
 * This visitor visits all users and usergroups and looks for the last
 * updated User. 
 *
 */
public class UpdateVisitor implements Visitor{
	private User lastUpdatedUser = null;
	private long lastUpdateTime = 0;
	
	@Override
	public void visit(User u){
		if(u.getLastUpdateTime() >= lastUpdateTime){
			lastUpdatedUser = u;
			lastUpdateTime = u.getLastUpdateTime();
		}
	}
	@Override
	public void visit(UserGroup u){
		for(Component c : u.getChildUserGroup()){
			if(c instanceof User){
				this.visit((User)c);
			}
			else{
				this.visit((UserGroup) c);
			}
		}
	}
	public User getLastUpdatedUser(){
		return lastUpdatedUser;
	}
}
