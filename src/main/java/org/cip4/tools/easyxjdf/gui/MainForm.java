package org.cip4.tools.easyxjdf.gui;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class MainForm {

	protected Shell shlCipEasyxjdf;
	private Text txtJobId;
	private Text txtContentData;

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlCipEasyxjdf.open();
		shlCipEasyxjdf.layout();
		while (!shlCipEasyxjdf.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlCipEasyxjdf = new Shell();
		shlCipEasyxjdf.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shlCipEasyxjdf.setSize(451, 454);
		shlCipEasyxjdf.setText("CIP4 EasyXJDF");

		txtJobId = new Text(shlCipEasyxjdf, SWT.BORDER);
		txtJobId.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		txtJobId.setBounds(120, 103, 115, 27);

		Label lblJobId = new Label(shlCipEasyxjdf, SWT.NONE);
		lblJobId.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblJobId.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblJobId.setBounds(10, 106, 39, 21);
		lblJobId.setText("JobID");

		Image imgXJdf = new Image(shlCipEasyxjdf.getDisplay(), MainForm.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/xjdf-logo.png"));

		Button btnSend = new Button(shlCipEasyxjdf, SWT.NONE);
		btnSend.setBounds(349, 381, 75, 25);
		btnSend.setText("Send");

		Button btnSaveAs = new Button(shlCipEasyxjdf, SWT.NONE);
		btnSaveAs.setText("Save as...");
		btnSaveAs.setBounds(268, 381, 75, 25);

		Label lblNewLabel = new Label(shlCipEasyxjdf, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setBounds(274, 10, 150, 87);
		lblNewLabel.setImage(imgXJdf);

		Label lblContentData = new Label(shlCipEasyxjdf, SWT.NONE);
		lblContentData.setText("ContentData");
		lblContentData.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblContentData.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblContentData.setBounds(10, 141, 87, 21);

		txtContentData = new Text(shlCipEasyxjdf, SWT.BORDER);
		txtContentData.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		txtContentData.setBounds(120, 138, 277, 27);

		Button btnContentData = new Button(shlCipEasyxjdf, SWT.NONE);
		btnContentData.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnContentData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog dialog = new FileDialog(shlCipEasyxjdf, SWT.NULL);
				String path = dialog.open();
				if (path != null) {

					File file = new File(path);
					if (file.isFile())
						txtContentData.setText(file.getPath());
					else
						txtContentData.setText("");
				}
			}
		});
		btnContentData.setBounds(403, 136, 21, 31);
		btnContentData.setText("...");

	}
}
