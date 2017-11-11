package admin;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import data.Message;
import data.User;

public class UserView implements ActionListener, FocusListener{
	private JList<User> followers;
	private JList<Message> newsFeed;
	private JButton followButton;
	private JButton tweetButton;
	private JPanel followPanel;
	private JPanel newsFeedPanel;
	private JTextField tweet;
	private JTextField userId;
	private String followUser = "";
	private String tweetMessage = "";

	private User user;
	private AdminControlPanel admin;

	public UserView(User user, AdminControlPanel admin){
		this.user = user;
		this.admin = admin;
	}
	public void display(){
		JFrame mainFrame = new JFrame(user.toString());	
		mainFrame.setLayout(new GridLayout(2,1));

		buildFollowPanel();
		buildNewsFeedPanel();

		mainFrame.add(followPanel);
		mainFrame.add(newsFeedPanel);

		mainFrame.setPreferredSize(new Dimension(300, 200));
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void buildFollowPanel(){
		followPanel = new JPanel(new GridLayout(2,1));
		JPanel buttonPanel = new JPanel(new GridLayout(1,2));

		followButton = new JButton("Follow");
		userId = new JTextField();

		followButton.addActionListener(this);
		userId.addFocusListener(this);

		buttonPanel.add(userId);
		buttonPanel.add(followButton);
		followPanel.add(buttonPanel);

		if(user.getFollowings()!=null){
			followers = new JList(user.getFollowings().toArray());
		}
		else{
			followers = new JList<User>();
		}

		JScrollPane scroller = new JScrollPane(followers);
		followPanel.add(scroller);
	}
	private void buildNewsFeedPanel(){
		newsFeedPanel = new JPanel(new GridLayout(2,1));
		JPanel buttonPanel = new JPanel(new GridLayout(1,2));

		tweetButton = new JButton("Tweet");
		tweet = new JTextField();

		tweetButton.addActionListener(this);
		tweet.addFocusListener(this);

		buttonPanel.add(tweet);
		buttonPanel.add(tweetButton);
		newsFeedPanel.add(buttonPanel);

		if(user.getNewsFeed()!=null){
			newsFeed = new JList<Message>(user.getNewsFeed());
		}
		else{
			newsFeed = new JList<Message>();
		}

		JScrollPane scroller = new JScrollPane(newsFeed);
		newsFeedPanel.add(scroller);
	}
	@Override
	public void focusGained(FocusEvent e) {
		// nothing to be done here
	}
	@Override
	public void focusLost(FocusEvent e) {
		if(e.getSource() == userId){
			followUser = userId.getText();
		}
		else if(e.getSource() == tweet){
			tweetMessage = tweet.getText();
		}
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				if(arg0.getSource() == followButton){
					User follow = admin.getRoot().getUser(followUser);
					if(follow!=null){
						if(!follow.equals(user)){
							follow.addObserver(user);

							DefaultListModel<User> model = new DefaultListModel<User>();
							for(User u : user.getFollowings())
								model.addElement(u);

							followers.setModel(model);
							userId.setText("");
							followUser="";		
						}
						else{
							JOptionPane.showMessageDialog(null,"Sorry, you cannot follow yourself.");
						}
					}
					else{
						JOptionPane.showMessageDialog(null,"User not found.");
					}
				}
				else if(arg0.getSource() == tweetButton){
					try{
						user.postMessage(new Message(tweetMessage));	
					}catch (IllegalArgumentException e){
						JOptionPane.showMessageDialog(null,"Cannot post an empty message.");
					}
					newsFeed.setModel(user.getNewsFeed());
					tweet.setText("");
					tweetMessage = "";	
				}
			}
		});

	}

}
