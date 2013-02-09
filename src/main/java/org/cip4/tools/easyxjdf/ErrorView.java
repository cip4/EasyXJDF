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

import org.apache.commons.lang.StringUtils;
import org.cip4.tools.easyxjdf.model.ErrorModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * Error View class.
 * @author s.meissner
 * @date 28.01.2013
 */
public class ErrorView extends Dialog {

	protected Object result;

	protected Shell shell;

	private Text txtStackTrace;

	private Text txtMessage;

	private final ErrorModel errorModel;

	private final Shell parent;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ErrorView(Shell parent, ErrorModel errorModel) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		setText("Sorry, an error has occured...");

		this.errorModel = errorModel;
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
		shell.setSize(508, 447);
		shell.setText("Sorry, an error has been occured...");

		Image imgXJdf = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/xjdf-logo-small.png"));
		Image imgCIP4 = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/cip4-logo-small.png"));
		Image imgError = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/error.png"));

		shell.setImage(imgError);

		Label lblXJDF = new Label(shell, SWT.NONE);
		lblXJDF.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblXJDF.setBounds(440, 10, 52, 30);
		lblXJDF.setImage(imgXJdf);

		txtStackTrace = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtStackTrace.setEditable(false);
		txtStackTrace.setBounds(10, 202, 482, 154);

		if (!StringUtils.isEmpty(errorModel.getStackTrace()))
			txtStackTrace.setText(errorModel.getStackTrace());

		Label lblStackTrace = new Label(shell, SWT.NONE);
		lblStackTrace.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblStackTrace.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblStackTrace.setBounds(10, 175, 120, 21);
		lblStackTrace.setText("Stack Trace:");

		Label lblMessage = new Label(shell, SWT.NONE);
		lblMessage.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblMessage.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblMessage.setText("Message:");
		lblMessage.setBounds(10, 74, 105, 21);

		txtMessage = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtMessage.setEditable(false);
		txtMessage.setBounds(10, 101, 482, 68);

		if (!StringUtils.isEmpty(errorModel.getMessage()))
			txtMessage.setText(errorModel.getMessage());

		Button btnClose = new Button(shell, SWT.NONE);
		btnClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
			}
		});
		btnClose.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnClose.setBounds(411, 378, 81, 31);
		btnClose.setText("Close");

		Label lblErrorImg = new Label(shell, SWT.NONE);
		lblErrorImg.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblErrorImg.setBounds(41, 27, 32, 32);
		lblErrorImg.setImage(imgError);

		Label lblSorryAnError = new Label(shell, SWT.NONE);
		lblSorryAnError.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblSorryAnError.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD | SWT.ITALIC));
		lblSorryAnError.setBounds(89, 30, 386, 25);
		lblSorryAnError.setText("Sorry, an error has been occured...");

		Label lblCIP4 = new Label(shell, SWT.NONE);
		lblCIP4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblCIP4.setBounds(10, 377, 50, 32);
		lblCIP4.setImage(imgCIP4);
	}
}
