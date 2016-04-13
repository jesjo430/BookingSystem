import java.util.ArrayList;
import java.util.List;

/**
 * Singelton with all events.
 */

public class EventList
{
    private List<Event> eventList;

    private static EventList ourInstance = new EventList();

    public static EventList getInstance() {
	return ourInstance;
    }

    private EventList() {
        this.eventList = new ArrayList<Event>();
    }

    public void addToEventList(Event event) {
        eventList.add(event);
    }

    public List<Event> getEventList() {
        return eventList;
    }
}
