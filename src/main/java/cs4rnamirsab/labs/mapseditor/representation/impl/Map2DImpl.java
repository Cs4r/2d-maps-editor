package cs4rnamirsab.labs.mapseditor.representation.impl;

import java.util.Arrays;

import cs4rnamirsab.labs.mapseditor.representation.Cell;
import cs4rnamirsab.labs.mapseditor.representation.Map2D;

/**
 * Implementation of {@link Map2D} which holds cells as chars in a 2-dimensional
 * matrix
 * 
 * @author cs4r
 *
 */
public class Map2DImpl implements Map2D {
	private final char[][] map;

	/**
	 * Constructs an instance of {@link Map2D} by copying it from a given
	 * parameter
	 * 
	 * @param map
	 *            2-dimensional chars matrix defining the map to be copied
	 */
	public Map2DImpl(final char[][] map) {
		this.map = Arrays.stream(map).sequential().map(x -> x.clone()).toArray(char[][]::new);
	}

	/**
	 * Constructs an instance of {@link Map2DImpl}
	 */
	public Map2DImpl() {
		map = new char[HEIGHT][WIDTH];
		initializeMap();
	}

	private void initializeMap() {
		char empty = Cell.EMPTY.getCharValue();
		Arrays.stream(map).parallel().forEach(row -> Arrays.fill(row, empty));
	}

	@Override
	public void setCell(final int i, final int j, final Cell value) {
		map[i][j] = value.getCharValue();
	}

	@Override
	public Cell getCell(final int i, final int j) {
		return Cell.fromChar(map[i][j]);
	}

}
