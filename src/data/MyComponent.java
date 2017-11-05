package data;
import javax.swing.tree.TreeNode;

import stats.CountElement;

public interface MyComponent extends TreeNode, CountElement{
	public void setParent(MyComponent u);	
	public String toString();
	public User getUser(String id);
	public boolean contains(MyComponent c);
}
