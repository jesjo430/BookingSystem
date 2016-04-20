import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads files.
 */

public class ReadFile
{
    public String readFiles(final String fileName) {
	System.out.println("READ: " + fileName);
	File file = new File(fileName);
	try {
	    FileReader fileReader = new FileReader(file);

	    StringBuilder stringBuffer = new StringBuilder();
	    int numCharsRead;
	    int numb = 2^10; // 1024 made invisible...
	    char[] charArray = new char[numb];
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
	}
	return "Failed reading.";
    }
}
