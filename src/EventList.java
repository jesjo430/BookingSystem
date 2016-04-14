import java.util.ArrayList;
import java.util.List;

/**
 * Singelton with all events.
 */

public final class EventList
{
    private List<Event> eventList;

    private final static EventList INSTANCE = new EventList();

    public static EventList getINSTANCE() {
	return INSTANCE;
    }

    private EventList() {
        this.eventList = new ArrayList<>();
    }

    public void addToEventList(Event event) {
        eventList.add(event);
    }

    public List<Event> getEventList() {
        return eventList;
    }

}
