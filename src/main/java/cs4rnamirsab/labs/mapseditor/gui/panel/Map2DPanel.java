package cs4rnamirsab.labs.mapseditor.gui.panel;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Path;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import cs4rnamirsab.labs.mapseditor.gui.util.CellToImageIconCorrespondence;
import cs4rnamirsab.labs.mapseditor.representation.Cell;
import cs4rnamirsab.labs.mapseditor.representation.Map2D;
import cs4rnamirsab.labs.mapseditor.representation.impl.Map2DImpl;

/**
 * Panel which contains the {@link Map2D}
 * 
 * @author cs4r
 * @author namirsab
 */
@SuppressWarnings("serial")
public class Map2DPanel extends JPanel {

	private static final Logger LOGGER = LoggerFactory.getLogger(Map2DPanel.class);
	private static final boolean DRAGGING = true;
	private JButton[][] tiles;
	private boolean mode = !DRAGGING;
	private final EventBus eventBus;
	private final CellToImageIconCorrespondence blockImageCorrespondence;
	private Map2D mapRepresentation;
	private Optional<ImageIcon> selectedTileCode;

	/**
	 * Constructs a {@link Map2DPanel}
	 * 
	 * @param eventBus
	 *            event bus to publish updates about relevant info
	 * 
	 * @param tilesPath
	 *            path where tiles are
	 */
	public Map2DPanel(final EventBus bus, final Path tilesPath) {
		mapRepresentation = new Map2DImpl();
		selectedTileCode = Optional.empty();
		eventBus = bus;
		eventBus.register(this);
		blockImageCorrespondence = new CellToImageIconCorrespondence(tilesPath);
		tiles = new JButton[Map2D.HEIGHT][Map2D.WIDTH];
		initComponents();
		initializeTiles();
	}

	private void initComponents() {
		setPreferredSize(new java.awt.Dimension(505, 590));
		setRequestFocusEnabled(false);
		setLayout(new GridLayout(31, 28, 1, 1));
	}

	private void initializeTiles() {
		for (int i = 0; i < Map2D.HEIGHT; ++i) {
			for (int j = 0; j < Map2D.WIDTH; ++j) {
				JButton jButton = new JButton();
				addListeners(i, j, jButton);
				add(jButton);
				tiles[i][j] = jButton;
			}
		}
	}

	private void addListeners(int i, int j, JButton jButton) {
		jButton.addActionListener(event -> updateTile(i, j));
		addMouseListeners(jButton, i, j);
	}

	private void addMouseListeners(final JButton jButton, final int i, final int j) {
		jButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				if (mode == DRAGGING) {
					updateTile(i, j);
				}
				eventBus.post("Row: " + i + " Column: " + j);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				mode = DRAGGING;
				LOGGER.info("Mouse pressed on tile ({}, {})", i, j);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				mode = !DRAGGING;
				LOGGER.info("Mouse released for tile ({}, {})", i, j);
			}
		});
	}

	private void updateTile(final int i, final int j) {
		selectedTileCode.ifPresent(image -> {
			Cell block = blockImageCorrespondence.getCell(image);
			mapRepresentation.setCell(i, j, block);
			tiles[i][j].setIcon(image);
			repaint();
			LOGGER.info("Tile at coordinates ({}, {}) was updated", i, j);
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawMap();
	}

	private void drawMap() {
		for (int i = 0; i < Map2D.HEIGHT; i++) {
			for (int j = 0; j < Map2D.WIDTH; j++) {
				Cell block = mapRepresentation.getCell(i, j);
				ImageIcon img = blockImageCorrespondence.getImageIcon(block);
				tiles[i][j].setIcon(img);
			}
		}
	}

	public void udpateMapRepresentation(Map2D mapRepresentation) {
		this.mapRepresentation = mapRepresentation;
		this.repaint();
	}

	public Map2D getMapRepresentation() {
		return mapRepresentation;
	}

	@Subscribe
	public void updateSelectedTile(final Optional<ImageIcon> selectedTile) {
		this.selectedTileCode = selectedTile;
	}
}
