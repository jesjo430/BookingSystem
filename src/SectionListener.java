import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Listnera
 */

public class SectionListener implements MouseListener
{

    @Override public void mouseClicked(final MouseEvent e) {
    }

    /**
     * Listens to mouseClicks on the SeatComponents.
     * @param e what SeatComponent.
     */
    @Override public void mousePressed(final MouseEvent e) {
	SeatComponent seatC = (SeatComponent) e.getSource();

	if (seatC.seatInMarkedSeatList(seatC)) {
	    seatC.getSeat().setSeatIsMarked(false);
	    seatC.removeFromMarkedList(seatC);
	}
	else {
	    seatC.getSeat().setSeatIsMarked(true);
	    seatC.addToMarkedList(seatC);
	}
	seatC.repaint();
    }

    @Override public void mouseReleased(final MouseEvent e) {
    }

    @Override public void mouseEntered(final MouseEvent e) {
    }

    @Override public void mouseExited(final MouseEvent e) {
    }
}
