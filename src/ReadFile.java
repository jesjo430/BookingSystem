import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Reads files.
 */

public class ReadFile
{
    private final static Logger LOGGER = Logger.getLogger(ReadFile.class.getName());

    /**
     * Reads data from file.
     * @param fileName string of file to read from.
     * @return content of file as string.
     * @throws IOException if no file found.
     */
    public String readFiles(final String fileName) throws IOException {
	File file = new File(fileName);
	try (FileReader fileReader = new FileReader(file)){
	    StringBuilder stringBuffer = new StringBuilder();
	    int numCharsRead;
	    int charSize = 2^10;
	    char[] charArray = new char[charSize];
	    numCharsRead = fileReader.read(charArray);
	    while (numCharsRead > 0) {
		stringBuffer.append(charArray, 0, numCharsRead);
		numCharsRead = fileReader.read(charArray);
	    }
	    fileReader.close();
	    return stringBuffer.toString();
	}
	catch (IOException e) {
	    e.printStackTrace();
	    LOGGER.log(Level.SEVERE, "Could not read file");
	}
	throw new IOException("Could not read file" + fileName +".");
    }
}
