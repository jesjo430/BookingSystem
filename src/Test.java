/**
 * Test doc
 */

public class Test
{
    public static void main(String[] args) {
	Section test1 = new Section(20, 20);
	Section test2 = new Section(20, 20);
	Section test3 = new Section(20, 20);

	EventList.getINSTANCE().addToEventList(new Event(test1, "Jesper Del 1", "12:00", "1 Jan"));
	EventList.getINSTANCE().addToEventList(new Event(test2, "Jesper Del 2", "14:00", "1 Jan"));
	EventList.getINSTANCE().addToEventList(new Event(test3, "Jesper Del 3", "16:00", "1 Jan"));

	new WindowFrame();

    }
}
