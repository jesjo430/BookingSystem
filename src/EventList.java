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

    public void removeFromEventList(Event event) {
	eventList.remove(event);
    }

    public List<Event> getEventList() {
        return eventList;
    }

    /**
     * creates a String with all events from eventList represented.
     * @return String with eventdata.
     */
    public String writeEventToFile() {
   	StringBuilder text = new StringBuilder();
   	for (Event event : eventList) {
            text.append(event);
	    text.append("¤");
	    text.append("\n");
        }

   	return text.toString();
       }

    /**
     * Loads and pushes events written on event.file to the EventList.
     */
    public void loadEventListFromFile() {
	ReadFile read = new ReadFile();
	String readEventString = read.readFiles(Main.EVENT_TXT);
	String[] eventLists = readEventString.split("¤");
	for (String strings : eventLists) {
	    String[] eventDetails = strings.split("'");
	    if (eventDetails.length > 1) {
		Section section = new Section(Integer.parseInt(eventDetails[2]), Integer.parseInt(eventDetails[4]));
		Event event = new Event(section, eventDetails[6], eventDetails[8], eventDetails[10]);
		EventList.getINSTANCE().addToEventList(event);

		String[] bookings = eventDetails[12].split(" "); // magic number not fixed baecause I've chooesen read-ability in the file.
		int row = 0;
		int seat = 0;
		for(String seatStatus : bookings) {
		    String[] statusAndName = seatStatus.split("@");
		    if(seat == Integer.parseInt(eventDetails[4])) {
			seat = 0;
			row += 1;
		    }
		    if(row == Integer.parseInt(eventDetails[2])) {
			break;
		    }
		    if (!statusAndName[0].equals("false")) {
			if(statusAndName.length > 1) {
			    section.getSeatAt(row, seat).book(statusAndName[1]);
			}
			section.getSeatAt(row, seat).book("null");
		    }
		    seat += 1;
		}
	    }
	}
    }
}


