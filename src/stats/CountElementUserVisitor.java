package stats;

import java.util.Enumeration;

import data.Component;
import data.User;
import data.UserGroup;

public class CountElementUserVisitor implements CountElementVisitor{
	public int userCount = 0;
	public int userGroupCount = 0;
	@Override
	public void visit(UserGroup u) {
		userGroupCount++;
		System.out.println("group count");
	}

	@Override
	public void visit(User u) {
		userCount++;	
		System.out.println("user count");
	}
	
}
