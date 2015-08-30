package cs4rnamirsab.labs.mapseditor.gui;

import java.nio.file.Paths;

import cs4rnamirsab.labs.mapseditor.gui.panel.MapsEditorPanel;

/**
 * Entry point of the map's editor
 * 
 * @author cs4r
 * @author namirsab
 *
 */
public final class MapsEditorMain {

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MapsEditorPanel(Paths.get("src/main/resources/editor/tiles/"));
			}
		});
	}
}
