import javax.swing.*;
import java.awt.*;

/**
 * The graphical reflection of the Setion.
 */
public class SectionComponent extends JComponent
{
    private Section section;
    private static final int CHAIR_SIZE = 40;

    public SectionComponent(final Section section) {
	this.section = section;
    }

    private Color getSeatColorAt(int y, int x, Section section) {
	if (section.getSeats()[y][x].getStatus()) {
	    return Color.PINK;
	}
	return Color.BLACK;
    }

    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	g2d.setColor(Color.GRAY);
	for (int h = 0; h < section.getHeight(); h++) {
	    for (int w = 0; w < section.getWidth(); w++) {
		g2d.setColor(getSeatColorAt(h, w, section));
		g2d.fillRect(w*CHAIR_SIZE, h*CHAIR_SIZE, CHAIR_SIZE-(CHAIR_SIZE/2), CHAIR_SIZE-(CHAIR_SIZE/2));
	    }
	}
    }

    public Section getSection() {
	return section;
    }
}
