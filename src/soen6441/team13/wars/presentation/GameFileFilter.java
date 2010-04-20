package soen6441.team13.wars.presentation;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * This class is used by the file dialog to filter out non game files 
 */
public class GameFileFilter extends FileFilter {

	/**
	 * This method determines which files are displayed in the file dialog
	 */
	@Override
	public boolean accept(File file) {
		String extension = getExtension(file);
		return extension == null ? false : extension.equals("game");
	}

	@Override
	public String getDescription() {
		return null;
	}

	/**
	 * Get the extension of a file.
	 */
	public static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

}
