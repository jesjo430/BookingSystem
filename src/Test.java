/**
 * Test doc
 */

public class Test
{
    /**
     * Event.txt the file containing data about the saved events.
     */
    public static final String EVENT_TXT = "event.txt";

    public static void main(String[] args) {

	EventList.getINSTANCE().loadEventListFromFile();

	new WindowFrame();




	ReadFile read = new ReadFile();
	String string = read.ReadFile(EVENT_TXT);
	System.out.println(string);

	System.out.println(" \n ------------------------------------------------------------------ \n");

	System.out.println("DONE!");

//	System.exit(0);
    }
}
