import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public interface UserGroup extends TreeNode{
	public void setParent(UserGroup u);
	
}
