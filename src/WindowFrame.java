import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class is the window of the program. It contains a section and components.
 * now with a comment.
 */

public class WindowFrame extends JFrame
{

    public WindowFrame(SectionComponent sectionComp) {
	JFrame frame = new JFrame("FkngWindowTitle");
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	Container contents = frame.getContentPane();
	contents.setLayout(new MigLayout("debug", "[grow][][]","[grow][][]"));


	contents.add(createSectionGrid(sectionComp));
	contents.add(createInfoPanel(), "height 100, width 50, wrap");
	contents.add(createButtons(), "skip");

	frame.pack();
	frame.setVisible(true);
    }


    private JPanel createButtons() {
	JPanel panel = new JPanel();

	JButton book = new JButton("Book");
	book.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		for (SeatComponent seatC : SeatComponent.getMarkedSeats()) {
		    seatC.getSeat().setStatus(true);

		    //String name = JOptionPane.showInputDialog("Please enter your name: ");
		    //seatC.getSeat().setName(user.getName());

		    String chairInfo = createToolTipString(seatC);
		    seatC.setToolTipText(chairInfo);

		    seatC.repaint();
		}
	    }
	});

	panel.add(book);

	return panel;
    }

    private JPanel createInfoPanel() {
	JTextArea infoArea = new JTextArea();

	String[] eventList = {"Hej", "Vad", "Heter", "du", "?"};

	JList list = new JList(eventList);

	JPanel infoPanel = new JPanel();
	infoPanel.add(list);
	infoPanel.add(infoArea);

	return infoPanel;
    }

    private JPanel createSectionGrid(SectionComponent sectionComp) {
	JPanel sectionPanel = new JPanel();
	sectionPanel.setBackground(Color.BLACK);

	sectionPanel.setLayout(new MigLayout("gap 5"));
	Section section = sectionComp.getSection();

	for (int h = 0; h < section.getHeight() ; h++) {
	    for (int w = 0; w < section.getWidth(); w++) {
		SeatComponent seatC = new SeatComponent(section.getSeatAt(h, w));

		String chairInfo = createToolTipString(seatC);
		seatC.setToolTipText(chairInfo);
		seatC.addMouseListener(new SectionListener());

		if (w == section.getWidth()-1) {
		    sectionPanel.add(seatC, "wrap");
		}
		else {
		    sectionPanel.add(seatC);
		}
	    }
	}
	return sectionPanel;
    }

    private String createToolTipString(SeatComponent seatC) {
	String chairRow = Integer.toString((seatC.getSeat().getRow()));
	String chairSeat = Integer.toString((seatC.getSeat().getSeat()));
	String chairName = seatC.getSeat().getName();

	String text = "Row: " + chairRow + "\n" + "  Seat: " + chairSeat;
	if (chairName != null) {
	    text += " \n" + "  Name: " + chairName;
	}

	return text;
    }
}


