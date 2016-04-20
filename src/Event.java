/**
 * Events
 */

public class Event
{
    private Section section;
    private SectionComponent sectionC;
    private String title;
    private String time;
    private String date;

    public Event(final Section section, final String title, final String time, final String date) {
	this.section = section;
	this.sectionC = new SectionComponent(section);
	this.title = title;
	this.time = time;
	this.date = date;
    }

    public Section getSection() {
	return section;
    }

    public SectionComponent getSectionC() {
	return sectionC;
    }

    public void setSectionC(final SectionComponent sectionC) {
	this.sectionC = sectionC;
    }

    public String getTitle() {
	return title;
    }

    public String getTime() {
	return time;
    }

    public String getDate() {
	return date;
    }

    /**
     * used when writing events to event.file.
     * @return Srnigversion of event.
     */
    @Override public String toString() {
	return "'Event{" +
	       ",sectionHeight='" + section.getHeight() + "\'" +
	       ",sectionWidth='" + section.getWidth() + "\'" +
	       ",title='" + title + '\'' +
	       ", time='" + time + '\'' +
	       ", date='" + date + '\'' +
	       ", bookedChairs='" + section.getChairStatusAndName() + "\'" +
	       "},";
    }
}
