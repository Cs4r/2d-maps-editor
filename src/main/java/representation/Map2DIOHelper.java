package representation;

import java.io.File;
import java.nio.file.Path;

/**
 * Utility class which helps with Input/Output operations over {@link Map2D}
 * 
 * @author cs4r
 *
 */
public interface Map2DIOHelper {

	/**
	 * Loads a {@link Map2D} from a file
	 * 
	 * @param mapFile
	 *            the path to the file where the map is written
	 * @return {@link Map2D} contained into the file
	 * @throws Error
	 *             If {@link Map2D} cannot be read
	 */
	Map2D loadMapRepresentationFromFile(File mapFile) throws Error;

	/**
	 * Saves a {@link Map2D} into a file
	 * 
	 * @param map2D
	 *            the {@link Map2D}
	 * @param path
	 *            the path to the file where the map will be written
	 */
	void saveMapRepresentationToFile(Map2D map2D, Path path);

}