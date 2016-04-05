import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Listener implements MouseListener
{

    @Override public void mouseClicked(final MouseEvent e) {

    }

    @Override public void mousePressed(final MouseEvent e) {
	SeatComponent seatC = (SeatComponent) e.getSource();
	seatC.getSeat().book(3,3, "Mamma");
	seatC.repaint();
    }

    @Override public void mouseReleased(final MouseEvent e) {

    }

    @Override public void mouseEntered(final MouseEvent e) {
	SeatComponent seatC = (SeatComponent) e.getSource();
    }

    @Override public void mouseExited(final MouseEvent e) {

    }
}
