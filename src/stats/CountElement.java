package stats;

public interface CountElement {
	public void accept(CountElementVisitor visitor);
}