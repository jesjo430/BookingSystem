/**
 * Test doc
 */

public class Test
{
    /**
     * Event.txt the file containing data about the saved events.
     */
    public static final String EVENT_TXT = "event.txt";

    /**
     * User.txt the file containing data about the saved users.
     */
    public static final String USER_TXT = "user.txt";

    public static void main(String[] args) {

	UserList.getInstance().loadUserListFromFile();
	EventList.getINSTANCE().loadEventListFromFile();

	new WindowFrame();
    }
}
