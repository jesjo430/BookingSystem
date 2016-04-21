/**
 * Normal user. Other Authorisation.
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

    @Override public String getName() {
	return name;
    }

    @Override public String getAuthorisation() {
	return authorisation;
    }

    @Override public String getPassword() {
	return password;
    }

    @Override public void addToUserList() {
	UserList.getOurInstance().addToUserList(this);
    }
}
