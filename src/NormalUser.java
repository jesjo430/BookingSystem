public class NormalUser implements User
{
    private String name;
    private String authorisation = "Normal";

    public NormalUser(final String name, final String authorisation) {
	this.name = name;
	this.authorisation = authorisation;
    }

    public String getName() {
	return name;
    }

    public String getAuthorisation() {
	return authorisation;
    }


}
