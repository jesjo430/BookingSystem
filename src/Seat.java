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
    private ChairType chairType;

    public Seat(final int row, final int seat) {
	this.row = row;
	this.seat = seat;
	this.status = false;
	initialize();
    }

    private void initialize() {
	this.status = false;
	this.name = null;
	this.chairType = ChairType.SINGLE;
    }

    public void setStatus(final boolean status) {
	this.status = status;
    }

    public boolean isBooked() {
	return status;
    }

    public void setName(final String name) {
	this.name = name;
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

    public ChairType getChairType() {
   	return chairType;
       }
}
