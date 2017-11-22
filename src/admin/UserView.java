package admin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import data.Message;
import data.User;

public class UserView implements ActionListener {
	private JList<User> followers;
	private JList<Message> newsFeed;
	private JButton followButton;
	private JButton tweetButton;
	private JPanel followPanel;
	private JPanel newsFeedPanel;
	private JTextField tweet;
	private JTextField userId;
	private JButton lastUpdated;
	private Calendar creationTime;

	private User user;
	private AdminControlPanel admin;

	public UserView(User user, AdminControlPanel admin) {
		this.user = user;
		this.admin = admin;
	}

	public void display() {
		JFrame mainFrame = new JFrame(user.toString());
		mainFrame.setLayout(new GridLayout(2, 1));

		buildFollowPanel();
		buildNewsFeedPanel();

		mainFrame.add(followPanel);
		mainFrame.add(newsFeedPanel);

		mainFrame.setPreferredSize(new Dimension(300, 300));
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void buildFollowPanel() {
		followPanel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel(new BorderLayout());

		followButton = new JButton("Follow");
		userId = new JTextField();

		followButton.addActionListener(this);
		
		creationTime = Calendar.getInstance();
		creationTime.setTimeInMillis(user.getCreationTime());
		String creationDisplay = "Created: "+creationTime.getTime().toString();
		JLabel creation = new JLabel(creationDisplay);
		creation.setHorizontalAlignment(SwingConstants.CENTER);
		
		buttonPanel.add(creation,BorderLayout.NORTH);
		buttonPanel.add(userId, BorderLayout.CENTER);
		buttonPanel.add(followButton, BorderLayout.EAST);
		followPanel.add(buttonPanel, BorderLayout.NORTH);

		if (user.getFollowings() != null) {
			followers = new JList(user.getFollowings().toArray());
		} else {
			followers = new JList<User>();
		}

		JScrollPane scroller = new JScrollPane(followers);
		followPanel.add(scroller, BorderLayout.CENTER);
	}

	private void buildNewsFeedPanel() {
		newsFeedPanel = new JPanel(new GridLayout(3, 1));
		JPanel buttonPanel = new JPanel(new BorderLayout());

		tweetButton = new JButton("Tweet");
		tweet = new JTextField();

		tweetButton.addActionListener(this);
		
		lastUpdated = new JButton("Get Last Update Time");
		lastUpdated.addActionListener(this);

		buttonPanel.add(tweet, BorderLayout.CENTER);
		buttonPanel.add(tweetButton, BorderLayout.EAST);
		newsFeedPanel.add(buttonPanel);

		if (user.getNewsFeed() != null) {
			newsFeed = new JList<Message>(user.getNewsFeed());
		} else {
			newsFeed = new JList<Message>();
		}

		JScrollPane scroller = new JScrollPane(newsFeed);
		newsFeedPanel.add(scroller);
		newsFeedPanel.add(lastUpdated);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() == followButton) {
			User follow = admin.getRoot().getUser(userId.getText());
			if (follow != null) {
				if (!follow.equals(user)) {
					follow.addObserver(user);

					DefaultListModel<User> model = new DefaultListModel<User>();
					for (User u : user.getFollowings())
						model.addElement(u);

					followers.setModel(model);
					userId.setText("");
				} else {
					JOptionPane.showMessageDialog(null, "Sorry, you cannot follow yourself.", "User View Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "User not found.", "User View Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (arg0.getSource() == tweetButton) {
			try {
				user.postMessage(new Message(tweet.getText()));
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, "Cannot post an empty message.", "User View Error",
						JOptionPane.ERROR_MESSAGE);
			}
			newsFeed.setModel(user.getNewsFeed());
			tweet.setText("");
		}
		else if(arg0.getSource() == lastUpdated){
			creationTime.setTimeInMillis(user.getLastUpdateTime());
			String lastUpdateDisplay = "Last Updated: "+creationTime.getTime().toString();
			JOptionPane.showMessageDialog(null, lastUpdateDisplay);
		}
	}
}
