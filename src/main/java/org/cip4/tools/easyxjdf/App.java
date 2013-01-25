package org.cip4.tools.easyxjdf;

import org.cip4.tools.easyxjdf.gui.MainForm;

/**
 * Hello world!
 * 
 */
public class App {

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainForm window = new MainForm();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
