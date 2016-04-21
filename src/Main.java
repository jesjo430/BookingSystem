import java.io.IOException;

/**
 * Main running programfiles.
 */
public final class Main
{
    /**
     * String representing the text file (.txt) to save all events in.
     */
    public static final String EVENT_TXT = "event.txt";

    /**
     * String representing the text file (.txt) to save all users in.
     */
    public static final String USER_TXT = "user.txt";


    private Main() {}

    public static void main(String[] args) {
	try {
	    UserList.getOurInstance().loadUserListFromFile();
	}
	catch (IOException e) {
	    e.printStackTrace();
	}

	try {
	    EventList.getINSTANCE().loadEventListFromFile();
	} catch (IOException e) {
	    e.printStackTrace();
	}

	new WindowFrame();
    }
}
