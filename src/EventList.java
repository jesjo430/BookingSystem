/**
 * Singelton with all events.
 */

public class EventList
{
    private static EventList ourInstance = new EventList();

    public static EventList getInstance() {
	return ourInstance;
    }

    private EventList() {
        Event eventList[] = {};
    }

    public void addToEventList(Event event) {

    }
}
