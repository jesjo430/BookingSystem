import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Writes text to eventFile.
 */

public final class WriteFiles
{
    private final static Logger LOGGER = Logger.getLogger(WriteFiles.class.getName());

    private WriteFiles() {
    }

    public static void writeFiles(String text, String fileName) throws FileNotFoundException {
	System.out.println("WRITE");
	try (PrintWriter out = new PrintWriter(fileName)){
	    out.println(text);
	    out.close();
	    System.out.println(text);
	}
	catch (IOException ex) {
	    LOGGER.log(Level.SEVERE, "Failed to write to file.");
	    throw ex;
	}
    }
}