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

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.cip4.tools.easyxjdf.event.XJdfSaveAsEvent;
import org.cip4.tools.easyxjdf.event.XJdfSaveAsEventListener;
import org.cip4.tools.easyxjdf.event.XJdfSendEvent;
import org.cip4.tools.easyxjdf.event.XJdfSendEventListener;
import org.cip4.tools.easyxjdf.model.XJdfModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
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

	private final List<XJdfSaveAsEventListener> xJdfSaveAsListener;

	private final List<XJdfSendEventListener> xJdfSendListener;

	private String oldJobName = "";

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
		xJdfSaveAsListener = new ArrayList<XJdfSaveAsEventListener>();
		xJdfSendListener = new ArrayList<XJdfSendEventListener>();
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
	 * Show an info message in a MessageBox.
	 * @param message Message to show in a MessageBox.
	 */
	public void showInfo(String message) {

		// show message
		MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
		mb.setMessage(message);
		mb.open();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {

		shell = new Shell(SWT.TITLE | SWT.CLOSE | SWT.MIN | SWT.BORDER | SWT.TRANSPARENT);
		shell.setBackground(new Color(shell.getDisplay(), 238, 238, 238));
		shell.setSize(514, 422);
		shell.setText("CIP4 EasyXJDF");

		Image imgXJdf = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/xjdf-logo.png"));
		Image imgInfo = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/info.png"));
		Image imgTitleBg = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/title-bg.png"));
		Image imgCip4 = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/cip4-logo.png"));
		Image imgSettings = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/settings.png"));

		shell.setImage(imgXJdf);

		Label lblSettings = new Label(shell, SWT.NONE);
		lblSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				new SettingsController(shell).showView();
			}
		});
		lblSettings.setToolTipText("Show EasyXJDF Settings");
		lblSettings.setBounds(474, 97, 24, 24);
		lblSettings.setImage(imgSettings);

		txtAmount = new Text(shell, SWT.BORDER);
		txtAmount.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		txtAmount.setBounds(112, 164, 134, 27);

		Label lblAmount = new Label(shell, SWT.NONE);
		lblAmount.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblAmount.setBackground(new Color(shell.getDisplay(), 238, 238, 238));
		lblAmount.setBounds(12, 167, 56, 21);
		lblAmount.setText("Amount");

		Button btnSend = new Button(shell, SWT.NONE);
		btnSend.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				processSend();
			}
		});
		btnSend.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				processSend();
			}
		});
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
		btnSaveAs.setBounds(303, 351, 87, 31);

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setBounds(414, -3, 86, 70);
		lblNewLabel.setImage(imgXJdf);

		Label lblRunList = new Label(shell, SWT.NONE);
		lblRunList.setText("ContentData");
		lblRunList.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblRunList.setBackground(new Color(shell.getDisplay(), 238, 238, 238));
		lblRunList.setBounds(12, 260, 87, 21);

		txtRunList = new Text(shell, SWT.BORDER);
		txtRunList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.keyCode == 8 || e.keyCode == 127) {
					txtRunList.setText("");
					txtRunList.setToolTipText("");
					updateJobName();
				}
			}
		});
		txtRunList.setEditable(false);
		txtRunList.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		txtRunList.setBounds(112, 257, 361, 27);

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

					if (file.isFile()) {
						txtRunList.setText(file.getPath());
						txtRunList.setToolTipText(txtRunList.getText());
						updateJobName();

					} else {
						txtRunList.setText("");
					}
				}
			}
		});
		btnContentData.setBounds(479, 255, 21, 31);
		btnContentData.setText("...");

		Label lblJobId = new Label(shell, SWT.NONE);
		lblJobId.setText("JobID");
		lblJobId.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblJobId.setBackground(new Color(shell.getDisplay(), 238, 238, 238));
		lblJobId.setBounds(12, 134, 39, 21);

		txtJobId = new Text(shell, SWT.BORDER);
		txtJobId.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		txtJobId.setBounds(112, 131, 134, 27);

		Label lblTitle = new Label(shell, SWT.NONE);
		lblTitle.setText("EasyXJDF");
		lblTitle.setFont(SWTResourceManager.getFont("Segoe UI", 21, SWT.BOLD | SWT.ITALIC));
		lblTitle.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblTitle.setBounds(144, 32, 125, 38);

		txtJobName = new Text(shell, SWT.BORDER);
		txtJobName.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		txtJobName.setBounds(112, 292, 388, 27);

		Label lblJobName = new Label(shell, SWT.NONE);
		lblJobName.setText("JobName");
		lblJobName.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblJobName.setBackground(new Color(shell.getDisplay(), 238, 238, 238));
		lblJobName.setBounds(14, 295, 66, 21);

		txtCatalogId = new Text(shell, SWT.BORDER);
		txtCatalogId.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		txtCatalogId.setBounds(364, 197, 134, 27);

		Label lblCatalogId = new Label(shell, SWT.NONE);
		lblCatalogId.setText("CatalogID");
		lblCatalogId.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblCatalogId.setBackground(new Color(shell.getDisplay(), 238, 238, 238));
		lblCatalogId.setBounds(270, 200, 68, 21);

		Label lblMediaQuality = new Label(shell, SWT.NONE);
		lblMediaQuality.setText("MediaQuality");
		lblMediaQuality.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblMediaQuality.setBackground(new Color(shell.getDisplay(), 238, 238, 238));
		lblMediaQuality.setBounds(12, 200, 93, 21);

		txtMediaQuality = new Text(shell, SWT.BORDER);
		txtMediaQuality.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		txtMediaQuality.setBounds(112, 197, 134, 27);

		txtCustomerId = new Text(shell, SWT.BORDER);
		txtCustomerId.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		txtCustomerId.setBounds(364, 161, 134, 27);

		Label lblCustomerId = new Label(shell, SWT.NONE);
		lblCustomerId.setText("CustomerID");
		lblCustomerId.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblCustomerId.setBackground(new Color(shell.getDisplay(), 238, 238, 238));
		lblCustomerId.setBounds(270, 167, 88, 21);

		Label lblInfo = new Label(shell, SWT.NONE);
		lblInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {

				// show info
				new InfoController(shell).showView();
			}
		});
		lblInfo.setToolTipText("Click for Info about EasyXJDF...");
		lblInfo.setBackground(new Color(shell.getDisplay(), 238, 238, 238));
		lblInfo.setBounds(14, 354, 24, 24);
		lblInfo.setImage(imgInfo);

		Label lblCIP4Logo = new Label(shell, SWT.NONE);
		lblCIP4Logo.setBounds(4, 2, 111, 70);
		lblCIP4Logo.setImage(imgCip4);

		Label lblTitleBg = new Label(shell, SWT.NONE);
		lblTitleBg.setBounds(0, 0, shell.getSize().x, 100);
		lblTitleBg.setImage(new Image(shell.getDisplay(), imgTitleBg.getImageData().scaledTo(shell.getSize().x, 100)));

		shell.setTabList(new Control[] { txtJobId, txtAmount, txtCatalogId, txtCustomerId, txtMediaQuality, txtJobName, btnContentData, btnSaveAs, btnSend, txtRunList });

	}

	/**
	 * Updates the JobNames value base on ContentData file path.
	 */
	private void updateJobName() {

		String contentData = txtRunList.getText();

		if (contentData == null) {
			return;
		}

		String jobName = FilenameUtils.getBaseName(contentData);
		jobName = StringUtils.replace(jobName, "_", " ");
		jobName = StringUtils.replace(jobName, "-", " ");
		jobName = StringUtils.replace(jobName, ".", " ");

		if (StringUtils.isEmpty(txtJobName.getText()) || txtJobName.getText().equals(oldJobName)) {
			txtJobName.setText(jobName);
		}

		oldJobName = jobName;
	}

	/**
	 * Notify a list of SaveAsListners.
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

			try {

				// create model object
				XJdfModel model = createModel();

				// notify all listeners
				for (XJdfSaveAsEventListener l : xJdfSaveAsListener) {
					l.notify(new XJdfSaveAsEvent(model, path));
				}

			} catch (Exception ex) {

				// process exception
				ErrorController.processException(shell, ex);
			}
		}
	}

	/**
	 * Notify a list of SendListners.
	 */
	private void processSend() {

		try {

			// create model object
			XJdfModel model = createModel();

			// notify all listeners
			for (XJdfSendEventListener l : xJdfSendListener) {
				l.notify(new XJdfSendEvent(model));
			}

		} catch (Exception ex) {

			// process exception
			ErrorController.processException(shell, ex);
		}
	}

	/**
	 * Create new model object by form content.
	 * @return Model object which contains form details.
	 */
	private XJdfModel createModel() {

		// create model object
		XJdfModel model = new XJdfModel();

		// fill attributes
		model.setJobId(txtJobId.getText());
		model.setAmount(Integer.parseInt(txtAmount.getText()));
		model.setRunList(txtRunList.getText());
		model.setCatalogId(txtCatalogId.getText());
		model.setCustomerId(txtCustomerId.getText());
		model.setMediaQuality(txtMediaQuality.getText());
		model.setJobName(txtJobName.getText());

		// return result
		return model;
	}

	/**
	 * Append listener for SaveAs Event.
	 * @param saveAsListener XJdfListener to append to.
	 */
	public void addXJdfSaveAsListener(XJdfSaveAsEventListener xJdfSaveAsListener) {
		this.xJdfSaveAsListener.add(xJdfSaveAsListener);
	}

	/**
	 * Append listener for Send Event.
	 * @param sendListener XJdfListener to append to.
	 */
	public void addXJdfSendListener(XJdfSendEventListener xJdfSendListener) {
		this.xJdfSendListener.add(xJdfSendListener);
	}
}
