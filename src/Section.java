/**
 * The chair sections, a 2-dimensional array representing the chairs.
 */
public class Section
{
    private int height, width;
    private Seat[][] seats;
    private int totalSeats;


    public Section(final int height, final int width) {
	this.height = height;
	this.width = width;
	this.totalSeats = height*width;
	initialize();
    }

    /**
     * creates the section as Seat[][]
     */
    private void initialize() {
	seats = new Seat[height][width];
	for (int h = 0; h < seats.length; h++) {
	    for (int w = 0; w < seats[0].length; w++) {
		seats[h][w] = new Seat(h, w);
	    }
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

    /**
     * @param row int
     * @param seat int
     * @return seat object at point.
     */
    public Seat getSeatAt(int row, int seat) {
	return seats[row][seat];
    }

    /**
     * @return amount of seats with status as false
     */
    public int getAmountOfFreeSeats() {
	int amount = 0;
	for (Seat[] row : seats) {
	    for (Seat seat : row) {
		if (!seat.getStatus()) {
		    amount += 1;
		}
	    }
	}
	return amount;
    }

    public int getTotalSeats() {
	return totalSeats;
    }

    /**
     * Changes the status of every seat to false.
     */
    public void unBookAllSeats() {
	for (Seat[] row : seats) {
	    for (Seat seat : row) {
		seat.setStatus(false);
		seat.setSeatIsMarked(false);
	    }
	}
    }

    /**
     * sets all seats marked fields to false.
     */
    public void unmarkAllSeats() {
	for (Seat[] row : seats) {
	    for (Seat seat : row) {
		seat.setSeatIsMarked(false);
	    }
	}
    }

    public String getChairStatusAndName() {
       StringBuilder stringBuilder = new StringBuilder();
       for (Seat[] row : seats) {
	   for (Seat seat : row) {
	       stringBuilder.append(seat.getStatus());
	       stringBuilder.append("@");
	       stringBuilder.append(seat.getName());
	       stringBuilder.append(" ");
	   }
       }
       return stringBuilder.toString();
   }
}
