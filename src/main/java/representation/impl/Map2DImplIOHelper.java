package representation.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import representation.Map2D;
import representation.Map2DIOHelper;

/**
 * Implementation of {@link Map2DIOHelper} for {@link Map2DImpl}
 * 
 * @author cs4r
 *
 */
public final class Map2DImplIOHelper implements Map2DIOHelper {

	@Override
	public Map2D loadMapRepresentationFromFile(final File mapFile) throws Error {
		char[][] map = null;
		try {
			map = Files.lines(mapFile.toPath()).sequential().map(line -> transfromIntoRow(line)).toArray(char[][]::new);
		} catch (IOException e) {
			System.out.println("Path inválido");
		}
		return new Map2DImpl(map);
	}

	private static char[] transfromIntoRow(final String line) {
		if (line.length() != Map2D.WIDTH) {
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
		} catch (IOException e) {
			System.out.println("Error writting the map into a file");
		}
	}

}
