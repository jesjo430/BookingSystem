/**
 * test class to make it easier to evolve.
 */

public class ChairToTextConverter
{
    public static String convertToText(Section section) {
	StringBuilder stringbuilder = new StringBuilder();

	int height = section.getHeight();
	int width = section.getWidth();

	for (int h = 0; h < height ; h++) {
	    for (int w = 0; w < width; w++) {
		seatStatus(section.getSeatAt(h, w), stringbuilder);
		stringbuilder.append(" ");
	    }
	    stringbuilder.append("\n");
	}
	String text = stringbuilder.toString();
	return text;
    }

    private static StringBuilder seatStatus(Seat seat, StringBuilder stringbBuilder) {
	if (seat.isBooked()) {
	    stringbBuilder.append("F");
	}
	else {
	    stringbBuilder.append("T");
	}
	return stringbBuilder;
    }
}
