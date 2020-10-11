import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import acm.util.ErrorException;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

public class NameSurferDataBase implements NameSurferConstants {

	/* Constructor: NameSurferDataBase(filename) */
	/**
	 * Creates a new NameSurferDataBase and initializes it using the data in the
	 * specified file. The constructor throws an error exception if the requested
	 * file does not exist or if an error occurs as the file is being read.
	 */
	private HashMap<String, NameSurferEntry> list = new HashMap<>();

// 	put name and nameSurferEntry object in map	
	public NameSurferDataBase(String filename) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while (true) {

				String line = reader.readLine();
				if (line == null)
					break;

				list.put(line.substring(0, line.indexOf(' ')), new NameSurferEntry(line));

			}

			reader.close();

		} catch (Exception e) {
			throw new ErrorException(e);
		}

	}

	/* Method: findEntry(name) */
	/**
	 * Returns the NameSurferEntry associated with this name, if one exists. If the
	 * name does not appear in the database, this method returns null.
	 */
	public NameSurferEntry findEntry(String name) {
		for (String str : list.keySet())
			if (str.toUpperCase().equals(name.toUpperCase()))
				return list.get(str);

		return null;
	}

}
