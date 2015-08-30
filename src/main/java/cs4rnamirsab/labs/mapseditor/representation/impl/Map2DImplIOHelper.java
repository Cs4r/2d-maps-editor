package cs4rnamirsab.labs.mapseditor.representation.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cs4rnamirsab.labs.mapseditor.representation.Map2D;
import cs4rnamirsab.labs.mapseditor.representation.Map2DIOHelper;

/**
 * Implementation of {@link Map2DIOHelper} for {@link Map2DImpl}
 * 
 * @author cs4r
 *
 */
public final class Map2DImplIOHelper implements Map2DIOHelper {

	public static final Logger LOGGER = LoggerFactory.getLogger(Map2DImplIOHelper.class);

	@Override
	public Map2D loadMapRepresentationFromFile(final File mapFile) throws Error {
		char[][] map = null;
		try {
			map = Files.lines(mapFile.toPath()).sequential().map(line -> transfromIntoRow(line)).toArray(char[][]::new);
			LOGGER.info("Map loaded from file {}", mapFile.getAbsolutePath());
		} catch (IOException exception) {
			LOGGER.error("{} is an invalid path", mapFile.getAbsolutePath(), exception);
		}
		return new Map2DImpl(map);
	}

	private static char[] transfromIntoRow(final String line) {
		if (line.length() != Map2D.WIDTH) {
			LOGGER.error("line: \"[{}]\" does not match expected length", line);
			throw new Error("Map's width present in file is either bigger or smaller than expected");
		}
		return line.toCharArray();
	}

	@Override
	public void saveMapRepresentationToFile(final Map2D mapRepresentation, final Path absolutePath) {
		List<String> lines = new ArrayList<>(Map2D.HEIGHT);
		for (int row = 0; row < Map2D.HEIGHT; ++row) {
			StringBuilder line = new StringBuilder(Map2D.WIDTH);
			for (int col = 0; col < Map2D.WIDTH; ++col) {
				line.append(mapRepresentation.getCell(row, col).getCharValue());
			}
			lines.add(line.toString());
		}

		try {
			Files.write(absolutePath, lines);
			LOGGER.info("Map saved into file {}", absolutePath.toFile().getAbsolutePath());
		} catch (IOException exception) {
			LOGGER.error("Error writting the map into a file", exception);
		}
	}

}
