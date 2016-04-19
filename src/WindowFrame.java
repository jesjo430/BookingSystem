import jdk.nashorn.internal.runtime.RewriteException;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is the window of the program. It contains components.
 */

public class WindowFrame extends JFrame
{
    private static final int EVENT_TITLE_FONT_SIZE = 25;

    private JFrame frame = new JFrame("WindowTitle");
    private Event currentEvent;
    private SectionComponent sectionC;
    private JLabel freeSeats;
    private JLabel bookedSeats;

    private User user;

    private int horizontalStrut = 10;


    public WindowFrame() {
	loginDialog();

	frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	frame.setLayout(new BorderLayout());

	currentEvent = EventList.getINSTANCE().getEventList().get(0);
	sectionC  = currentEvent.getSectionC();

	Container contents = frame.getContentPane();
	contents.setBackground(Color.PINK);
	contents.setLayout(new MigLayout("", "[grow][][]","[grow][][]"));

	initContent();
    }

    private void loginDialog() {
	JPanel myPanel = new JPanel();
	myPanel.setLayout(new MigLayout());
	JTextField username = new JTextField();
	JTextField password = new JTextField();

	myPanel.add(new JLabel("Username:"));
	myPanel.add(username, "wrap, width 100");

	myPanel.add(new JLabel("Password:"));
	myPanel.add(password, "width 100");

	int answer = JOptionPane.showConfirmDialog(null, myPanel, "Login", JOptionPane.OK_CANCEL_OPTION);
	if (answer == JOptionPane.OK_OPTION) {
	    User currentUser = UserList.getInstance().getUserFromString(username.getText());
	    if (!currentUser.getName().equals("false") && currentUser.getPassword().equals(password.getText()))
	    {
		user = currentUser;
	    }

	    else {
		JOptionPane.showMessageDialog(null, "Incorrect Username or Password.");
		loginDialog();
	    }
	}
	else {
	    quitSession();
	}
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
		quitSession();
	    }
	});

	panel.add(book);
	panel.add(exit);
	return panel;
    }

    private JPanel createEventSelectionPanel() {
	DefaultListModel<String> listModel = new DefaultListModel<>();
	JList<String> eventList = new JList<>(listModel);

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

	freeSeats = new JLabel("Free: " + currentEvent.getSection().getAmountOfFreeSeats());
	bookedSeats = new JLabel("Booked: " + (Math.abs(currentEvent.getSectionC().getSection().getAmountOfFreeSeats() - sectionC.getSection().getTotalSeats())));

	infoPanel.add(freeSeats, "wrap");
	infoPanel.add(bookedSeats);

	return infoPanel;
    }

    private void updateInfoPanel() {
	freeSeats.setText("Free: " + currentEvent.getSectionC().getSection().getAmountOfFreeSeats());
	bookedSeats.setText("Booked: " + (sectionC.getSection().getTotalSeats() - currentEvent.getSectionC().getSection().getAmountOfFreeSeats()));
    }

    private void addMenuBar() {
	JMenuBar menuBar = new JMenuBar();
	JMenu fileMenu, editMenu, helpMenu;

	JMenuItem exitItem = new JMenuItem("Quit");

	JMenuItem newEvent = new JMenuItem("New event");
	JMenuItem editEvent = new JMenuItem("Edit event");

	JMenuItem instructionsItem = new JMenuItem("Instructions");


	fileMenu = new JMenu("File");
	fileMenu.setMnemonic('F');

	fileMenu.addSeparator();
	exitItem.setAccelerator(KeyStroke.getKeyStroke('Q' ,ActionEvent.CTRL_MASK));
	exitItem.setToolTipText("Exit application");
	exitItem.addActionListener(new ActionListener() {
	    @Override public void actionPerformed(ActionEvent event) {
		quitSession();
	    }
	});

	editMenu = new JMenu("Edit");
	editMenu.setMnemonic('E');

	newEvent.setAccelerator(KeyStroke.getKeyStroke('N', ActionEvent.CTRL_MASK));
	newEvent.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		openNewEventDialog("Create new Event", null);
	    }
	});

	editEvent.setAccelerator(KeyStroke.getKeyStroke('E', ActionEvent.CTRL_MASK));
	editEvent.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		openEditEventDialog();
	    }
	});



	helpMenu = new JMenu("Help");
	helpMenu.setMnemonic('H');

	instructionsItem.setAccelerator(KeyStroke.getKeyStroke('I' ,ActionEvent.CTRL_MASK));
	instructionsItem.setToolTipText("Instructions");
	instructionsItem.addActionListener(new ActionListener() {
	    @Override public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(frame, "Sorry, no instructions avalible.");
	    }
	});


	fileMenu.add(exitItem);
	editMenu.add(newEvent);
	editMenu.add(editEvent);
	helpMenu.add(instructionsItem);

	menuBar.add(fileMenu);
	menuBar.add(editMenu);
	menuBar.add(Box.createHorizontalGlue());
	menuBar.add(helpMenu);
	frame.setJMenuBar(menuBar);
    }

    public void openNewEventDialog(String windowTitle, Event event) {
	JTextField title = new JTextField(5);
	JTextField date = new JTextField(5);
	JTextField time = new JTextField(5);
	JTextField sectionh = new JTextField(5);
	JTextField sectionw = new JTextField(5);

	if (event != null) {
	    title.setText(event.getTitle());
	    date.setText(event.getDate());
	    time.setText(event.getTime());
	    sectionh.setText(Integer.toString(event.getSection().getHeight()));
	    sectionw.setText(Integer.toString(event.getSection().getWidth()));
	}

	JPanel myPanel = new JPanel();
	myPanel.add(new JLabel("x:"));
	myPanel.add(title);
	myPanel.add(date);
	myPanel.add(time);

	myPanel.add(Box.createHorizontalStrut(horizontalStrut)); // a spacer
	myPanel.add(new JLabel("y:"));
	myPanel.add(sectionh);
	myPanel.add(sectionw);


	int result = JOptionPane.showConfirmDialog(null, myPanel, windowTitle, JOptionPane.OK_CANCEL_OPTION);
	if (result == JOptionPane.OK_OPTION) {
	    Event newEvent = new Event(new Section(Integer.parseInt(sectionh.getText()), Integer.parseInt(sectionw.getText())),
				       title.getText(), time.getText(), date.getText());
	    EventList.getINSTANCE().addToEventList(newEvent);
	}
	updateContentPane(sectionC);
    }

    public void openEditEventDialog() {
	//fixme make this function not to throw away old section with old bookings.

	String message = "Create new event";
	JPanel myPanel = new JPanel(new MigLayout());
	StringBuilder sb = new StringBuilder();

	for (Event event : EventList.getINSTANCE().getEventList()) {
	    sb.append(event.getTitle());
	    sb.append("'");
	}

	String sbToString = sb.toString();
	List<String> event = new ArrayList<>(Arrays.asList(sbToString.split("'")));

	JList<Object> eventList = new JList<>(event.toArray());

	myPanel.add(new JLabel("Select the event you would like to edit: "), "wrap");
	myPanel.add(eventList, "width 250");
	myPanel.add(Box.createHorizontalStrut(horizontalStrut)); // a spacer


	int result = JOptionPane.showConfirmDialog(null, myPanel, message, JOptionPane.OK_CANCEL_OPTION);
	if (result == JOptionPane.OK_OPTION) {
	    int marked = eventList.getSelectedIndex();

	    openNewEventDialog("Edit event " + findEventFromString(event.get(marked)).getTitle() + ".",
			       findEventFromString(event.get(marked)));
	    EventList.getINSTANCE().removeFromEventList(findEventFromString(event.get(marked)));
	    updateContentPane(currentEvent.getSectionC());
	}
    }

    private Event findEventFromString(String eventName) {
	for (Event event : EventList.getINSTANCE().getEventList()) {
	    if (event.getTitle().equals(eventName)) {
		return event;
	    }
	}
	return new Event(new Section(1,1), "Failed to find event", "", "");
    }

    private void quitSession() {
	WriteFile wf = new WriteFile(EventList.getINSTANCE().writeEventToFile(), Test.EVENT_TXT);
	UserList.getInstance().writeUserListToFile();
	System.exit(0);
    }
}