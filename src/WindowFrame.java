import javafx.scene.input.*;
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
    private static final int EVENT_TITLE_FONT_SIZE = 25;

    String rad0 = "0 1 0 0 0 0 0";
    String[] booking = rad0.split(" ");

    private JFrame frame = new JFrame("WindowTitle");
    private Event currentEvent = EventList.getINSTANCE().getEventList().get(0);
    private SectionComponent sectionC = currentEvent.getSectionC();
    private JLabel freeSeats;
    private JLabel bookedSeats;


    public WindowFrame() {
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	frame.setLayout(new BorderLayout());

	Container contents = frame.getContentPane();
	contents.setLayout(new MigLayout("debug", "[grow][][]","[grow][][]"));

	initContent();
    }

    private void initContent() {
	Container contents = frame.getContentPane();

	JLabel eventTitle = new JLabel(currentEvent.getTitle());
	Font titleFont = new Font("Times New Roman", Font.BOLD, EVENT_TITLE_FONT_SIZE);
	eventTitle.setFont(titleFont);

	addMenuBar();

	contents.add(eventTitle, "gap 50px, width 30, height 40, wrap");

	contents.add(createSectionGrid(sectionC));
	contents.add(createEventSelectionPanel(), "top, wrap");
	contents.add(createInfoPanel());
	contents.add(createButtons());

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


		    String chairInfo = createToolTipString(seatC);
		    seatC.setToolTipText(chairInfo);

		    updateInfoPanel();
		    seatC.repaint();
		}
	    }
	});

	JButton exit = new JButton("Exit");
	exit.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		new WriteFile(EventList.getINSTANCE().writeEventToFile(), Test.EVENT_TXT);
		System.exit(0);
	    }
	});

	panel.add(book);
	panel.add(exit);
	return panel;
    }

    private JPanel createEventSelectionPanel() {
	DefaultListModel<String> listModel = new DefaultListModel<>();
	JList<String> eventList = new JList(listModel);

	for (Event e : EventList.getINSTANCE().getEventList()) {
	    listModel.addElement(e.getTitle());
	}


	eventList.addListSelectionListener(new ListSelectionListener() {
	    @Override public void valueChanged(ListSelectionEvent e)
	    {
	        if(!e.getValueIsAdjusting()) {
		    List<String> selectedValuesList = eventList.getSelectedValuesList();

		    Event newEvent = getEventFromString(selectedValuesList.get(0));
		    SectionComponent newSectionComponent = newEvent.getSectionC();
		    currentEvent = newEvent;

		    updateContentPane(newSectionComponent);
	        }
	    }
	});

	eventList.setSelectionBackground(Color.GRAY);
	JPanel infoPanel = new JPanel();
	infoPanel.add(eventList);

	return infoPanel;
    }

    private void updateContentPane(SectionComponent sectionComponent) {
	frame.getContentPane().removeAll();
	sectionC = sectionComponent;
	initContent();
	frame.repaint();
    }

    private Event getEventFromString(String comp) {
	for (Event event : EventList.getINSTANCE().getEventList()) {
	    if (event.getTitle().equals(comp)) {
		return event;
	    }
	}
	return EventList.getINSTANCE().getEventList().get(0);
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

    private JPanel createInfoPanel() {
	JPanel infoPanel = new JPanel();
	infoPanel.setLayout(new MigLayout());

	freeSeats = new JLabel("Free: " + currentEvent.getSectionC().getSection().getAmountOfFreeSeats());
	bookedSeats = new JLabel("Booked: " + (currentEvent.getSectionC().getSection().getAmountOfFreeSeats() - sectionC.getSection().getTotalSeats()));

	infoPanel.add(freeSeats, "wrap");
	infoPanel.add(bookedSeats);

	return infoPanel;
    }

    private void updateInfoPanel() {
	freeSeats.setText("Free: " + currentEvent.getSectionC().getSection().getAmountOfFreeSeats());
	bookedSeats.setText("Booked: " + (sectionC.getSection().getTotalSeats() - currentEvent.getSectionC().getSection().getAmountOfFreeSeats()));
    }

    private void addMenuBar() {
	JMenuBar menuBar;
	JMenu fileMenu, helpMenu;
	JMenuItem exitItem = new JMenuItem("Quit");
	JMenuItem helpItem = new JMenuItem("Instructions");

	menuBar = new JMenuBar();

	fileMenu = new JMenu("File");
	fileMenu.setMnemonic('F');

	fileMenu.addSeparator();
	exitItem.setAccelerator(KeyStroke.getKeyStroke('Q' ,ActionEvent.CTRL_MASK));

	exitItem.setToolTipText("Exit application");
	exitItem.addActionListener(new ActionListener() {
	    @Override public void actionPerformed(ActionEvent event) {
		System.exit(0);
	    }
	});

	helpMenu = new JMenu("Help");
	helpMenu.setMnemonic('H');

	helpItem.setAccelerator(KeyStroke.getKeyStroke('I' ,ActionEvent.CTRL_MASK));
	helpItem.setToolTipText("Instructions");
	helpItem.addActionListener(new ActionListener() {
	    @Override public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(frame, "Sorry, no instructions avalible.");
	    }
	});


	fileMenu.add(exitItem);
	helpMenu.add(helpItem);

	menuBar.add(fileMenu);
	menuBar.add(helpMenu);
	frame.setJMenuBar(menuBar);
    }
}