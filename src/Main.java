import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Main running programfiles.
 */
public final class Main
{
    private final static Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static FileHandler fileHandler = null;

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
	initLogger();
	try {
	    UserList.getOurInstance().loadUserListFromFile();
	} catch (IOException e) {
	    LOGGER.log(Level.SEVERE, "Could not load users from file: " + USER_TXT);
	    e.printStackTrace();
	}

	try {
	    EventList.getINSTANCE().loadEventListFromFile();
	} catch (IOException e) {
	    LOGGER.log(Level.SEVERE, "Could not load events from file: " + EVENT_TXT);
	    e.printStackTrace();
	}

	WindowFrame wf = new WindowFrame();
	LOGGER.log(Level.INFO, "Window " + wf + " is running.");
    }

    private static void initLogger() {
	try {
	    fileHandler = new FileHandler("systemlogger.log", false);
	}
	catch (SecurityException | IOException e) {
	    e.printStackTrace();
	}

	Logger l = Logger.getLogger("logger");
	fileHandler.setFormatter(new SimpleFormatter());
	l.addHandler(fileHandler);
	l.setLevel(Level.ALL);
    }
}
