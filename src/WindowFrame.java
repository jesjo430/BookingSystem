import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.awt.*;

/**
 * This class is the window of the program. It contains a section and components.
 * now with a comment.
 */
public class WindowFrame extends JFrame
{
    private static final int WINDOW_HEIGHT = 600;
    private static final int WINDOW_WIDTH = 900;

    private JFrame frame;


    public WindowFrame(SectionComponent section) {
	super("MyFrame");
	this.frame = new JFrame();
	frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	frame.add(new JButton("Left"), BorderLayout.WEST);
	frame.add(new JButton("Center"), BorderLayout.CENTER);
	frame.add(new JButton("Right"), BorderLayout.EAST);
	frame.add(new JButton("Botton"), BorderLayout.SOUTH);

	frame.setSize(300,300);

	frame.setVisible(true);
    }
}
