/**
 * Normal user implements User.
 */
public class NormalUser implements User
{
    private String name;
    private String authorisation;
    private String password;

    public NormalUser(final String name, final String password) {
	this.name = name;
	this.password = password;
	authorisation = "normal";
	addToUserList();
    }

    public String getName() {
	return name;
    }

    public String getAuthorisation() {
	return authorisation;
    }

    public String getPassword() {
	return password;
    }

    public void addToUserList() {
	UserList.getOurInstance().addToUserList(this);
    }
}
