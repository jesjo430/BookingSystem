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

	JPanel infoPanel = createInfoPanel();
	JPanel sectionPanel = createSectionGrid(sectionComp, contents);

	contents.add(infoPanel, BorderLayout.EAST);
	contents.add(sectionPanel, BorderLayout.CENTER);

	frame.setSize(FRAMESIZEX, FRAMESIZEY);
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

    private JPanel createSectionPanel(SectionComponent sectionComp) {
	JPanel sectionPanel = new JPanel();

	sectionPanel.add(sectionComp);

	return sectionPanel;
    }

    private JPanel createSectionGrid(SectionComponent sectionComp, Container contents) {
	JPanel sectionPanel = new JPanel();
	sectionPanel.setBackground(Color.GRAY);

	sectionPanel.setLayout(new GridLayout(10,10));
	Section section = sectionComp.getSection();

	for (int h = 0; h < section.getHeight() ; h++) {
	    for (int w = 0; w < section.getWidth(); w++) {
		SeatComponent seatC = new SeatComponent(section.getSeatAt(h, w));

		String chairInfo = createInfoString(seatC);
		seatC.setToolTipText(chairInfo);

		seatC.addMouseListener(new Listener());

		sectionPanel.add(seatC);
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
