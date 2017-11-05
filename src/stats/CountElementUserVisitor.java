package stats;

import java.util.HashSet;
import java.util.Set;

import data.User;
import data.UserGroup;

public class CountElementUserVisitor implements CountElementVisitor{
	private int userCount = 0;

	@Override
	public void visit(User u) {
		userCount++;	
	}
	public int getUserCount(){
		return userCount;
	}
	
	
}
