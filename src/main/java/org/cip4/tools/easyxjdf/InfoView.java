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
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
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

	private final Shell parent;

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
		this.parent = parent;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();

		// Move the dialog to the center of the top level shell.
		Rectangle shellBounds = parent.getBounds();
		Point dialogSize = shell.getSize();
		shell.setLocation(shellBounds.x + (shellBounds.width - dialogSize.x) / 2, shellBounds.y + (shellBounds.height - dialogSize.y) / 2);

		// open
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

		// init font
		Label label = new Label(shell, SWT.NONE);
		FontData[] fd = label.getFont().getFontData();
		fd[0].setHeight(12);
		Font font = new Font(shell.getDisplay(), fd[0]);

		fd[0].setHeight(10);
		fd[0].setStyle(SWT.ITALIC);
		Font fontSmall = new Font(shell.getDisplay(), fd[0]);

		fd[0].setHeight(10);
		fd[0].setStyle(SWT.ITALIC | SWT.BOLD);
		Font fontSmallBold = new Font(shell.getDisplay(), fd[0]);

		Image imgFALogo = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/fa-logo.png"));
		Image imgXJdfLogo = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/xjdf-logo-small.png"));
		Image imgCIP4Logo = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/cip4-logo.png"));
		Image imgInfo = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/info.png"));

		shell.setImage(imgInfo);

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
		btnClose.setFont(font);
		btnClose.setBounds(347, 274, 87, 31);
		btnClose.setText("Close");

		Label lblCIP4Logo = new Label(shell, SWT.NONE);
		lblCIP4Logo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblCIP4Logo.setBounds(10, 10, 111, 70);
		lblCIP4Logo.setImage(imgCIP4Logo);

		Label lblTitle = new Label(shell, SWT.NONE);
		lblTitle.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblTitle.setFont(SWTResourceManager.getFont("Segoe UI", 21, SWT.BOLD | SWT.ITALIC));
		lblTitle.setBounds(142, 40, 186, 38);
		lblTitle.setText("EasyXJDF");

		Label lblVersion = new Label(shell, SWT.NONE);
		lblVersion.setFont(fontSmallBold);
		lblVersion.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblVersion.setBounds(214, 78, 220, 20);
		lblVersion.setText("Version: " + infoModel.getEasyJdfVersion());

		Label lblXJdfLibTitle = new Label(shell, SWT.NONE);
		lblXJdfLibTitle.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblXJdfLibTitle.setFont(fontSmallBold);
		lblXJdfLibTitle.setBounds(214, 162, 220, 17);
		lblXJdfLibTitle.setText("CIP4 xJdfLib " + infoModel.getxJdfLibVersion());

		Label lblCipPrinttalkLibrary = new Label(shell, SWT.NONE);
		lblCipPrinttalkLibrary.setText("CIP4 xPrintTalkLib " + infoModel.getPtkLibVersion());
		lblCipPrinttalkLibrary.setFont(fontSmallBold);
		lblCipPrinttalkLibrary.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblCipPrinttalkLibrary.setBounds(214, 206, 220, 17);

		Label lblPtkLibBuildDate = new Label(shell, SWT.NONE);
		lblPtkLibBuildDate.setFont(fontSmall);
		lblPtkLibBuildDate.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblPtkLibBuildDate.setText("Release Date: " + infoModel.getPtkLibBuildDate());
		lblPtkLibBuildDate.setBounds(214, 222, 220, 15);

		Label lblXJdfLibBuildDate = new Label(shell, SWT.NONE);
		lblXJdfLibBuildDate.setFont(fontSmall);
		lblXJdfLibBuildDate.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblXJdfLibBuildDate.setText("Release Date: " + infoModel.getxJdfLibBuildDate());
		lblXJdfLibBuildDate.setBounds(214, 179, 220, 15);

		Label lblFlyeralarm = new Label(shell, SWT.NONE);
		lblFlyeralarm.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblFlyeralarm.setBounds(10, 274, 186, 30);
		lblFlyeralarm.setImage(imgFALogo);

		Label lblFlyeralarmGmbh = new Label(shell, SWT.NONE);
		lblFlyeralarmGmbh.setText("flyeralarm GmbH");
		lblFlyeralarmGmbh.setFont(fontSmallBold);
		lblFlyeralarmGmbh.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblFlyeralarmGmbh.setBounds(16, 186, 159, 17);

		Label lblAuthorStefanMeissner = new Label(shell, SWT.NONE);
		fd[0].setHeight(14);
		fd[0].setStyle(SWT.ITALIC | SWT.BOLD);
		lblAuthorStefanMeissner.setText("Stefan Meissner");
		lblAuthorStefanMeissner.setFont(new Font(shell.getDisplay(), fd[0]));
		lblAuthorStefanMeissner.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblAuthorStefanMeissner.setBounds(16, 159, 159, 21);

		Label lblEasyXJDFBuildDate = new Label(shell, SWT.NONE);
		lblEasyXJDFBuildDate.setText("Release Date: " + infoModel.getEasyJdfBuildDate());
		lblEasyXJDFBuildDate.setFont(fontSmall);
		lblEasyXJDFBuildDate.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblEasyXJDFBuildDate.setBounds(214, 100, 220, 15);

		Label lblAuthor = new Label(shell, SWT.NONE);
		lblAuthor.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblAuthor.setFont(fontSmall);
		lblAuthor.setBounds(16, 143, 55, 15);
		lblAuthor.setText("Author:");

		Label lblXJDF = new Label(shell, SWT.NONE);
		lblXJDF.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblXJDF.setBounds(382, 10, 52, 30);
		lblXJDF.setImage(imgXJdfLogo);

	}
}
