import java.io.IOException;
import java.io.PrintWriter;

/**
 * Writes text to eventFile.
 */

public class WriteFile
{
    public WriteFile(final String text, final String fileName) {
	System.out.println("WRITE");
	try {
	    PrintWriter out = new PrintWriter(fileName);
	    out.println(text);
	    out.close();
	    System.out.println(text);
	}
	catch (IOException ex) {
	    System.out.println("Failed" + ex);
	}
    }
}
