import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * This class is the window of the program. It contains a section and components.
 * now with a comment.
 */

public class WindowFrame extends JFrame
{
    private JFrame frame = new JFrame("WindowTitle");
    private SectionComponent sectionC = EventList.getInstance().getEventList().get(0).getSectionC();
    private Event currentEvent = EventList.getInstance().getEventList().get(0);


    public WindowFrame(SectionComponent sectionComp) {
	frame = new JFrame("WindowTitle");
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	Container contents = frame.getContentPane();
	contents.setLayout(new MigLayout("debug", "[grow][][]","[grow][][]"));

	initContent();

	frame.pack();
	frame.setVisible(true);
    }

    private void initContent() {
	Container contents = frame.getContentPane();

	contents.add(new JTextArea(EventList.getInstance().getEventList().get(0).getName()), "width 30, wrap");
	contents.add(createSectionGrid(sectionC));
	contents.add(createEventSelectionPanel(), "height 100, width 50, wrap");
	contents.add(createButtons(), "skip");

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

    private JPanel createEventSelectionPanel() {
	JList<String> eventList;
	DefaultListModel<String> listModel = new DefaultListModel<>();

	for (Event e : EventList.getInstance().getEventList()) {
	    listModel.addElement(e.getName());
	}

	eventList = new JList(listModel);

	eventList.addListSelectionListener(new ListSelectionListener() {
	    @Override public void valueChanged(ListSelectionEvent e)
	    {
	        if(!e.getValueIsAdjusting()) {
		    List<String> selectedValuesList = eventList.getSelectedValuesList();
	            System.out.println(selectedValuesList);

		    Event newEvent = getEventFromString(selectedValuesList.get(0));
		    SectionComponent newSectionComponent = newEvent.getSectionC();
		    currentEvent = newEvent;

		    changeSectionOnContentPane(newSectionComponent);
	        }
	    }
	});

	JPanel infoPanel = new JPanel();
	infoPanel.add(eventList);

	return infoPanel;
    }

    private void changeSectionOnContentPane(SectionComponent sectionComponent) {
//	sectionC = sectionComponent;
//	frame.getContentPane().removeAll();
//	initContent();
	frame.getContentPane().repaint();
    }

    private Event getEventFromString(String comp) {
	for (Event event : EventList.getInstance().getEventList()) {
	    if (event.getName().equals(comp)) {
		System.out.println("YAY");
		return event;
	    }
	}
	System.out.println("NO");
	return EventList.getInstance().getEventList().get(0);
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