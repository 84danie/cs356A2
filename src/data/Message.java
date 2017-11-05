package data;

public class Message {
	private long timestamp;
	private String content;
	
	public Message(String message){
		if(message.isEmpty())
			throw new IllegalArgumentException();
		this.content = message;
		timestamp = System.currentTimeMillis();
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
	public String getContent() {
		return content;
	}
	public void setContent(String message) {
		this.content = message;
	}
	@Override
	public String toString() {
		return content;
	}
	
}
