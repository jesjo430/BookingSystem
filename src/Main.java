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
        UserList.getOurInstance().loadUserListFromFile();
	EventList.getINSTANCE().loadEventListFromFile();

	new WindowFrame();
    }
}
