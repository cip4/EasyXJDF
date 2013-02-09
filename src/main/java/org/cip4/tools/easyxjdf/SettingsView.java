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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.cip4.tools.easyxjdf.event.SettingsSaveEvent;
import org.cip4.tools.easyxjdf.event.SettingsSaveEventListener;
import org.cip4.tools.easyxjdf.model.SettingsModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
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
import org.eclipse.swt.widgets.MessageBox;
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

	private final static String FIELD_AMOUNT = "Amount";

	private final static String FIELD_MEDIA_QUALITY = "MediaQuality";

	private final static String FIELD_CUSTOMER_ID = "CustomerID";

	private final static String FIELD_CATALOG_ID = "CatalogID";

	private final static String NEW_LINE = "\r\n";

	private final List<SettingsSaveEventListener> settingsSaveEventListener;

	private final Shell parent;

	private final SettingsModel settingsModel;

	private SettingsModel result;

	protected Shell shell;

	private final Map<String, String> mapUrls;

	private final Map<String, String> mapLogo;

	private Combo cmbSystemType;

	private Label lblLogo;

	private Text txtUrl;

	private Text txtValues;

	private Button chkDefault;

	private Button chkAutoExtend;

	private Combo cmbFields;

	/**
	 * Create the dialog.
	 * @param parent The parent view.
	 */
	public SettingsView(Shell parent, SettingsModel settingsModel) {

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

		settingsSaveEventListener = new ArrayList<SettingsSaveEventListener>();

		this.settingsModel = settingsModel;
		this.parent = parent;
		this.result = null;
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
	 * Open the dialog.
	 * @return the result
	 */
	public SettingsModel open() {

		// create contents
		createContents();

		// Move the dialog to the center of the top level shell.
		Rectangle shellBounds = parent.getBounds();
		Point dialogSize = shell.getSize();
		shell.setLocation(shellBounds.x + (shellBounds.width - dialogSize.x) / 2, shellBounds.y + (shellBounds.height - dialogSize.y) / 2);

		// show model in view
		model2View();

		// prepare view
		updateConnection();

		// further view settings when view opens
		if (!StringUtils.isEmpty(settingsModel.getUrl())) {
			txtUrl.setText(settingsModel.getUrl());
		} else {
			chkDefault.setSelection(true);
		}

		// open
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		// return result settings model
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
				updateConnection();
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
		lblUrl.setBounds(10, 123, 74, 21);

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

		chkDefault = new Button(compConnection, SWT.CHECK);
		chkDefault.setText("Always use this URL");
		chkDefault.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		chkDefault.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		chkDefault.setBounds(90, 168, 283, 21);

		TabItem tabFields = new TabItem(tabFolder, SWT.NONE);
		tabFields.setText("Field Suggestions");

		Composite compFields = new Composite(tabFolder, SWT.NONE);
		tabFields.setControl(compFields);

		Label lblFieldTitle = new Label(compFields, SWT.NONE);
		lblFieldTitle.setText("Field Suggestion Settings");
		lblFieldTitle.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.BOLD | SWT.ITALIC));
		lblFieldTitle.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblFieldTitle.setBounds(10, 21, 223, 23);

		cmbFields = new Combo(compFields, SWT.READ_ONLY);
		cmbFields.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateField();
			}
		});
		cmbFields.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		cmbFields.setItems(new String[] { "Amount", "MediaQuality", "CustomerID", "CatalogID" });
		cmbFields.setBounds(10, 60, 131, 23);
		cmbFields.select(0);
		cmbFields.setText("Amount\r\nMediaQuality\r\nCustomerID\r\nCatalogID\r\n");

		txtValues = new Text(compFields, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		txtValues.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {

				try {
					view2Model();
				} catch (Exception ex) {
					ErrorController.processException(shell, ex);
				}
			}
		});
		txtValues.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtValues.setBounds(156, 60, 156, 149);

		Label lblInfo = new Label(compFields, SWT.NONE);
		lblInfo.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblInfo.setText("Each line in \r\ntextfield \r\nrepresents a \r\nsingle element.");
		lblInfo.setBounds(325, 123, 116, 86);

		chkAutoExtend = new Button(compFields, SWT.CHECK);
		chkAutoExtend.setToolTipText("Extend suggestion values automatically.");
		chkAutoExtend.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		chkAutoExtend.setBounds(10, 189, 160, 20);
		chkAutoExtend.setText("Auto-Extend");

		Button btnSave = new Button(shell, SWT.NONE);
		btnSave.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				processSave();
			}
		});
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				processSave();
			}
		});
		btnSave.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnSave.setBounds(375, 330, 84, 31);
		btnSave.setText("Ok");

		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				processCancel();
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
	 * Append listener for SettingsSave Event.
	 * @param settingsSaveEventListener SettingsSaveEventListener implementation to append to.
	 */
	public void addSettingsSaveEventListener(SettingsSaveEventListener settingsSaveEventListener) {
		this.settingsSaveEventListener.add(settingsSaveEventListener);
	}

	/**
	 * Validates the URL.
	 */
	private boolean validateUrl() {

		boolean result = false;

		// read url
		String url = txtUrl.getText();

		// validate
		try {
			new URL(url);

			result = true;

		} catch (MalformedURLException mex) {

		}

		// return result
		return result;
	}

	/**
	 * Show model in view.
	 */
	private void model2View() {

		// connection settings
		cmbSystemType.setText(settingsModel.getSystemType());
		txtUrl.setText(settingsModel.getUrl());
		chkDefault.setSelection(settingsModel.isDefaultUrl());
		chkAutoExtend.setSelection(settingsModel.isAutoExtend());

		// field settings
		updateField();

	}

	private void view2Model() {

		// connection settings
		settingsModel.setSystemType(cmbSystemType.getText());
		settingsModel.setUrl(txtUrl.getText());
		settingsModel.setDefaultUrl(chkDefault.getSelection());
		settingsModel.setAutoExtend(chkAutoExtend.getSelection());

		// field settings
		if (FIELD_AMOUNT.equals(cmbFields.getText())) {

			if (!StringUtils.isEmpty(txtValues.getText())) {
				String[] values = txtValues.getText().split(NEW_LINE);
				List<Integer> lst = new ArrayList<Integer>(values.length);

				try {
					for (String val : values) {
						lst.add(Integer.parseInt(val));
					}

					settingsModel.setAmounts(lst);
				} catch (NumberFormatException ne) {

					// show message
					showInfo("At least one Amount is not numeric.");

					// set focus
					txtValues.setFocus();
				}

			} else {
				settingsModel.setAmounts(new ArrayList<Integer>());
			}

		}

		if (FIELD_MEDIA_QUALITY.equals(cmbFields.getText())) {

			if (!StringUtils.isEmpty(txtValues.getText())) {
				String[] values = txtValues.getText().split(NEW_LINE);
				settingsModel.setMediaQualities(Arrays.asList(values));
			} else {
				settingsModel.setMediaQualities(new ArrayList<String>());
			}
		}

		if (FIELD_CATALOG_ID.equals(cmbFields.getText())) {

			if (!StringUtils.isEmpty(txtValues.getText())) {
				String[] values = txtValues.getText().split(NEW_LINE);
				settingsModel.setCatalogIDs(Arrays.asList(values));
			} else {
				settingsModel.setCatalogIDs(new ArrayList<String>());
			}
		}

		if (FIELD_CUSTOMER_ID.equals(cmbFields.getText())) {

			if (!StringUtils.isEmpty(txtValues.getText())) {
				String[] values = txtValues.getText().split(NEW_LINE);
				settingsModel.setCustomerIDs(Arrays.asList(values));
			} else {
				settingsModel.setCustomerIDs(new ArrayList<String>());
			}
		}
	}

	/**
	 * Cancel editing settings.
	 */
	private void processCancel() {

		// show message
		MessageBox mb = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK | SWT.CANCEL);
		mb.setMessage("Cancel editing settings?");

		if (SWT.OK == mb.open()) {
			shell.close();
		}
	}

	/**
	 * Save all settings.
	 */
	private void processSave() {

		// validate
		if (!validateUrl()) {

			// show info
			showInfo("URL is not valid.");

			// set focus
			txtUrl.setFocus();

			// return
			return;
		}

		try {

			// read new settings
			view2Model();

			// notify all listeners
			for (SettingsSaveEventListener l : settingsSaveEventListener) {
				l.notify(new SettingsSaveEvent(settingsModel));
			}

			// set result
			this.result = settingsModel;

			// close window
			shell.close();

		} catch (Exception ex) {

			// process exception
			ErrorController.processException(shell, ex);
		}

	}

	/**
	 * Update URL Textfield
	 */
	private void updateConnection() {

		String systemType = cmbSystemType.getText();

		InputStream isLogo = SettingsView.class.getResourceAsStream(mapLogo.get(systemType));
		Image imgLogo = new Image(shell.getDisplay(), isLogo);

		int width = imgLogo.getImageData().width;
		int height = imgLogo.getImageData().height;

		double scaleFactor = 30d / height;

		imgLogo = new Image(shell.getDisplay(), imgLogo.getImageData().scaledTo((int) (width * scaleFactor), (int) (height * scaleFactor)));

		lblLogo.setImage(imgLogo);

		// update URL text field
		String url = mapUrls.get(systemType);
		txtUrl.setText(url);
	}

	/**
	 * Update field values.
	 */
	private void updateField() {

		// get field name
		String fieldName = cmbFields.getText();

		// build string
		StringBuilder b = new StringBuilder(255);

		if (FIELD_AMOUNT.equals(fieldName)) {
			for (Integer amount : settingsModel.getAmounts()) {
				b.append(amount.toString());
				b.append(NEW_LINE);
			}
		}

		if (FIELD_MEDIA_QUALITY.equals(fieldName)) {
			for (String mediaQuality : settingsModel.getMediaQualities()) {
				b.append(mediaQuality);
				b.append(NEW_LINE);
			}
		}

		if (FIELD_CATALOG_ID.equals(fieldName)) {
			for (String catalogId : settingsModel.getCatalogIDs()) {
				b.append(catalogId);
				b.append(NEW_LINE);
			}
		}

		if (FIELD_CUSTOMER_ID.equals(fieldName)) {
			for (String customerId : settingsModel.getCustomerIDs()) {
				b.append(customerId);
				b.append(NEW_LINE);
			}
		}

		// show string
		txtValues.setText(b.toString());
	}
}
