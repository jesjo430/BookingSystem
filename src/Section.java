/**
 * The chair sections, a 2-dimensional array representing the chairs.
 */
public class Section
{
    private int height, width;
    private Seat[][] seats;

    public Section(final int height, final int width) {
	this.height = height;
	this.width = width;
	initialize();
    }

    private void initialize() {
	seats = new Seat[height][width];
	for (int h = 0; h < seats.length; h++) {
	    for (int w = 0; w < seats[0].length; w++) {
		seats[h][w] = new Seat(h, w);
	    }
	}
    }

    public void book(int row, int seat, String name) {
	Seat place = seats[row][seat];
	if (!place.isBooked()) {
	    place.setStatus(true);
	    place.setName(name);
	}
    }

    public int getHeight() {
	return height;
    }

    public int getWidth() {
	return width;
    }

    public Seat[][] getSeats() {
	return seats;
    }

    public Seat getSeatAt(int row, int seat) {
	return seats[row][seat];
    }
}
