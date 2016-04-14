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

    public String writeEventToFile() {
   	StringBuilder text = new StringBuilder();
   	for (Event event : eventList) {
            text.append(event + "¤" +"\n");
        }

   	return text.toString();
       }

    public void loadEventListFromFile() {
	ReadFile read = new ReadFile();
	String string = read.ReadFile(Test.EVENT_TXT);
	String[] eventLists = string.split("¤");
	for (String strings : eventLists) {
	    String[] eventDetails = strings.split("'");
	    if (eventDetails.length > 1) {
		Section section = new Section(Integer.parseInt(eventDetails[2]), Integer.parseInt(eventDetails[4]));
		Event event = new Event(section, eventDetails[6], eventDetails[8], eventDetails[10]);
		EventList.getINSTANCE().addToEventList(event);

		String[] bookings = eventDetails[12].split(" ");
		int row = 0;
		int seat = 0;
		for(String bool : bookings) {
		    if(seat == Integer.parseInt(eventDetails[4])) {
			seat = 0;
			row += 1;
		    }
		    if(row == Integer.parseInt(eventDetails[2])) {
			break;
		    }
		    if (!bool.equals("false")) {
			section.getSeatAt(row, seat).book(row, seat);
			System.out.println("JJFHKF");
		    }
		    seat += 1;

		}
	    }
	}
    }
}


