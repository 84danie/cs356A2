package stats;

import java.util.HashSet;
import java.util.Set;

import data.User;
import data.UserGroup;

/**
 * This visitor checks the validity of the users and user groups.
 * Must call reset() after each use, or create a new instance
 * in order to reset the validity flag and set used for duplicates.
 * 
 * **NOTE: Users and Usergroups already prevent duplicates
 *
 */
public class ValidateVisitor implements Visitor{
	private boolean isValid = true;
	private Set<String> names = new HashSet<String>();
	
	@Override
	public  void visit(UserGroup u){
		if(u.getId().contains(" ")||!names.add(u.getId())){
			isValid = false;
		}	
	}
	
	@Override
	public  void visit(User u){
		if(u.getVisibleID().contains(" ")){
			isValid = false;
		}	
	}
	
	/**
	 * @return true if no users or usergroups contain spaces 
	 * 		   and are unique, false otherwise
	 */
	public boolean isValid(){
		return isValid;
	}
	/**
	 * Reset this visitor.
	 */
	public void reset(){
		isValid = true;
		names = new HashSet<String>();
	}

}
