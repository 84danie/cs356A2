


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

		User u3 = new User("Bob");
		u1.addObserver(u3);
		System.out.println(u1.getFollowers());
		System.out.println(u2.getFollowings());
		System.out.println(u3.getFollowings());

		System.out.println("Test UserGroup:");

		CompositeUserGroup Root = new CompositeUserGroup("Root");
		CompositeUserGroup Group1 = new CompositeUserGroup("Group 1");
		CompositeUserGroup Group2 = new CompositeUserGroup("Group 2");

		Group1.add(u2);
		Root.add(u1);
		Group2.add(u3);
		Root.add(Group1);
		Group1.add(Group2);
		Root.add(u1);

		Root.add(Group2);

		System.out.println(Root.print(" "));
	}

}
