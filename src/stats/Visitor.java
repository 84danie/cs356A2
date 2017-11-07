package stats;
import data.User;
import data.UserGroup;

/**
 *  Visitor interface for Usergroups and Users. Classes that implement
 *  visitor are able to visit UserGroups and Users.
 */
public interface Visitor {
	/**
	 * Visit a Usergroup.
	 * @param u the Usergroup to be visited.
	 */
	public default void visit(UserGroup u){};
	/**
	 * Visit a User.
	 * @param u the User to be visited.
	 */
	public default void visit(User u){};
}
