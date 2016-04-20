import java.util.ArrayList;
import java.util.List;

/**
 * List over users, Singelton.
 */
public final class UserList
{
    private List<User> userList;

    private static UserList ourInstance = new UserList();

    public static UserList getOurInstance() {
	return ourInstance;
    }

    private UserList() {
        this.userList = new ArrayList<>();
    }

    public void addToUserList(User user) {
        userList.add(user);
    }

    public User getUserFromString(String input) {
	for (User user : userList) {
	    if (user.getName().equals(input)) {
		return user;
	    }
	}
	return new NormalUser("false", "false");
    }

    public void writeUserListToFile() {
	StringBuilder sb = new StringBuilder();
	sb.append("User:'");

	for(User user: userList) {
	    sb.append("¤'");

	    sb.append(user.getName());
	    sb.append("'");

	    sb.append(user.getPassword());
	    sb.append("'");

	    sb.append(user.getAuthorisation());
	    sb.append("'\n");
	}
	new WriteFile(sb.toString(), "user.txt");
    }

    public void loadUserListFromFile() {
	ReadFile read = new ReadFile();
	String readUserString = read.readFiles(Main.USER_TXT);
	String[] userList = readUserString.split("¤");
	for (String user : userList) {
	    String[] userData = user.split("'");
	    if (userData.length > 2) {
		if (userData[3].equals("normal")) {
		    User newNU = new NormalUser(userData[1], userData[2]);
		    UserList.getOurInstance().addToUserList(newNU);
		}
	    }
	}
    }
}


