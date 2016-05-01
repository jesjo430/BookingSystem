import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Seat representation as a JComponent.
 */
public class SeatComponent extends JComponent
{
    private final Seat seat;
    private static Collection<SeatComponent> markedSeats = new ArrayList<>();

    private static final int CHAIR_SIZE = 20;

    public SeatComponent(Seat seat) {
	this.seat = seat;
    }

    private Color getSeatColor() {
	if (seat.getStatus()) {
	    return Color.RED;
	}
	else if (seat.seatIsMarked) {
	    return Color.LIGHT_GRAY;
	}
	return Color.GRAY;
    }

    @Override public Dimension getPreferredSize() {
	return new Dimension(CHAIR_SIZE, CHAIR_SIZE);
    }

    @Override protected void paintComponent(Graphics g) {
	final Graphics2D g2d = (Graphics2D) g;

	g2d.setColor(getSeatColor());
	g2d.fillRoundRect(0,0,CHAIR_SIZE,CHAIR_SIZE, 2,2);
    }

    public Seat getSeat() {
	return seat;
    }

    public void addToMarkedList(SeatComponent seatC) {
	markedSeats.add(seatC);
    }

    public static Collection<SeatComponent> getMarkedSeats() {
    	return markedSeats;
        }

    public boolean seatInMarkedSeatList(SeatComponent seatC) {
	return markedSeats.contains(seatC);
    }

    public void removeFromMarkedList(SeatComponent seatC) {
	markedSeats.remove(seatC);
    }
}
