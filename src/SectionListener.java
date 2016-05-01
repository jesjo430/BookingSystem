import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Listeners to sections.
 */

public class SectionListener extends MouseAdapter
{
    /**
     * Listens to mouseClicks on the SeatComponents.
     * @param e what SeatComponent.
     */
    @Override public void mousePressed(final MouseEvent e) {
	SeatComponent seatC = (SeatComponent) e.getSource();

	if(WindowFrame.isUnbooking) {
	    if(!seatC.getSeat().getStatus()) {
		seatC.getSeat().setStatus(true);
		seatC.repaint();
	    }
	    else {
		seatC.getSeat().setStatus(false);
		seatC.repaint();
	    }

	}
	else if (!seatC.getSeat().getStatus()) {
	    if (seatC.seatInMarkedSeatList(seatC)) {
		seatC.getSeat().setSeatIsMarked(false);
		seatC.removeFromMarkedList(seatC);
	    } else {
		seatC.getSeat().setSeatIsMarked(true);
		seatC.addToMarkedList(seatC);
	    }
	    seatC.repaint();
	}
    }

}
