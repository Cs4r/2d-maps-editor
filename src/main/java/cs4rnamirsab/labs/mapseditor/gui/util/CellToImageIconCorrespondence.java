package cs4rnamirsab.labs.mapseditor.gui.util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cs4rnamirsab.labs.mapseditor.representation.Cell;

/**
 * Utility class which holds correspondence between {@link Cell}s and
 * {@link ImageIcon}s and vice-versa
 * 
 * @author cs4r
 *
 */
public final class CellToImageIconCorrespondence {

	private static final Logger LOGGER = LoggerFactory.getLogger(CellToImageIconCorrespondence.class);
	private final Map<Cell, ImageIcon> blockToImage;
	private final Map<String, Cell> imageToBlock;

	/**
	 * Constructs an instance of {@link CellToImageIconCorrespondence}
	 * 
	 * @param tilesDir
	 *            path where tiles are
	 */
	public CellToImageIconCorrespondence(final Path tilesDir) {
		blockToImage = new HashMap<>();
		imageToBlock = new HashMap<>();
		File dir = tilesDir.toFile();
		for (String tileName : dir.list()) {
			String tilePathAsString = Paths.get(tilesDir.toString(), tileName).toString();
			ImageIcon img = new ImageIcon(tilePathAsString);
			Cell cell = Cell.fromChar(tileName.charAt(0));
			LOGGER.info("Loaded cell {} ", cell);
			blockToImage.put(cell, img);
			imageToBlock.put(img.toString(), cell);
		}
	}

	/**
	 * Returns the {@link ImageIcon} which is equivalent to given {@link Cell}
	 * 
	 * @param cell
	 *            the {@link Cell}
	 * @return {@link ImageIcon} equivalent to cell
	 */
	public ImageIcon getImageIcon(final Cell cell) {
		return blockToImage.get(cell);
	}

	/**
	 * Returns the {@link Cell} which is equivalent to given {@link ImageIcon}
	 * 
	 * @param imageIcon
	 *            the {@link ImageIcon}
	 * @return {@link Cell} equivalent to imageIcon
	 */
	public Cell getCell(final ImageIcon imageIcon) {
		return imageToBlock.get(imageIcon.toString());
	}

}
