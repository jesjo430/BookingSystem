/**
 * Test doc
 */

public class Test
{
    public static void main(String[] args) {
	Section test = new Section(10, 20);

	String text = ChairToTextConverter.convertToText(test);
	System.out.println(text);
    }
}