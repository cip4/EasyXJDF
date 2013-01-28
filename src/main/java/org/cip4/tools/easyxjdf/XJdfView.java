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
import org.cip4.tools.easyxjdf.event.util.ExceptionUtil;
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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * XJDF View class.
 * @author s.meissner
 * @date 28.01.2013
 */
public class XJdfView {

	private final List<SaveAsEventListener> saveAsListener;

	private final List<SaveAsEventListener> sendListener;

	protected Shell shell;
	private Text txtAmount;
	private Text txtRunList;
	private Text txtJobId;
	private Text txtJobName;
	private Text txtCatalogId;
	private Text txtMediaQuality;
	private Text txtCustomerId;

	/**
	 * Default constructor.
	 */
	public XJdfView() {

		// init instance variables
		saveAsListener = new ArrayList<SaveAsEventListener>();
		sendListener = new ArrayList<SaveAsEventListener>();
	}

	/**
	 * Getter for shell attribute.
	 * @return the shell
	 */
	public Shell getShell() {
		return shell;
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
		shell.setSize(514, 422);
		shell.setText("CIP4 EasyXJDF");

		Image imgXJdf = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/xjdf-logo.png"));
		Image imgInfo = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/info.png"));

		txtAmount = new Text(shell, SWT.BORDER);
		txtAmount.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		txtAmount.setBounds(122, 164, 115, 27);

		Label lblAmount = new Label(shell, SWT.NONE);
		lblAmount.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblAmount.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblAmount.setBounds(12, 167, 56, 21);
		lblAmount.setText("Amount");

		Button btnSend = new Button(shell, SWT.NONE);
		btnSend.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnSend.setBounds(411, 351, 87, 31);
		btnSend.setText("Send");

		Button btnSaveAs = new Button(shell, SWT.NONE);
		btnSaveAs.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
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
		btnSaveAs.setBounds(318, 351, 87, 31);

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setBounds(348, 10, 150, 87);
		lblNewLabel.setImage(imgXJdf);

		Label lblRunList = new Label(shell, SWT.NONE);
		lblRunList.setText("ContentData");
		lblRunList.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblRunList.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblRunList.setBounds(12, 285, 87, 21);

		txtRunList = new Text(shell, SWT.BORDER);
		txtRunList.setEditable(false);
		txtRunList.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		txtRunList.setBounds(122, 282, 349, 27);

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
						txtRunList.setText(file.getPath());
					else
						txtRunList.setText("");
				}
			}
		});
		btnContentData.setBounds(477, 280, 21, 31);
		btnContentData.setText("...");

		Label lblJobId = new Label(shell, SWT.NONE);
		lblJobId.setText("JobID");
		lblJobId.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblJobId.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblJobId.setBounds(12, 134, 39, 21);

		txtJobId = new Text(shell, SWT.BORDER);
		txtJobId.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		txtJobId.setBounds(122, 131, 115, 27);

		Label lblTitle = new Label(shell, SWT.NONE);
		lblTitle.setText("CIP4 EasyXJDF");
		lblTitle.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		lblTitle.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblTitle.setBounds(12, 67, 142, 30);

		txtJobName = new Text(shell, SWT.BORDER);
		txtJobName.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		txtJobName.setBounds(122, 247, 376, 27);

		Label lblJobName = new Label(shell, SWT.NONE);
		lblJobName.setText("JobName");
		lblJobName.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblJobName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblJobName.setBounds(12, 250, 66, 21);

		txtCatalogId = new Text(shell, SWT.BORDER);
		txtCatalogId.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		txtCatalogId.setBounds(122, 197, 115, 27);

		Label lblCatalogId = new Label(shell, SWT.NONE);
		lblCatalogId.setText("CatalogID");
		lblCatalogId.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblCatalogId.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblCatalogId.setBounds(10, 200, 68, 21);

		Label lblMediaQuality = new Label(shell, SWT.NONE);
		lblMediaQuality.setText("MediaQuality");
		lblMediaQuality.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblMediaQuality.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblMediaQuality.setBounds(274, 200, 93, 21);

		txtMediaQuality = new Text(shell, SWT.BORDER);
		txtMediaQuality.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		txtMediaQuality.setBounds(383, 197, 115, 27);

		txtCustomerId = new Text(shell, SWT.BORDER);
		txtCustomerId.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		txtCustomerId.setBounds(383, 164, 115, 27);

		Label lblCustomerId = new Label(shell, SWT.NONE);
		lblCustomerId.setText("CustomerID");
		lblCustomerId.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblCustomerId.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblCustomerId.setBounds(273, 167, 88, 21);

		Label lblInfo = new Label(shell, SWT.NONE);
		lblInfo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblInfo.setBounds(10, 361, 24, 24);
		lblInfo.setImage(imgInfo);
		shell.setTabList(new Control[] { txtJobId, txtAmount, txtCatalogId, txtCustomerId, txtMediaQuality, txtJobName, btnContentData, btnSaveAs, btnSend, txtRunList });

	}

	/**
	 * Notify a list of XJdfListners.
	 * @param xJdfListeners
	 */
	private void processSaveAs() {

		// save dialog
		String[] filterNames = { "XJDF Package (*.xjdf.zip) (recommended)", "XJDF Document (*.xjdf)" };
		String[] filterExts = { "*.xjdf.zip", "*.xjdf" };

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

			try {

				model.setJobId(txtJobId.getText());
				model.setAmount(Integer.parseInt(txtAmount.getText()));
				model.setRunList(txtRunList.getText());
				model.setCatalogId(txtCatalogId.getText());
				model.setCustomerId(txtCustomerId.getText());
				model.setMediaQuality(txtMediaQuality.getText());
				model.setJobName(txtJobName.getText());

				// notify all listeners
				for (SaveAsEventListener l : saveAsListener) {
					l.notify(new SaveAsEvent(model, path));
				}

			} catch (Exception ex) {

				// process exception
				ExceptionUtil.processException(shell, ex);
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
