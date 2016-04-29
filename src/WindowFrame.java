import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is the window of the program. It contains components.
 */
public class WindowFrame extends JFrame
{
    private final static Logger LOGGER = Logger.getLogger(WindowFrame.class.getName());

    private static final int EVENT_TITLE_FONT_SIZE = 25;
    private static final String WINDOW_TITLE = "Booking client";
    private static final Color FRAME_COLOR = Color.decode("#7E0D05");
    private static final Color PANEL_COLOR = Color.LIGHT_GRAY;
    private static final Color TEXT_COLOR = Color.BLACK;

    private final JFrame frame;
    private Event currentEvent;

    private SectionComponent sectionC;
    private JLabel freeSeats;
    private JLabel bookedSeats;

    /**
     * The current active user.
     */
    public User user = null;

    public WindowFrame() {
	frame = new JFrame(WINDOW_TITLE);
	Container contents = frame.getContentPane();
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	frame.setLayout(new BorderLayout());

	currentEvent = EventList.getINSTANCE().getEventList().get(0);
	sectionC  = currentEvent.getSectionC();

	contents.setBackground(FRAME_COLOR);
	contents.setLayout(new MigLayout("", "[grow][][][]","[grow][]"));

	loginDialog();
	initContent();

	assert currentEvent != null : "No currentEvent assigned! Pointer null.";
	assert sectionC != null : "No sectionC assigned! Pointer null.";
	assert user != null : " No user was assigned!";
    }

    /**
     * Initializes all components that belongs to the contentpane.
     * Sets layout, titles and sets visibility.
     */
    private void initContent() {
	Container contents = frame.getContentPane();

	JLabel eventTitle = new JLabel(currentEvent.getTitle());
	JLabel userInfoLabel = new JLabel("Loged in as: " + user.getName());

	eventTitle.setForeground(TEXT_COLOR);
	Font titleFont = new Font("Times New Roman", Font.BOLD, EVENT_TITLE_FONT_SIZE);
	eventTitle.setFont(titleFont);
	addMenuBar();

	addImageToPane();
	contents.add(eventTitle, "gap 100px, width 30, height 40, wrap");
	contents.add(createSectionGrid(sectionC), "split 2, gap 20px");
	contents.add(createEventHandlingPanel(), "top, gap 20px, span, wrap");
	contents.add(createInfoPanel(), "split 2, gap 25px");
	contents.add(createButtons(), "gap 690px, wrap");
	contents.add(userInfoLabel);


	frame.pack();
	frame.setVisible(true);
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
   	    User currentUser = UserList.getOurInstance().getUserFromString(username.getText());
   	    if (!currentUser.getName().equals("false") && currentUser.getPassword().equals(password.getText())) {
		user = currentUser;
	    }

   	    else {
   		JOptionPane.showMessageDialog(null, "Incorrect Username or Password.");
   		loginDialog();
		LOGGER.log(Level.WARNING, "Failed to login.");
	    }
	}
   	else {
   	    System.exit(0); //so no-one can get in without login.
	}
    }

    /**
     * Generates a JPanel containing the JButtons.
     * @return JPanel with buttons and actionlisteners.
     */
    private JPanel createButtons() {
	JPanel panel = new JPanel();
	panel.setBackground(FRAME_COLOR);
	JButton book = new JButton("Book");
	book.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		if (!SeatComponent.getMarkedSeats().isEmpty()) { //If any seat has been marked.
		    for (SeatComponent seatC : SeatComponent.getMarkedSeats()) {
			seatC.getSeat().book(user.getName());

			String chairInfo = createToolTipString(seatC);
			seatC.setToolTipText(chairInfo);

			updateInfoPanel();
			seatC.repaint();
		    }
		    SeatComponent.getMarkedSeats().removeAll(SeatComponent.getMarkedSeats());
		    openDefaultMessageBox("Your seat(s) has been booked!");
		}
		else { //if no seat was marked befor clicking book.
		    openDefaultMessageBox("Please select a seat before booking.");
		}
	    }
	});

	JButton exit = new JButton("Exit");
	exit.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		try {
		    quitSession();
		} catch (FileNotFoundException e1) {
		    e1.printStackTrace();
		    LOGGER.log(Level.SEVERE, "Was not able to exit session.");
		}
	    }
	});
	panel.add(book);
	panel.add(exit);
	return panel;
    }

    /**
     * Generates a JPanel containing a selectable list with all Events.
     * @return JPanel with listeners and current Events.
     */
    private JPanel createEventHandlingPanel() {
	DefaultListModel<String> listModel = new DefaultListModel<>();
	JList<String> eventList = new JList<>(listModel);
	eventList.setBackground(PANEL_COLOR);

	for (Event e : EventList.getINSTANCE().getEventList()) {
	    listModel.addElement(e.getTitle());
	}

	eventList.addListSelectionListener(new ListSelectionListener() {
	    @Override public void valueChanged(ListSelectionEvent e)
	    {
	        if(!e.getValueIsAdjusting()) {
		    List<String> selectedValuesList = eventList.getSelectedValuesList();

		    Event newEvent = getEventFromString(selectedValuesList.get(0)); //the first and only element.
		    SectionComponent newSectionComponent = newEvent.getSectionC();
		    currentEvent.getSection().unmarkAllSeats();
		    currentEvent = newEvent;
		    updateContentPane(newSectionComponent);
	        }
	    }
	});

	eventList.setSelectionBackground(Color.GRAY);
	JPanel infoPanel = new JPanel(new MigLayout(""));
	infoPanel.setBackground(FRAME_COLOR);

	JLabel title = new JLabel("Events: ");
	title.setForeground(TEXT_COLOR);

	infoPanel.add(title, "wrap");
	infoPanel.add(eventList, "wrap");
	infoPanel.add(createEventInfoPanel(), "width 300, gaptop 20");

	return infoPanel;
    }

    /**
     * updates the contentpane by first removing all its content and the reproduces it with initContent().
     * @param sectionComponent The new comopnent to replace sectionC.
     */
    private void updateContentPane(SectionComponent sectionComponent) {
	frame.getContentPane().removeAll();
	sectionC = sectionComponent;
	initContent();
	frame.repaint();
    }

    /**
     * Looks up what eventuall event that corresponds to comp String.
     * @param comp value to check.
     * @return the event coresponding to comp. if no match: returns the first event in EventList.
     */
    private Event getEventFromString(String comp) {
	for (Event event : EventList.getINSTANCE().getEventList()) {
	    if (event.getTitle().equals(comp)) {
		return event;
	    }
	}
	LOGGER.log(Level.SEVERE, "No event matched the string, 1st element in eventlist was given.");
	return EventList.getINSTANCE().getEventList().get(0);
    }

    /**
     *  Generates a JPanel with the SeatComponents in a grid with sizes given from section.
     * @param sectionComp corresponnding sectionComponent.
     * @return JPanel containing SeatComponents.
     */
    private JPanel createSectionGrid(SectionComponent sectionComp) {
	JPanel sectionPanel = new JPanel();
	sectionPanel.setBackground(Color.BLACK);
	sectionPanel.setLayout(new MigLayout("gap 5")); //gap 5 --> gapsize in ox between added objects.
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

    /**
     * Creates a String with seat and row label from a SeatComponent.
     * @param seatC current SeatComponent to get seciton values from.
     * @return String with Seat and Row.
     */
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

    /**
     * Generates a JPanel with two labels: free and booked, with corresponding data.
     * @return JPanel with free and booked status.
     */
    private JPanel createInfoPanel() {
	JPanel infoPanel = new JPanel();
	infoPanel.setBackground(PANEL_COLOR);
	infoPanel.setLayout(new MigLayout());

	freeSeats = new JLabel("Free: " + currentEvent.getSection().getAmountOfFreeSeats());
	bookedSeats = new JLabel("Booked: " + (Math.abs(currentEvent.getSectionC().getSection().getAmountOfFreeSeats() - sectionC.getSection().getTotalSeats())));

	infoPanel.add(freeSeats, "wrap");
	infoPanel.add(bookedSeats);

	return infoPanel;
    }

    private JPanel createEventInfoPanel() {
	JPanel eventPanel = new JPanel();
	eventPanel.setBackground(PANEL_COLOR);
	eventPanel.setLayout(new MigLayout());

	JLabel title = new JLabel("Event Info");

	JTextArea infoArea = new JTextArea();
	infoArea.setBackground(PANEL_COLOR);

	infoArea.setText("Title: " + currentEvent.getTitle() + " \n" +
			 "Starttime: " + currentEvent.getTime() + "\n" +
			"Date: " + currentEvent.getDate());

	JLabel info = new JLabel("Summary:");
	JTextArea summary = new JTextArea("No summary avalible. \n yet... but the design is nice."); // to fill with info about the event, when I have time...
	summary.setBackground(PANEL_COLOR);

	eventPanel.add(title, "wrap");
	eventPanel.add(infoArea, "wrap");
	eventPanel.add(info, "wrap");
	eventPanel.add(summary, "height 280");

	return eventPanel;
    }

    /**
     * updates the infoPanel's values.
     */
    private void updateInfoPanel() {
	freeSeats.setText("Free: " + currentEvent.getSectionC().getSection().getAmountOfFreeSeats());
	bookedSeats.setText("Booked: " + (sectionC.getSection().getTotalSeats() - currentEvent.getSectionC().getSection().getAmountOfFreeSeats()));
    }

    /**
     * Adds a manubar to the menubararea in the frame.
     */
    public void addMenuBar() {
	JMenuBar menuBar = new JMenuBar();
	JMenu fileMenu, helpMenu;

	assert menuBar != null: "No menuBar was created, pointer null";

	JMenuItem exitItem = new JMenuItem("Quit");
	JMenuItem newEvent = new JMenuItem("New event");
	JMenuItem editEvent = new JMenuItem("Edit event");
	JMenuItem clearSection = new JMenuItem("Clear bookings");
	JMenuItem newUser = new JMenuItem("New user");
	JMenuItem editUser = new JMenuItem("Edit user");
	JMenuItem removeUser = new JMenuItem("Remove user");
	JMenuItem instructionsItem = new JMenuItem("Instructions");

	fileMenu = new JMenu("File");
	fileMenu.setMnemonic('F');

	fileMenu.addSeparator();
	exitItem.setAccelerator(KeyStroke.getKeyStroke('Q' , InputEvent.CTRL_DOWN_MASK));
	exitItem.setToolTipText("Exit application");
	exitItem.addActionListener(new ActionListener() {
	    @Override public void actionPerformed(ActionEvent event) {
		try {
		    quitSession();
		} catch (FileNotFoundException e) {
		    LOGGER.log(Level.SEVERE, "Was not able to exit session.");
		    e.printStackTrace();
		}
	    }
	});

	menuBar.add(fileMenu);

	/**
	 * spcial added for admin users.
	 */
	if(user.getAuthorisation().equals(Authorization.ADMIN)) {
	    JMenu editMenu = new JMenu("Edit");
	    editMenu.setMnemonic('E');

	    newEvent.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));
	    newEvent.addActionListener(new ActionListener()
	    {
		@Override public void actionPerformed(final ActionEvent e) {
		    openNewEventDialog("Create new Event", null);
		}
	    });

	    editEvent.setAccelerator(KeyStroke.getKeyStroke('E', InputEvent.CTRL_DOWN_MASK));
	    editEvent.addActionListener(new ActionListener()
	    {
		@Override public void actionPerformed(final ActionEvent e) {
		    openEditEventDialog();
		}
	    });

	    JMenu sectionMenu = new JMenu("Section");
	    sectionMenu.setMnemonic('S');

	    clearSection.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_DOWN_MASK));
	    clearSection.addActionListener(new ActionListener()
	    {
		@Override public void actionPerformed(final ActionEvent e) {
		    if (openYesNoMessageBox("Clear all booked seats",
					    "Are you sure you want to remove all booked seats on '" + currentEvent.getTitle() +
					    "'?")) {
			sectionC.getSection().unBookAllSeats();
			frame.getContentPane().removeAll();
			initContent();
			openDefaultMessageBox(currentEvent.getTitle() + " has been cleared from all bookings.");
		    }
		}
	    });

	    JMenu userMenu = new JMenu("User");
	    editMenu.setMnemonic('U');

	    newUser.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));
	    newUser.addActionListener(new ActionListener()
	    {
		@Override public void actionPerformed(final ActionEvent e) {
		    openNewUserDialog(null, "Create new user");
	   		}
	    });

	    editUser.setAccelerator(KeyStroke.getKeyStroke('E', InputEvent.CTRL_DOWN_MASK));
	    editUser.addActionListener(new ActionListener()
	    {
		@Override public void actionPerformed(final ActionEvent e) {
		    openEditUserDialog();
		}
	    });

	    removeUser.setAccelerator(KeyStroke.getKeyStroke('R', InputEvent.CTRL_DOWN_MASK));
	    removeUser.addActionListener(new ActionListener()
	    {
		@Override public void actionPerformed(final ActionEvent e) {
		    openRemoveUserDialog();
		}
	    });


	    editMenu.add(editEvent);
	    sectionMenu.add(clearSection);
	    editMenu.add(newEvent);

	    menuBar.add(editMenu);
	    menuBar.add(sectionMenu);

	    userMenu.add(newUser);
	    userMenu.add(editUser);
	    userMenu.add(removeUser);
	    menuBar.add(userMenu);
	}

	helpMenu = new JMenu("Help");
	helpMenu.setMnemonic('H');

	instructionsItem.setAccelerator(KeyStroke.getKeyStroke('I' , InputEvent.CTRL_DOWN_MASK));
	instructionsItem.setToolTipText("Instructions");
	instructionsItem.addActionListener(new ActionListener() {
	    @Override public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(frame, "Sorry, no instructions avalible.");
	    }
	});


	fileMenu.add(exitItem);

	helpMenu.add(instructionsItem);

	menuBar.add(Box.createHorizontalGlue());
	menuBar.add(helpMenu);
	frame.setJMenuBar(menuBar);
    }

    /**
     * Creates and opens a Dialogbox with inputs for a new event, its parameters.
     * @param windowTitle title for the pop-up window.
     * @param event eventuall current event.
     */
    public void openNewEventDialog(String windowTitle, Event event) {
	JTextField title = new JTextField(5);
	JTextField date = new JTextField(5);
	JTextField time = new JTextField(5);
	JTextField sectionh = new JTextField(5);
	JTextField sectionw = new JTextField(5);

	if (event != null) { //if there is any current event.
	    title.setText(event.getTitle());
	    date.setText(event.getDate());
	    time.setText(event.getTime());
	    sectionh.setText(Integer.toString(event.getSection().getHeight()));
	    sectionw.setText(Integer.toString(event.getSection().getWidth()));
	}

	JPanel myPanel = new JPanel();
	myPanel.setLayout(new MigLayout());
	myPanel.add(new JLabel("Title:"));
	myPanel.add(title, "wrap");
	myPanel.add(new JLabel("Date:"));
	myPanel.add(date, "wrap");
	myPanel.add(new JLabel("Time:"));
	myPanel.add(time, "wrap");

	myPanel.add(new JLabel("SectionSize:"));
	myPanel.add(sectionh);
	myPanel.add(new JLabel("x"));
	myPanel.add(sectionw, "wrap");


	int result = JOptionPane.showConfirmDialog(null, myPanel, windowTitle, JOptionPane.OK_CANCEL_OPTION);
	if (result == JOptionPane.OK_OPTION) {
	    Event newEvent = new Event(new Section(Integer.parseInt(sectionh.getText()),
						   Integer.parseInt(sectionw.getText())),
				       title.getText(), time.getText(), date.getText());
	    EventList.getINSTANCE().addToEventList(newEvent);
	    if (event != null) { //if there is any current event. to save the old sections data (bookings).
		newEvent.setSectionC(event.getSectionC());
	    }
	    openDefaultMessageBox(currentEvent.getTitle() + " has been saved.");
	}
	else {
	    openDefaultMessageBox(currentEvent.getTitle() + " has NOT been saved.");
	    LOGGER.log(Level.SEVERE, "The new Event was not saved!");
	}
	updateContentPane(sectionC);
    }

    /**
     * Creates and opens a dialog that contains the list over current events.
     * these are selectanble and will on OK be sent to openNewEventDialog().
     */
    public void openEditEventDialog() {
	String windowTitle = "Edit event";
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

	int result = JOptionPane.showConfirmDialog(null, myPanel, windowTitle, JOptionPane.OK_CANCEL_OPTION);
	if (result == JOptionPane.OK_OPTION) {
	    int marked = eventList.getSelectedIndex();

	    openNewEventDialog("Edit event " + findEventFromString(event.get(marked)).getTitle() + ".",
			       findEventFromString(event.get(marked)));
	    EventList.getINSTANCE().removeFromEventList(findEventFromString(event.get(marked)));
	    updateContentPane(currentEvent.getSectionC());
	}
    }

    private void openRemoveUserDialog() {
	String windowTitle = "Edit event";
	JPanel myPanel = new JPanel(new MigLayout());
	StringBuilder sb = new StringBuilder();

	for (User user : UserList.getOurInstance().getUserList()) {
	    sb.append(user.getName());
	    sb.append("'");
	}

	String sbToString = sb.toString();
	Collection<String> users = new ArrayList<>(Arrays.asList(sbToString.split("'")));
	JList<Object> userList = new JList<>(users.toArray());

	myPanel.add(new JLabel("Select the user you want to edit: "), "wrap");
	myPanel.add(userList, "width 250");

	int result = JOptionPane.showConfirmDialog(null, myPanel, windowTitle, JOptionPane.OK_CANCEL_OPTION);
	if (result == JOptionPane.OK_OPTION) {
	    String userString = userList.getSelectedValue().toString();
	    User user = UserList.getOurInstance().getUserFromString(userString);
	    UserList.getOurInstance().removeUserFromList(user);
	    openDefaultMessageBox("The user " + user.getName() + " has been removed");
	}
    }

    private void openNewUserDialog(User user, String wt) {
	JTextField name = new JTextField(5);
	JTextField password = new JTextField(5);

	JList<Object> authorizationField = new JList<>(Authorization.values());

	authorizationField.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	authorizationField.setLayoutOrientation(JList.HORIZONTAL_WRAP);
	authorizationField.setVisibleRowCount(-1);

	JScrollPane listScroller = new JScrollPane(authorizationField);

	if (user != null) { //if user is given.
	    name.setText(user.getName());
	    password.setText(user.getPassword());
	}

	JPanel myPanel = new JPanel();
	myPanel.setLayout(new MigLayout());
	myPanel.add(new JLabel("Name:"));
	myPanel.add(name, "wrap");
	myPanel.add(new JLabel("Password:"));
	myPanel.add(password, "wrap");
	myPanel.add(new JLabel("Active users:"));
	myPanel.add(listScroller, "wrap");

	int result = JOptionPane.showConfirmDialog(null, myPanel, wt, JOptionPane.OK_CANCEL_OPTION);
	if (result == JOptionPane.OK_OPTION) {
	    Authorization selectedAuthorization = (Authorization) authorizationField.getSelectedValue();
	    if (user == null) {
		User newUser = new User(name.getText(), password.getText(), selectedAuthorization);
		UserList.getOurInstance().addToUserList(newUser);
		openDefaultMessageBox("The new user " + newUser.getName() + " was created.");
	    }
	    else {
		user.setName(name.getText());
		user.setPassword(password.getText());
		user.setAuthorisation(selectedAuthorization);
		UserList.getOurInstance().removeUserFromList(user);
		UserList.getOurInstance().addToUserList(user);

		openDefaultMessageBox("The user " + user.getName() + " was changed.");
	    }
	}
    }

    private void openEditUserDialog() {
	String windowTitle = "Edit users";
	JPanel myPanel = new JPanel(new MigLayout());
	StringBuilder sb = new StringBuilder();

	for (User user : UserList.getOurInstance().getUserList()) {
	    sb.append(user.getName());
	    sb.append("'");
	}

	String sbToString = sb.toString();
	Collection<String> users = new ArrayList<>(Arrays.asList(sbToString.split("'")));
	JList<Object> eventList = new JList<>(users.toArray());

	myPanel.add(new JLabel("Select the user you want to edit: "), "wrap");
	myPanel.add(eventList, "width 250");

	int result = JOptionPane.showConfirmDialog(null, myPanel, windowTitle, JOptionPane.OK_CANCEL_OPTION);
	if (result == JOptionPane.OK_OPTION) {
	    String userString = eventList.getSelectedValue().toString();
	    User user = UserList.getOurInstance().getUserFromString(userString);
	    openNewUserDialog(user, "Edit user " + user.getName());
	}
    }

    /**
     * Picks a event from its title that corresponsd to the given string.
     * @param eventName String to compare with titles in EventList.
     * @return An event that had the same title as the eventName. if none: returns a new Event with title: Failed to find event.
     */
    private Event findEventFromString(String eventName) throws ExceptionInInitializerError  {
	assert EventList.getINSTANCE().getEventList() != null : "EventList pointer null.";
	for (Event event : EventList.getINSTANCE().getEventList()) {
	    if (event.getTitle().equals(eventName)) {
		return event;
	    }
	}
	LOGGER.log(Level.SEVERE, "No event was found with the given string.");
	throw new ExceptionInInitializerError("No event was found from given string.");
    }

    /**
     * Exits program, saves the current eventList and userlist.
     */
    private void quitSession() throws FileNotFoundException {
	if(openYesNoMessageBox("Exit program", "Are you sure you want to exit the client?")) {

	    WriteFiles.writeFiles(EventList.getINSTANCE().writeEventToFile(), Main.EVENT_TXT);
	    WriteFiles.writeFiles(UserList.getOurInstance().writeUserListToFile(), Main.USER_TXT);
	    UserList.getOurInstance().writeUserListToFile();

	    System.exit(0);
	}
    }

    /**
     * Opens a dialogbox with a question.
     * @param boxTitle must be string.
     * @param question must be question.
     * @return the answer on the given question as boolean.
     */
    private boolean openYesNoMessageBox(String boxTitle, String question) {
	int answer = JOptionPane.showConfirmDialog(null, question, boxTitle, JOptionPane.YES_NO_OPTION);
	if (answer == JOptionPane.YES_OPTION) {
	    return true;
	}
	return false;
    }

    /**
     * adds coded image as header on the contentpane.
     */
    private void addImageToPane() {
	ImageIcon image = new ImageIcon("resources/images/rodakvarn.jpg");
	assert image != null: "Header image not loaded!";
	JLabel label = new JLabel("", image, SwingConstants.CENTER);
	JPanel panel = new JPanel(new BorderLayout());
	panel.add(label, BorderLayout.CENTER );

	frame.getContentPane().add(panel, "wrap 2");
    }

    /**
     * Opens a dialogbox with a OK button.
     * @param message can be any string.
     */
    private void openDefaultMessageBox(String message) {
	JOptionPane.showConfirmDialog(null, message, "Confirmation", JOptionPane.DEFAULT_OPTION);
    }
}