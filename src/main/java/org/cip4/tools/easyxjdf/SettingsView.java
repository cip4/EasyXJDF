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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.cip4.tools.easyxjdf.event.SendEventListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * Send XJDF View Class.
 * @author s.meissner
 * @date 30.01.2013
 */
public class SettingsView extends Dialog {

	private final List<SendEventListener> sendListener;

	private final Shell parent;

	protected Object result;

	protected Shell shell;

	private final String oldUrl = "";

	private final Map<String, String> mapUrls;

	private final Map<String, String> mapLogo;

	private Combo cmbSystemType;

	private Label lblLogo;

	private Text txtUrl;

	private Text txtValues;

	/**
	 * Create the dialog.
	 * @param parent The parent view.
	 */
	public SettingsView(Shell parent) {

		// init dialog
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		setText("Send XJDF to...");

		// init instance variables
		mapUrls = new LinkedHashMap<String, String>(5);
		mapUrls.put("Others", "http://");
		mapUrls.put("Heidelberg Prinect", "http://[YOUR_SERVER]:6090/w2pc/xjdf");
		mapUrls.put("FLYERALARM API", "http://api.flyeralarm.com/xjdf");

		mapLogo = new LinkedHashMap<String, String>(5);
		mapLogo.put("Others", "/org/cip4/tools/easyxjdf/gui/xjdf-logo.png");
		mapLogo.put("Heidelberg Prinect", "/org/cip4/tools/easyxjdf/gui/heidelberg-logo.png");
		mapLogo.put("FLYERALARM API", "/org/cip4/tools/easyxjdf/gui/fa-logo.png");

		sendListener = new ArrayList<SendEventListener>();

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
		shell = new Shell(getParent(), SWT.CLOSE | SWT.TITLE | SWT.APPLICATION_MODAL);
		shell.setSize(475, 399);
		shell.setText("EasyXJDF Settings");

		Image imgTitleBg = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/title-bg.png"));
		Image imgXJdf = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/xjdf-logo-small.png"));
		Image imgCIP4 = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/cip4-logo-small.png"));
		Image imgSettings = new Image(shell.getDisplay(), XJdfView.class.getResourceAsStream("/org/cip4/tools/easyxjdf/gui/settings.png"));

		shell.setImage(imgSettings);

		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		tabFolder.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tabFolder.setBounds(10, 65, 449, 252);

		TabItem tabConnection = new TabItem(tabFolder, SWT.NONE);
		tabConnection.setText("Connection");

		Composite compConnection = new Composite(tabFolder, SWT.NONE);
		tabConnection.setControl(compConnection);

		cmbSystemType = new Combo(compConnection, SWT.READ_ONLY);
		cmbSystemType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateUrl(cmbSystemType.getText());
			}
		});

		cmbSystemType.setItems(mapUrls.keySet().toArray(new String[mapUrls.size()]));
		cmbSystemType.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		cmbSystemType.setBounds(90, 66, 166, 29);
		cmbSystemType.select(0);

		Label lblSystemType = new Label(compConnection, SWT.NONE);
		lblSystemType.setText("Sytem:");
		lblSystemType.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblSystemType.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblSystemType.setBounds(10, 69, 84, 21);

		Label lblUrl = new Label(compConnection, SWT.NONE);
		lblUrl.setText("URL:");
		lblUrl.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblUrl.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblUrl.setBounds(10, 123, 32, 21);

		txtUrl = new Text(compConnection, SWT.BORDER);
		txtUrl.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		txtUrl.setBounds(90, 120, 345, 27);

		lblLogo = new Label(compConnection, SWT.NONE);
		lblLogo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblLogo.setBounds(262, 66, 166, 29);

		Label lblTitleConnection = new Label(compConnection, SWT.NONE);
		lblTitleConnection.setText("Connection Settings");
		lblTitleConnection.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD | SWT.ITALIC));
		lblTitleConnection.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblTitleConnection.setBounds(10, 21, 156, 23);

		Button chkDefault = new Button(compConnection, SWT.CHECK);
		chkDefault.setText("Always use this URL");
		chkDefault.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		chkDefault.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		chkDefault.setBounds(90, 168, 283, 21);

		TabItem tabFields = new TabItem(tabFolder, SWT.NONE);
		tabFields.setText("Field Suggestions");

		Composite compFields = new Composite(tabFolder, SWT.NONE);
		tabFields.setControl(compFields);

		Label lblFieldTitle = new Label(compFields, SWT.NONE);
		lblFieldTitle.setText("Field Values");
		lblFieldTitle.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD | SWT.ITALIC));
		lblFieldTitle.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblFieldTitle.setBounds(10, 21, 156, 23);

		Combo cmbFields = new Combo(compFields, SWT.READ_ONLY);
		cmbFields.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		cmbFields.setItems(new String[] { "Amount", "MediaQuality", "CustomerID", "CatalogID" });
		cmbFields.setBounds(10, 60, 131, 23);
		cmbFields.select(0);
		cmbFields.setText("Amount\r\nMediaQuality\r\nCustomerID\r\nCatalogID\r\n");

		txtValues = new Text(compFields, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		txtValues.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtValues.setBounds(156, 60, 156, 149);

		Label lblInfo = new Label(compFields, SWT.NONE);
		lblInfo.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblInfo.setText("Each line in \r\ntextfield \r\nrepresents a \r\nsingle element.");
		lblInfo.setBounds(325, 123, 106, 86);

		Button btnOk = new Button(shell, SWT.NONE);
		btnOk.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
		});
		btnOk.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnOk.setBounds(375, 330, 84, 31);
		btnOk.setText("Ok");

		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				shell.close();
			}
		});
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {

			}
		});
		btnCancel.setText("Cancel");
		btnCancel.setBounds(271, 330, 84, 31);

		Label lblXJDF = new Label(shell, SWT.NONE);
		lblXJDF.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblXJDF.setBounds(404, 10, 55, 31);
		lblXJDF.setImage(imgXJdf);

		Label lblCIP4 = new Label(shell, SWT.NONE);
		lblCIP4.setBounds(10, 10, 50, 32);
		lblCIP4.setImage(imgCIP4);

		Label lblTitleBg = new Label(shell, SWT.NONE);
		lblTitleBg.setBounds(0, -33, 629, 104);
		lblTitleBg.setImage(new Image(shell.getDisplay(), imgTitleBg.getImageData().scaledTo(shell.getSize().x, 100)));
	}

	/**
	 * Append listener for SaveAs Event.
	 * @param saveAsListener XJdfListener to append to.
	 */
	public void addSendListener(SendEventListener sendListener) {
		this.sendListener.add(sendListener);
	}

	// private void processSend() {
	//
	// try {
	//
	// // create model object
	// SendModel model = createModel();
	//
	// // notify all listeners
	// for (SendEventListener l : sendListener) {
	// l.notify(new SendEvent(model));
	// }
	//
	// // close window
	// shell.close();
	//
	// } catch (Exception ex) {
	//
	// // process exception
	// ErrorController.processException(shell, ex);
	// }
	//
	// }
	//
	// /**
	// * Create new model object by form content.
	// * @return Model object which contains form details.
	// */
	// private SendModel createModel() {
	//
	// // create model object
	// SendModel model = new SendModel();
	//
	// // fill attributes
	// model.setSystemType(cmbSystemType.getText());
	// model.setTargetUrl(txtTargetUrl.getText());
	// model.setDefault(chkDefault.getSelection());
	//
	// // return result
	// return model;
	// }
	//
	/**
	 * Update URL Textfield
	 */
	private void updateUrl(String systemType) {

		InputStream isLogo = SettingsView.class.getResourceAsStream(mapLogo.get(systemType));
		Image imgLogo = new Image(shell.getDisplay(), isLogo);

		int width = imgLogo.getImageData().width;
		int height = imgLogo.getImageData().height;

		double scaleFactor = 30d / height;

		imgLogo = new Image(shell.getDisplay(), imgLogo.getImageData().scaledTo((int) (width * scaleFactor), (int) (height * scaleFactor)));

		lblLogo.setImage(imgLogo);

		String url = mapUrls.get(systemType);
		txtUrl.setText(url);

	}
}
