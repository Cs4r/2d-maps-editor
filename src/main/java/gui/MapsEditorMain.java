package gui;

import java.nio.file.Paths;

import gui.panel.MapsEditorPanel;

/**
 * Entry point mapsEditor project
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
