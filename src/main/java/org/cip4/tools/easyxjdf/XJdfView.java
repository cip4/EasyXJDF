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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.cip4.tools.easyxjdf.event.SaveAsEvent;
import org.cip4.tools.easyxjdf.event.SaveAsEventListener;
import org.cip4.tools.easyxjdf.model.XJdfModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class XJdfView {

	private final List<SaveAsEventListener> saveAsListener;

	private final List<SaveAsEventListener> sendListener;

	protected Shell shell;
	private Text txtAmount;
	private Text txtContentData;
	private Text txtJobId;

	/**
	 * Default constructor.
	 */
	public XJdfView() {

		// init instance variables
		saveAsListener = new ArrayList<SaveAsEventListener>();
		sendListener = new ArrayList<SaveAsEventListener>();
	}

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.TITLE | SWT.CLOSE | SWT.BORDER | SWT.TRANSPARENT);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shell.setSize(451, 454);
		shell.setText("CIP4 EasyXJDF");

		txtAmount = new Text(shell, SWT.BORDER);
		txtAmount.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		txtAmount.setBounds(120, 199, 115, 27);

		Label lblAmount = new Label(shell, SWT.NONE);
		lblAmount.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblAmount.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblAmount.setBounds(10, 202, 56, 21);
		lblAmount.setText("Amount");

		Image imgXJdf = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/xjdf-logo.png"));

		Button btnSend = new Button(shell, SWT.NONE);
		btnSend.setBounds(349, 381, 75, 25);
		btnSend.setText("Send");

		Button btnSaveAs = new Button(shell, SWT.NONE);
		btnSaveAs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				processSaveAs();
			}
		});
		btnSaveAs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				processSaveAs();
			}
		});
		btnSaveAs.setText("Save as...");
		btnSaveAs.setBounds(268, 381, 75, 25);

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setBounds(274, 10, 150, 87);
		lblNewLabel.setImage(imgXJdf);

		Label lblContentData = new Label(shell, SWT.NONE);
		lblContentData.setText("ContentData");
		lblContentData.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblContentData.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblContentData.setBounds(10, 252, 87, 21);

		txtContentData = new Text(shell, SWT.BORDER);
		txtContentData.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		txtContentData.setBounds(120, 249, 277, 27);

		Button btnContentData = new Button(shell, SWT.NONE);
		btnContentData.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnContentData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog dialog = new FileDialog(shell, SWT.OPEN);
				dialog.setText("Append ContentData...");
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
		btnContentData.setBounds(403, 247, 21, 31);
		btnContentData.setText("...");

		Label lblJobId = new Label(shell, SWT.NONE);
		lblJobId.setText("JobID");
		lblJobId.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblJobId.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblJobId.setBounds(10, 169, 39, 21);

		txtJobId = new Text(shell, SWT.BORDER);
		txtJobId.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		txtJobId.setBounds(120, 166, 115, 27);

		Label lblTitle = new Label(shell, SWT.NONE);
		lblTitle.setText("CIP4 EasyXJDF");
		lblTitle.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		lblTitle.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblTitle.setBounds(10, 118, 142, 30);

	}

	/**
	 * Notify a list of XJdfListners.
	 * @param xJdfListeners
	 */
	private void processSaveAs() {

		// save dialog
		String[] filterNames = { "XJDF Document (*.xjdf)", "XJDF ZIP Archiv (*.zip)" };
		String[] filterExts = { "*.xjdf", "*.zip" };

		FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		dialog.setFilterExtensions(filterExts);
		dialog.setFilterNames(filterNames);
		dialog.setText("Save as XJDF Package...");
		String path = dialog.open();

		if (path != null) {

			if (new File(path).exists()) {

				MessageBox mb = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
				mb.setMessage(path + " already exists. Do you want to replace it?");

				if (mb.open() == SWT.NO) {
					return;
				}
			}

			// create model object
			XJdfModel model = new XJdfModel();
			model.setJobId(txtJobId.getText());
			model.setAmount(Integer.parseInt(txtAmount.getText()));
			model.setContentData(txtContentData.getText());

			// notify all listeners
			for (SaveAsEventListener l : saveAsListener) {
				l.notify(new SaveAsEvent(model, path));
			}
		}

	}

	/**
	 * Append listener for SaveAs Event.
	 * @param xJdfListener XJdfListener to append to.
	 */
	public void addSaveAsListener(SaveAsEventListener xJdfListener) {
		saveAsListener.add(xJdfListener);
	}

	/**
	 * Append listener for Send Event.
	 * @param xJdfListener XJdfListener to append to.
	 */
	public void addSendListener(SaveAsEventListener xJdfListener) {
		saveAsListener.add(xJdfListener);
	}
}
