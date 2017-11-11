package stats;

import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import data.Message;
import data.User;


/**
 * This visitor visits users in regards to their "content", namely their Messages.
 * 
 * Positive messages are messages containing the following key words:
 * 
 * "good","great","fun","happy", "positive",
 * "yu sun","memes","meme","food","in n out",
 * "bacon","cats","cat","coffee","joyful",
 * "christmas","family","fortune".
 *
 */
public class ContentVisitor implements Visitor{
	private Set<Message> messages = new HashSet<Message>();
	private String[] positive = {"good","great","fun","happy",
							"positive","yu sun","memes","meme",
							"food","in n out","bacon","cats","cat",
							"coffee","joyful","christmas","family",
													"fortune"};
	private int numPositive = 0;
	
	@Override
	public void visit(User u){
		ListModel<Message> newsFeed =  u.getNewsFeed();
		for(int i = 0; i <  newsFeed.getSize(); i++)
			if(messages.add(newsFeed.getElementAt(i))){
				checkPositiveMsgs(newsFeed.getElementAt(i));
			}
	}
	/**
	 * @return the total number of messages sent
	 */
	public int getMessagesCount(){
		return messages.size();
	}
	/**
	 * @return the total percent of positive messages sent
	 */
	public double getPositivePercent(){
		if(getMessagesCount()==0)
			return 0;
		return (numPositive / (double)getMessagesCount())*100;
	}
	private void checkPositiveMsgs(Message message){
		for(String s : positive){
			if(message.getContent().contains(s)){
				numPositive++;			
				break;
			}
		}
	}

}
