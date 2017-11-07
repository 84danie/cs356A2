package data;
import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import stats.Element;

/**
 * Component interface for the Composite design pattern.
 * 
 * Implements TreeNode in order to allow components to be
 * easily added to JTrees. 
 *
 */
public interface Component extends TreeNode, Element{
	/**
	 * Sets the parent of this Component. 
	 * 
	 * @param u
	 */
	public void setParent(Component u);	
	
	/**
	 * Returns the instance of a User with a given id.
	 * @param id the id to be searched with
	 * @return the User with @param id, null if there is no such User
	 */
	public User getUser(String id);
	
	/**
	 * @param c a Component instance
	 * @return true if this Component contains c,
	 * false otherwise
	 */
	public boolean contains(Component c);
	
	/**
	 * This method is not used in this implementation.
	 * 
	 * @return null
	 */
	@Override
	public default Enumeration<Component> children() {
		return null;
	}
}
