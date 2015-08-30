package cs4rnamirsab.labs.mapseditor.gui.panel;

import java.awt.GridLayout;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;

/**
 * Represents a panel with tiles
 * 
 * @author cs4r
 * @author namirsab
 *
 */
@SuppressWarnings("serial")
public class TilesPanel extends JPanel {

	private static final Logger LOGGER = LoggerFactory.getLogger(TilesPanel.class);
	private final JToggleButton[] tiles;
	private final EventBus eventBus;
	private final ButtonGroup buttonGroup;
	private ImageIcon selectedBlock;

	/**
	 * Constructs a {@link TilesPanel}
	 * 
	 * @param eventBus
	 *            event bus to publish updates about relevant info
	 * 
	 * @param tilesPath
	 *            path where tiles are
	 */
	public TilesPanel(EventBus bus, Path tilesPath) {
		setLayout(new GridLayout(6, 4, 1, 1));
		eventBus = bus;
		File tilesDir = tilesPath.toFile();
		buttonGroup = new ButtonGroup();
		String[] tileName = tilesDir.list();
		tiles = IntStream.range(0, tileName.length).mapToObj(value -> {
			JToggleButton jToggleButton = new JToggleButton();
			jToggleButton.setVisible(true);
			String imageIconPath = Paths.get(tilesDir.toString(), tileName[value]).toString();
			jToggleButton.setIcon(new ImageIcon(imageIconPath));
			jToggleButton.addActionListener(event -> selectTile());
			buttonGroup.add(jToggleButton);
			add(jToggleButton);
			return jToggleButton;
		}).toArray(JToggleButton[]::new);
	}

	private void selectTile() {
		Arrays.stream(tiles).filter(JToggleButton::isSelected).findAny()
				.ifPresent(e -> selectedBlock = (ImageIcon) e.getIcon());
		LOGGER.info("New tile selected");
		eventBus.post(Optional.ofNullable(selectedBlock));
	}
}
