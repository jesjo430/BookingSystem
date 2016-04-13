/**
 * Test doc
 */

public class Test
{
    public static void main(String[] args) {
	Section test0 = new Section(20, 20);
	Section test1 = new Section(20, 30);
	Section test2 = new Section(20, 20);
	Section test3 = new Section(20, 20);

	SectionComponent sc0 = new SectionComponent(test0);
	SectionComponent sc1 = new SectionComponent(test1);
	SectionComponent sc2 = new SectionComponent(test2);
	SectionComponent sc3 = new SectionComponent(test3);

	EventList.getInstance().addToEventList(new Event(test1, sc0, "Jesper Del 1", "12:00", "1 Jan"));
	EventList.getInstance().addToEventList(new Event(test2, sc1, "Jesper Del 2", "14:00", "1 Jan"));
	EventList.getInstance().addToEventList(new Event(test3, sc2,  "Jesper Del 3", "16:00", "1 Jan"));


	new WindowFrame(sc3);

    }
}
