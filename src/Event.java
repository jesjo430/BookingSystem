public class Event
{
    private Section section;
    private SectionComponent sectionC;
    private String name;
    private String time;
    private String date;

    public Event(final Section section, final SectionComponent sectionC, final String name, final String time, final String date) {
	this.section = section;
	this.sectionC = sectionC;
	this.name = name;
	this.time = time;
	this.date = date;
    }

    public Section getSection() {
	return section;
    }

    public SectionComponent getSectionC() {
	return sectionC;
    }

    public String getName() {
	return name;
    }

    public String getTime() {
	return time;
    }

    public String getDate() {
	return date;
    }

}
