/**
 * Users datatype.
 */
public class User
{
    private String name;
    private Authorization authorisation;
    private String password;

    public User(final String name, final String password, final Authorization authorisation) {
	this.name = name;
	this.password = password;
	this.authorisation = authorisation;
    }

    public String getName() {
	return name;
    }

    public Authorization getAuthorisation() {
	return authorisation;
    }

    public String getPassword() {
	return password;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public void setAuthorisation(final Authorization authorisation) {
	this.authorisation = authorisation;
    }

    public void setPassword(final String password) {
	this.password = password;
    }
}
