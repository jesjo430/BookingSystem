import javax.swing.*;
import java.awt.*;

/**
 * yep
 */

public class SeatComponent extends JComponent
{
    private Seat seat;
    private static final int CHAIR_SIZE = 40;

    public SeatComponent(Seat seat) {
	this.seat = seat;
    }

    private Color getSeatColorAt(){
	if (seat.isBooked()) {
		    return Color.RED;
		}
		return Color.BLUE;
    }

    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	g2d.setColor(getSeatColorAt());
	g2d.fillRect(CHAIR_SIZE, CHAIR_SIZE, CHAIR_SIZE-(CHAIR_SIZE/2), CHAIR_SIZE-(CHAIR_SIZE/2));
    }
}
