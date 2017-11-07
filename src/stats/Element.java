package stats;

/**
 * Element interface for visitor pattern. Classes that implement Element
 * are able to accept Visitors.
 *
 */
public interface Element {
	/**
	 * @param visitor the visitor that will visit this instance
	 */
	public void accept(Visitor visitor);
}
