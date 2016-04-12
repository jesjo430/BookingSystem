/**
 * Test doc
 */

public class Test
{
    public static void main(String[] args) {
	Section test = new Section(20, 20);
	SectionComponent sc = new SectionComponent(test);

	sc.getSection().book(1,1,"Jesper");

	new WindowFrame(sc);
    }
}
