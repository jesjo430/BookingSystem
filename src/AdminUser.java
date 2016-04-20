/**
 * admin user
 */
public class AdminUser implements User
{
    private String name;
    private String authorisation;
    private String password;

    public AdminUser(final String name, final String password) {
    	this.name = name;
    	this.password = password;
    	authorisation = "admin";
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
}
