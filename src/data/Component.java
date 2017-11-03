package data;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import stats.CountElement;

public interface Component extends TreeNode, CountElement{
	
	public void setParent(Component u);
	
}
