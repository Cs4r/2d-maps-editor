package cs4rnamirsab.labs.mapseditor.representation;

/**
 * Represents a 2-dimensional map
 * 
 * @author cs4r
 *
 */
public interface Map2D {

	/**
	 * Map height
	 */
	final static int HEIGHT = 31;
	/**
	 * Map width
	 */
	final static int WIDTH = 28;

	/**
	 * Sets the value of a cell defined by the pair (row, column)
	 * 
	 * @param row
	 *            the row
	 * @param col
	 *            the column
	 * @param value
	 *            the {@link Cell}
	 */
	void setCell(final int row, final int col, final Cell value);

	/**
	 * Returns the value which is set at cell defined by the pair (row, column)
	 * 
	 * @param row
	 *            the row
	 * @param col
	 *            the column
	 * @return {@link Cell} at cell (row, col)
	 */
	Cell getCell(final int row, final int col);
}