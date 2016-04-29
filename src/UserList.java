import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * List over users, Singelton.
 */
public final class UserList
{
    private final static Logger LOGGER = Logger.getLogger(UserList.class.getName());

    private static UserList ourInstance = new UserList();
    private List<User> userList;

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
	LOGGER.log(Level.SEVERE, "no user was found from given string.");
	LOGGER.log(Level.INFO, "False user was created.");
	return new User("false", "false", null);
    }

    public void removeUserFromList(User user) {
	userList.remove(user);
    }

    public String writeUserListToFile() {
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
	return sb.toString();
    }

    public void loadUserListFromFile() throws IOException {
	ReadFile read = new ReadFile();
	String readUserString = read.readFiles(Main.USER_TXT);
	String[] userList = readUserString.split("¤");
	for (String user : userList) {
	    String[] userData = user.split("'");
	    if (userData.length > 2) {
		if (userData[3].equals(Authorization.NORMAL.toString())) {
		    User newNU = new User(userData[1], userData[2], Authorization.NORMAL);
		    UserList.getOurInstance().addToUserList(newNU);
		}
		else if (userData[3].equals(Authorization.ADMIN.toString())) {
		    User newNU = new User(userData[1], userData[2], Authorization.ADMIN);
		    UserList.getOurInstance().addToUserList(newNU);
		}
		else {
		    LOGGER.log(Level.SEVERE, "User was not loaded from file");
		}
	    }
	}
    }

    public Iterable<User> getUserList() {
	return userList;
    }
}


