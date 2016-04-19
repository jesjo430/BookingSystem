import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads files.
 */

public class ReadFile
{
    public String readFiles(final String fileName) {
	System.out.println("READ" + fileName);
	try {
	    File file = new File(fileName);
	    FileReader fileReader = new FileReader(file);
	    StringBuilder stringBuffer = new StringBuilder();
	    int numCharsRead;
	    char[] charArray = new char[1024];
	    while ((numCharsRead = fileReader.read(charArray)) > 0) {
		stringBuffer.append(charArray, 0, numCharsRead);
	    }
	    fileReader.close();
	    return stringBuffer.toString();
	}
	catch (IOException e) {
	    e.printStackTrace();
	}
	return "Failed reading.";
    }
}
