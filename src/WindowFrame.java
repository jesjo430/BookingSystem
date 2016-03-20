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
    public WindowFrame(SectionComponent sectionComp) {
	JFrame frame = new JFrame("WindowTitle");
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	JPanel infoPanel = createInfoPanel();

	Container contents = frame.getContentPane();
	contents.add(infoPanel, BorderLayout.EAST);
	//contents.add(sectionComp, BorderLayout.CENTER);
	createSectionGrid(sectionComp, contents);


	frame.setSize(500,500);
	frame.setVisible(true);
    }

    private JPanel createInfoPanel() {
	JTextArea info = new JTextArea();
	info.setText("JEIJDEIJDIEJ");
	info.setEditable(false);

	JLabel infoTitle = new JLabel("Info");

	JPanel infoPanel = new JPanel();

	infoPanel.add(infoTitle);
	infoPanel.add(info);

	return infoPanel;
    }

    private JPanel createSectionPanel(SectionComponent sectionComp) {
	JPanel sectionPanel = new JPanel();

	sectionPanel.add(sectionComp);

	return sectionPanel;
    }

    private void createSectionGrid(SectionComponent SectionComp, Container contents) {
	Section section = SectionComp.getSection();
	System.out.println(section.getWidth());
	for (int h = 0; h < section.getHeight() ; h++) {
	    for (int w = 0; w < section.getWidth(); w++) {
		SeatComponent seatC = new SeatComponent(section.getSeatAt(h, w));
		contents.add(seatC, BorderLayout.CENTER);
	    }
	}
    }
}
