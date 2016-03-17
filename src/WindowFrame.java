import javax.swing.*;

/**
 * This class is the window of the program. It contains a section and components.
 * now with a comment.
 */
public class WindowFrame extends JFrame
{
    private static final int WINDOW_HEIGHT = 600;
    private static final int WINDOW_WIDTH = 900;

    public WindowFrame(Section section) {
	super("MyFrame");
	setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


	setVisible(true);
    }
}
