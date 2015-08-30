package cs4rnamirsab.labs.mapseditor.gui.panel;

import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Path;

import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import cs4rnamirsab.labs.mapseditor.gui.util.GuiConstants;
import cs4rnamirsab.labs.mapseditor.representation.Map2D;
import cs4rnamirsab.labs.mapseditor.representation.impl.Map2DImpl;
import cs4rnamirsab.labs.mapseditor.representation.impl.Map2DImplIOHelper;

/**
 * 
 * Main panel of maps editor, it contains a {@link Map2DPanel} and a
 * {@link TilesPanel}
 * 
 * @author cs4r
 * @author namirsab
 */
@SuppressWarnings("serial")
public final class MapsEditorPanel extends JFrame {

	private static final Logger LOGGER = LoggerFactory.getLogger(MapsEditorPanel.class);
	private JLabel currentRowAndColumnLabel;
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenuBar mainMenu;
	private JMenuItem saveMenuItem;
	private JMenuItem cleanAllMenuItem;
	private JMenuItem loadMapMenuItem;
	private JMenuItem exitMenuItem;
	private TilesPanel selectorPanel;
	private Map2DPanel mapRepresentationPanel;

	private Map2DImplIOHelper map2dIOHelper;
	private final EventBus eventBus;

	/**
	 * Constructs an {@link MapsEditorPanel}
	 * 
	 * @param tilesPath
	 *            path to cell's tiles
	 */
	public MapsEditorPanel(Path tilesPath) {
		eventBus = new EventBus();
		map2dIOHelper = new Map2DImplIOHelper();
		eventBus.register(this);
		initComponents(tilesPath);
		setTitle(GuiConstants.APP_NAME);
		setVisible(true);
	}

	private void initComponents(Path tilesPath) {
		selectorPanel = new TilesPanel(eventBus, tilesPath);
		mapRepresentationPanel = new Map2DPanel(eventBus, tilesPath);
		currentRowAndColumnLabel = new JLabel();
		mainMenu = new JMenuBar();
		fileMenu = new JMenu();
		saveMenuItem = new JMenuItem();
		loadMapMenuItem = new JMenuItem();
		exitMenuItem = new JMenuItem();
		editMenu = new JMenu();
		cleanAllMenuItem = new JMenuItem();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		currentRowAndColumnLabel.setText("");

		fileMenu.setText(GuiConstants.FILE_MENU);
		editMenu.setText(GuiConstants.EDIT_MENU);

		saveMenuItem.setAccelerator(
				KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
		saveMenuItem.setText(GuiConstants.SAVE_MAP);
		saveMenuItem.addActionListener(event -> saveMap());

		loadMapMenuItem.setText(GuiConstants.LOAD_MAP);
		loadMapMenuItem.addActionListener(event -> loadMap());

		exitMenuItem.setText(GuiConstants.EXIT_EDITOR);
		exitMenuItem.addActionListener(event -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));

		fileMenu.add(loadMapMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(exitMenuItem);

		cleanAllMenuItem.setAccelerator(
				KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
		cleanAllMenuItem.setText(GuiConstants.CLEAN_MAP);
		cleanAllMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cleanMap();
			}
		});
		editMenu.add(cleanAllMenuItem);

		mainMenu.add(fileMenu);
		mainMenu.add(editMenu);

		setJMenuBar(mainMenu);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(GroupLayout.Alignment.TRAILING,
										layout.createSequentialGroup()
												.addComponent(selectorPanel, GroupLayout.PREFERRED_SIZE, 211,
														GroupLayout.PREFERRED_SIZE)
												.addGap(121, 121, 121)
												.addComponent(mapRepresentationPanel, GroupLayout.PREFERRED_SIZE, 579,
														GroupLayout.PREFERRED_SIZE)
												.addGap(142, 142, 142))
						.addGroup(GroupLayout.Alignment.TRAILING,
								layout.createSequentialGroup().addComponent(currentRowAndColumnLabel,
										GroupLayout.PREFERRED_SIZE, 348, GroupLayout.PREFERRED_SIZE)
								.addGap(539, 539, 539)))));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(mapRepresentationPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(selectorPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(26, 26, 26).addComponent(currentRowAndColumnLabel).addContainerGap(47, Short.MAX_VALUE)));

		pack();
	}

	private void cleanMap() {
		mapRepresentationPanel.udpateMapRepresentation(new Map2DImpl());
		LOGGER.info("Map cleaned");
	}

	private void saveMap() {
		JFileChooser dialog = new JFileChooser();
		int returnVal = dialog.showSaveDialog(fileMenu);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			Path absolutePath = dialog.getSelectedFile().toPath();
			map2dIOHelper.saveMapRepresentationToFile(mapRepresentationPanel.getMapRepresentation(), absolutePath);
			LOGGER.info("Map successfully saved");
		} else {
			LOGGER.info("Map not saved");
		}
	}

	private void loadMap() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.getName().toLowerCase().endsWith("txt") || file.isDirectory();
			}

			@Override
			public String getDescription() {
				return GuiConstants.ONLY_TXT_FILES;
			}
		});
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.showOpenDialog(fileMenu);

		File absolutePath = fileChooser.getSelectedFile();
		Map2D map2d = map2dIOHelper.loadMapRepresentationFromFile(absolutePath);
		mapRepresentationPanel.udpateMapRepresentation(map2d);
		LOGGER.info("Loaded new map");
	}

	@Subscribe
	public void updateCurrentRowAndColumn(final String newValue) {
		currentRowAndColumnLabel.setText(newValue);
	}

}
