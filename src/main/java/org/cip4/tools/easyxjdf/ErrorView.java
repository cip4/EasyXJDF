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

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ErrorView(Shell parent, ErrorModel errorModel) {
		super(parent, SWT.DIALOG_TRIM);
		setText("Sorry, an error has occured...");

		this.errorModel = errorModel;
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
		shell.setSize(508, 447);
		shell.setText(getText());

		txtStackTrace = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtStackTrace.setEditable(false);
		txtStackTrace.setBounds(10, 183, 482, 173);

		if (!StringUtils.isEmpty(errorModel.getStackTrace()))
			txtStackTrace.setText(errorModel.getStackTrace());

		Label lblStackTrace = new Label(shell, SWT.NONE);
		lblStackTrace.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblStackTrace.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblStackTrace.setBounds(10, 156, 81, 21);
		lblStackTrace.setText("Stack Trace:");

		Label lblMessage = new Label(shell, SWT.NONE);
		lblMessage.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblMessage.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblMessage.setText("Message:");
		lblMessage.setBounds(10, 41, 64, 21);

		txtMessage = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtMessage.setEditable(false);
		txtMessage.setBounds(10, 68, 482, 68);

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

	}
}
