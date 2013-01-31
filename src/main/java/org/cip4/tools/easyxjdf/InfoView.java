/**
 * All rights reserved by
 * 
 * flyeralarm GmbH
 * Alfred-Nobel-Stra�e 18
 * 97080 W�rzburg
 *
 * Email: info@flyeralarm.com
 * Website: http://www.flyeralarm.com
 */
package org.cip4.tools.easyxjdf;

import org.cip4.tools.easyxjdf.model.InfoModel;
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

	private final InfoModel infoModel;

	/**
	 * Create the dialog.
	 * @param parent The parent dialog.
	 */
	public InfoView(Shell parent, InfoModel infoModel) {

		// init dialog
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		setText("Info about EasyXJDF...");

		// init members
		this.infoModel = infoModel;
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
		shell.setSize(450, 343);
		shell.setText(getText());

		Image imgFALogo = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/fa-logo.png"));
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
		btnClose.setBounds(347, 274, 87, 31);
		btnClose.setText("Close");

		Label lblCIP4Logo = new Label(shell, SWT.NONE);
		lblCIP4Logo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblCIP4Logo.setBounds(10, 10, 111, 70);
		lblCIP4Logo.setImage(imgCIP4Logo);

		Label lblTitle = new Label(shell, SWT.NONE);
		lblTitle.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblTitle.setFont(SWTResourceManager.getFont("Segoe UI", 21, SWT.BOLD | SWT.ITALIC));
		lblTitle.setBounds(142, 40, 125, 38);
		lblTitle.setText("EasyXJDF");

		Label lblVersion = new Label(shell, SWT.NONE);
		lblVersion.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD | SWT.ITALIC));
		lblVersion.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblVersion.setBounds(214, 78, 220, 20);
		lblVersion.setText("Version: " + infoModel.getEasyJdfVersion());

		Label lblXJdfLibTitle = new Label(shell, SWT.NONE);
		lblXJdfLibTitle.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblXJdfLibTitle.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD | SWT.ITALIC));
		lblXJdfLibTitle.setBounds(214, 162, 220, 17);
		lblXJdfLibTitle.setText("CIP4 xJdfLib " + infoModel.getxJdfLibVersion());

		Label lblCipPrinttalkLibrary = new Label(shell, SWT.NONE);
		lblCipPrinttalkLibrary.setText("CIP4 xPrintTalkLib " + infoModel.getPtkLibVersion());
		lblCipPrinttalkLibrary.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD | SWT.ITALIC));
		lblCipPrinttalkLibrary.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblCipPrinttalkLibrary.setBounds(214, 206, 220, 17);

		Label lblPtkLibBuildDate = new Label(shell, SWT.NONE);
		lblPtkLibBuildDate.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblPtkLibBuildDate.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblPtkLibBuildDate.setText("Release Date: " + infoModel.getPtkLibBuildDate());
		lblPtkLibBuildDate.setBounds(214, 222, 220, 15);

		Label lblXJdfLibBuildDate = new Label(shell, SWT.NONE);
		lblXJdfLibBuildDate.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblXJdfLibBuildDate.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblXJdfLibBuildDate.setText("Release Date: " + infoModel.getxJdfLibBuildDate());
		lblXJdfLibBuildDate.setBounds(214, 179, 220, 15);

		Label lblFlyeralarm = new Label(shell, SWT.NONE);
		lblFlyeralarm.setBounds(10, 274, 167, 30);
		lblFlyeralarm.setImage(imgFALogo);

		Label lblFlyeralarmGmbh = new Label(shell, SWT.NONE);
		lblFlyeralarmGmbh.setText("flyeralarm GmbH");
		lblFlyeralarmGmbh.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD | SWT.ITALIC));
		lblFlyeralarmGmbh.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblFlyeralarmGmbh.setBounds(16, 186, 159, 17);

		Label lblAuthorStefanMeissner = new Label(shell, SWT.NONE);
		lblAuthorStefanMeissner.setText("Stefan Meissner");
		lblAuthorStefanMeissner.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD | SWT.ITALIC));
		lblAuthorStefanMeissner.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblAuthorStefanMeissner.setBounds(16, 159, 159, 21);

		Label lblEasyXJDFBuildDate = new Label(shell, SWT.NONE);
		lblEasyXJDFBuildDate.setText("Release Date: " + infoModel.getEasyJdfBuildDate());
		lblEasyXJDFBuildDate.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblEasyXJDFBuildDate.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblEasyXJDFBuildDate.setBounds(214, 100, 220, 15);

		Label lblAuthor = new Label(shell, SWT.NONE);
		lblAuthor.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblAuthor.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.ITALIC));
		lblAuthor.setBounds(16, 143, 55, 15);
		lblAuthor.setText("Author:");

	}
}
