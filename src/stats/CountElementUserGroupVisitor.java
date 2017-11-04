package stats;

import data.UserGroup;

public class CountElementUserGroupVisitor implements CountElementVisitor{
	private int userGroupCount = 0;
	
	@Override
	public void visit(UserGroup u) {
		userGroupCount++;
	}
	public int getUserGroupCount(){
		return userGroupCount;
	}
}
