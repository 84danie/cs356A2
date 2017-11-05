package stats;

import java.util.HashSet;
import java.util.Set;

import data.Message;
import data.User;


public class CountMessagesVisitor implements CountElementVisitor{
	private Set<Message> messages = new HashSet<Message>();
	private String[] positive = {"good","great","fun","happy",
								 "positive","yu sun","memes","meme",
								 "food","in n out","bacon","cats","cat",
								 "coffee","joyful","christmas","family",
								 "fortune"};
	private int numPositive = 0;
	
	public void visit(User u){
		for(int i = 0; i <  u.getNewsFeed().size(); i++)
			if(messages.add(u.getNewsFeed().getElementAt(i))){
				for(String s : positive){
					if(u.getNewsFeed().getElementAt(i).getContent().contains(s)){
						numPositive++;			
						break;
					}
				}
			}
	}
	public int getMessagesCount(){
		return messages.size();
	}
	public double getPositivePercent(){
		if(getMessagesCount()==0)
			return 0;
		return (numPositive / (double)getMessagesCount())*100;
	}
	
}
