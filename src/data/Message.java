package data;

/**
 * A Message instance is identified by the time it was created (that is, when
 * the constructor was called). 
 *
 */
public class Message {
	private long timestamp;
	private String content;
	
	/**
	 * Constructor. 
	 * @param message the content of this message
	 * @throws IllegalArgumentException if the given message is null or empty
	 */
	public Message(String content){
		if(content==null||content.isEmpty())
			throw new IllegalArgumentException();
		this.content = content;
		timestamp = System.currentTimeMillis();
	}
	/**
	 * @return the content of this Message
	 */
	public String getContent() {
		return content;
	}
	/**
	 * Mutator for this Message's content. Only users/usergroups
	 * can edit the content of a Message.
	 * 
	 * @param content the new content of this message
	 */
	protected void setContent(String content) {
		this.content = content;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (timestamp != other.timestamp)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return content;
	}
	
}
