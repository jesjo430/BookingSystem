import javax.swing.*;
import java.awt.*;

/**
 * yep
 */

public class SeatComponent extends JComponent
{
    private Seat seat;
    private static final int CHAIR_SIZE = 20;

    public SeatComponent(Seat seat) {
	this.seat = seat;
    }

    private Color getSeatColorAt(){
	if (seat.getIsBooked()) {
		    return Color.RED;
		}
		return Color.BLUE;
    }

    @Override public Dimension getPreferredSize() {
	return new Dimension(CHAIR_SIZE, CHAIR_SIZE);
    }

    @Override protected void paintComponent(Graphics g) {
	final Graphics2D g2d = (Graphics2D) g;

	g2d.setColor(getSeatColorAt());
	g2d.fillRect(0,0,CHAIR_SIZE,CHAIR_SIZE);
    }

    public Seat getSeat() {
	return seat;
    }
}
