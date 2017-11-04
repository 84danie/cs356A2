package data;
import javax.swing.tree.TreeNode;

import stats.CountElement;

public interface MyComponent extends TreeNode, CountElement{
	
	public void setParent(MyComponent u);
	
}
