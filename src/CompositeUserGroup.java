import java.util.ArrayList;
import java.util.List;

public class CompositeUserGroup implements UserGroup{
	private String id;
	private List<UserGroup> childUserGroups;

	public CompositeUserGroup(String id) {
		this.id = id;
		this.childUserGroups = new ArrayList<UserGroup>();
	}
	public void add(UserGroup u){
		childUserGroups.add(u);
	}
	@Override
	public String print(String space){
		String s = id+"\n";
		for(UserGroup u : childUserGroups){
			if(u instanceof CompositeUserGroup)
				s+=space+"*"+u.print(space+" ");
			else
				s+=space+"-"+u.print(space+" ");
			

		}
		return s;
	}
}
