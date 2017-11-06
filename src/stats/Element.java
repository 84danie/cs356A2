package stats;

/**
 * Element interface for visitor pattern.
 *
 */
public interface Element {
	/**
	 * @param visitor the visitor that will visit this instance
	 */
	public void accept(Visitor visitor);
}
