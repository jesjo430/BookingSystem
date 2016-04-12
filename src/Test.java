/**
 * Test doc
 */

public class Test
{
    public static void main(String[] args) {
	Section test = new Section(20, 20);
	SectionComponent sc = new SectionComponent(test);

	User user = new NormalUser("Jesper");

	new WindowFrame(sc);
    }
}
