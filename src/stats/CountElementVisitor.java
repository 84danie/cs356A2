package stats;
import data.User;
import data.UserGroup;

public interface CountElementVisitor {
	public abstract void visit(UserGroup u);
	public abstract void visit(User u);
}
