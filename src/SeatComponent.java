import javafx.geometry.Rectangle2D;

import javax.swing.*;
import java.awt.*;

/**
 * yep
 */

public class SeatComponent extends JComponent
{
    private Seat seat;
    private static final int CHAIR_SIZE = 300;

    public SeatComponent(Seat seat) {
	this.seat = seat;
    }

    private Color getSeatColorAt(){
	if (seat.isBooked()) {
		    return Color.RED;
		}
		return Color.BLUE;
    }

    @Override protected void paintComponent(Graphics g) {
	final Graphics2D g2d = (Graphics2D) g;

	g2d.setColor(getSeatColorAt());
	Shape rect = new Rectangle(CHAIR_SIZE, CHAIR_SIZE);
	g2d.draw(rect);
    }

    public Seat getSeat() {
	return seat;
    }
}
