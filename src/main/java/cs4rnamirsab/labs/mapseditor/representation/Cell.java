package cs4rnamirsab.labs.mapseditor.representation;

import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents basic pieces of a {@link Map2D}
 * 
 * @author cs4r
 *
 */
public enum Cell {
	//@formatter:off
	CELL_1('1'),
	CELL_2('2'),
	CELL_3('3'),
	CELL_4('4'),
	CELL_5('5'),
	CELL_6('6'),
	CELL_7('7'),
	CELL_8('8'),
	CELL_A('A'),
	CELL_B('B'),
	CELL_I('I'),
	CELL_D('D'),
	CELL_C('C'),
	DOOR('P'),
	EMPTY('V'),
	SMALL_DOT('o'),
	BIG_DOT('0');
	//@formatter:on

	public static final Logger LOGGER = LoggerFactory.getLogger(Cell.class);
	private final char charValue;

	private Cell(final char c) {
		charValue = c;
	}

	/**
	 * Transforms a character into a {@link Cell}
	 * 
	 * @param charValue
	 *            character to be transformed
	 * @throws IllegalArgumentException
	 *             if <strong>charValue</strong> cannot be casted into a
	 *             {@link Cell}
	 * @return {@link Cell}
	 */
	public static Cell fromChar(final char charValue) {
		Optional<Cell> cell = Arrays.stream(values()).filter(c -> c.getCharValue() == charValue).findFirst();

		if (cell.isPresent()) {
			return cell.get();
		}

		LOGGER.error("Character '{}' whose Iteger representation is {} cannot be transformed into a {}", charValue,
				Character.getNumericValue(charValue), Cell.class.getName());

		throw new IllegalArgumentException("Character " + charValue + " cannot be casted to " + Cell.class.getName());
	}

	/**
	 * @return {@link Character} representation of this {@link Cell}
	 */
	public char getCharValue() {
		return charValue;
	}
}
