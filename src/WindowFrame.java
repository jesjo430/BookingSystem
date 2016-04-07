import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;


/**
 * This class is the window of the program. It contains a section and components.
 * now with a comment.
 */
public class WindowFrame extends JFrame
{
    public static int FRAMESIZEX = 700;
    public static int FRAMESIZEY = 700;

    private JTextArea infoArea = new JTextArea();
    private String infoString = "";


    public WindowFrame(SectionComponent sectionComp) {
	JFrame frame = new JFrame("WindowTitle");
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	Container contents = frame.getContentPane();

	contents.setLayout(new MigLayout("debug"));

	contents.add(createSectionGrid(sectionComp, contents), "width 400, height 500");

	frame.pack();
	frame.setVisible(true);
    }

    private JPanel createInfoPanel() {
	infoArea.setText(infoString);
	infoArea.setToolTipText("NOpe" );
	infoArea.setEditable(false);

	JLabel infoTitle = new JLabel("Info");

	JPanel infoPanel = new JPanel();

	infoPanel.add(infoTitle);
	infoPanel.add(infoArea);

	return infoPanel;
    }

    public void updateInfoPael() {
	infoArea.setText(infoString);
    }

    private JPanel createSectionGrid(SectionComponent sectionComp, Container contents) {
	JPanel sectionPanel = new JPanel();
	sectionPanel.setBackground(Color.BLACK);

	sectionPanel.setLayout(new MigLayout("debug, gap 1"));
	Section section = sectionComp.getSection();

	for (int h = 0; h < section.getHeight() ; h++) {
	    for (int w = 0; w < section.getWidth(); w++) {
		SeatComponent seatC = new SeatComponent(section.getSeatAt(h, w));

		String chairInfo = createInfoString(seatC);
		seatC.setToolTipText(chairInfo);
		seatC.addMouseListener(new Listener());


		if (w == section.getWidth()-1) {
		    sectionPanel.add(seatC, "wrap");
		    //sectionPanel.add(new JButton(""), "wrap");
		}
		else {
		    sectionPanel.add(seatC);
		    //sectionPanel.add(new JButton(""));
		}
	    }
	}
	return sectionPanel;
    }

    private String createInfoString(SeatComponent seatC) {
	String chairRow = Integer.toString((seatC.getSeat().getRow()));
	String chairSeat = Integer.toString((seatC.getSeat().getSeat()));
	String chairName = seatC.getSeat().getName();

	String text = "Row: " + chairRow + " \n" + "Seat: " + chairSeat + " \n" + "Name: " + chairName;

	return text;
    }
}
