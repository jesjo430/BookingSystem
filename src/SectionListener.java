import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Listnera
 */

public class SectionListener extends MouseAdapter
{
    /**
     * Listens to mouseClicks on the SeatComponents.
     * @param e what SeatComponent.
     */
    @Override public void mousePressed(final MouseEvent e) {
	SeatComponent seatC = (SeatComponent) e.getSource();

	if (!seatC.getSeat().getStatus()){
	    if (seatC.seatInMarkedSeatList(seatC)) {
		seatC.getSeat().setSeatIsMarked(false);
		seatC.removeFromMarkedList(seatC);
	    } else {
		seatC.getSeat().setSeatIsMarked(true);
		seatC.addToMarkedList(seatC);
		System.out.println("Clicked");
	    }
	    seatC.repaint();
	}
    }

}
