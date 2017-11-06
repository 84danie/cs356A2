package stats;
import data.User;
import data.UserGroup;

/**
 *  Visitor interface for Usergroups and Users. 
 *
 */
public interface Visitor {
	public default void visit(UserGroup u){};
	public default void visit(User u){};
}
