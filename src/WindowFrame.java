import javax.swing.*;
import net.miginfocom.*;

/**
 * This class is the window of the program. It contains a section and components.
 * now with a comment.
 */
public class WindowFrame extends JFrame
{
    private JFrame frame;
    private static final int WINDOW_HEIGHT = 600;
    private static final int WINDOW_WIDTH = 900;

    public WindowFrame(final SectionComponent section) {
	this.frame = new JFrame("Window");
	frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	frame.add(section);

	frame.setVisible(true);
    }

    private void addButtons() {
	frame.add(new JButton("Book All!"));
    }
}
