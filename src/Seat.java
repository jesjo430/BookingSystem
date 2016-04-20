/**
 * The representation of every seat, containing info as status that tells if seat is free,
 * eventuall name of booker and row- and seat-number on the section it belongs.
 */
public class Seat
{
    private boolean status;
    private String name;
    private final int row;
    private final int seat;

    /**
     * Boolean value on the question: "IS the seat booked?".
     */
    public boolean seatIsMarked = false;

    public Seat(final int row, final int seat) {
	this.row = row;
	this.seat = seat;
	this.status = false;
	initialize();
    }

    private void initialize() {
	this.status = false;
	this.name = null;
    }

    /**
     * changes the current status of the this object if not true.
     */
    public void book() {
    	if (!status) {
    	    status = true;
    	}
    }

    public void setStatus(final boolean status) {
	this.status = status;
    }

    public boolean getStatus() {
	return status;
    }

    public String getName() {
	return name;
    }

    public int getRow() {
	return row;
    }

    public int getSeat() {
	return seat;
    }

    public void setSeatIsMarked(final boolean bol) {
    	seatIsMarked = bol;
        }
}