public class NormalUser implements User
{
    private String name;

    public NormalUser(final String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public String getAuthorisation() {
	return "Normal";
    }


}
