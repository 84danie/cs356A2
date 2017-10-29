


public class Driver {

	public static void main(String[] args) {
		 javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	        		AdminControlPanel.getInstance();  
	            }
	        });
		User u1 = new User("Joe");
		User u2 = new User("Joe2");
		u1.addObserver(u2);
		System.out.println(u1.getFollowers());
		System.out.println(u2.getFollowings());
		

	}

}
