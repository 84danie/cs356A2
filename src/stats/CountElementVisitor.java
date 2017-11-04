package stats;
import data.User;
import data.UserGroup;

public interface CountElementVisitor {
	public default void visit(UserGroup u){};
	public default void visit(User u){};
}
