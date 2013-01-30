/**
 * All rights reserved by
 * 
 * flyeralarm GmbH
 * Alfred-Nobel-Straße 18
 * 97080 Würzburg
 *
 * Email: info@flyeralarm.com
 * Website: http://www.flyeralarm.com
 */
package org.cip4.tools.easyxjdf;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * EasyXJDF Info View class.
 * @author s.meissner
 * @date 30.01.2013
 */
public class InfoView extends Dialog {

	protected Object result;
	protected Shell shell;

	/**
	 * Create the dialog.
	 * @param parent The parent dialog.
	 */
	public InfoView(Shell parent) {

		// init dialog
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		setText("Info about EasyXJDF...");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {

		shell = new Shell(getParent(), getStyle());
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shell.setSize(450, 300);
		shell.setText(getText());

		Image imgInfo = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/info-large.png"));
		Image imgXJdfLogo = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/xjdf-logo.png"));
		Image imgCIP4Logo = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/cip4-logo.png"));

		Button btnClose = new Button(shell, SWT.NONE);
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shell.close();
			}
		});
		btnClose.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				shell.close();
			}
		});
		btnClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnClose.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnClose.setBounds(343, 231, 80, 31);
		btnClose.setText("Close");

		Label lblCIP4Logo = new Label(shell, SWT.NONE);
		lblCIP4Logo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblCIP4Logo.setBounds(10, 10, 111, 70);
		lblCIP4Logo.setImage(imgCIP4Logo);

		Label lblXJdfLogo = new Label(shell, SWT.NONE);
		lblXJdfLogo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblXJdfLogo.setBounds(10, 212, 86, 50);
		lblXJdfLogo.setImage(imgXJdfLogo);

		Label lblTitle = new Label(shell, SWT.NONE);
		lblTitle.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblTitle.setFont(SWTResourceManager.getFont("Segoe UI", 21, SWT.BOLD | SWT.ITALIC));
		lblTitle.setBounds(142, 42, 125, 38);
		lblTitle.setText("EasyXJDF");

	}
}
