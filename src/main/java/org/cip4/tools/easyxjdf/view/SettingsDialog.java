package org.cip4.tools.easyxjdf.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.cip4.tools.easyxjdf.controller.ErrorController;
import org.cip4.tools.easyxjdf.event.SettingsSaveEventListener;
import org.cip4.tools.easyxjdf.model.SettingsModel;

public class SettingsDialog extends JDialog {

	private static final long serialVersionUID = -7920567033339174358L;

	private final static String FIELD_AMOUNT = "Amount";

	private final static String FIELD_MEDIA_QUALITY = "MediaQuality";

	private final static String FIELD_CUSTOMER_ID = "CustomerID";

	private final static String FIELD_CATALOG_ID = "CatalogID";

	private final static String NEW_LINE = "\n";

	final List<SettingsSaveEventListener> settingsSaveEventListener;

	private final JPanel contentPanel;

	private JTextField txtUrl;

	private JTextArea txtValues;

	private JComboBox cmbFields;

	private JComboBox cmbSystemType;

	private JCheckBox chkDefault;

	private JCheckBox chkAutoExtend;

	private JLabel lblLogo;

	private final Map<String, String> mapUrls;

	private final Map<String, String> mapLogo;

	private final SettingsModel settingsModel;

	private SettingsModel result;

	/**
	 * Create the dialog.
	 * @wbp.parser.constructor
	 */
	public SettingsDialog(final JFrame parent, SettingsModel settingsModel) {

		super(parent, true);
		setResizable(false);

		getContentPane().setBackground(Color.WHITE);

		// init instance variables
		mapUrls = new LinkedHashMap<String, String>(5);
		mapUrls.put("Others", "http://");
		mapUrls.put("Heidelberg Prinect", "http://[YOUR_SERVER]:6090/w2pc/xjdf");

		mapLogo = new LinkedHashMap<String, String>(5);
		mapLogo.put("Others", "/org/cip4/tools/easyxjdf/gui/xjdf-logo-white.png");
		mapLogo.put("Heidelberg Prinect", "/org/cip4/tools/easyxjdf/gui/heidelberg-logo.png");

		settingsSaveEventListener = new ArrayList<SettingsSaveEventListener>();

		this.settingsModel = settingsModel;
		this.result = null;

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// init panel
		contentPanel = new JPanel();
		setBounds(100, 100, 450, 408);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);
		setLocationRelativeTo(parent);
		{
			Border border = BorderFactory.createLineBorder(Color.BLACK);

			JTabbedPane tabs = new JTabbedPane();
			tabs.setBackground(Color.WHITE);
			sl_contentPanel.putConstraint(SpringLayout.WEST, tabs, 0, SpringLayout.WEST, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.SOUTH, tabs, -5, SpringLayout.SOUTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.EAST, tabs, 0, SpringLayout.EAST, contentPanel);

			JPanel panelConnection = new JPanel();
			panelConnection.setBackground(Color.WHITE);
			tabs.addTab("Connection", panelConnection);
			SpringLayout sl_panelConnection = new SpringLayout();
			panelConnection.setLayout(sl_panelConnection);

			JLabel lblConnectionSettings = new JLabel("Connection Settings");
			lblConnectionSettings.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 15));
			sl_panelConnection.putConstraint(SpringLayout.NORTH, lblConnectionSettings, 10, SpringLayout.NORTH, panelConnection);
			sl_panelConnection.putConstraint(SpringLayout.WEST, lblConnectionSettings, 10, SpringLayout.WEST, panelConnection);
			panelConnection.add(lblConnectionSettings);

			JLabel lblSystem = new JLabel("System:");
			sl_panelConnection.putConstraint(SpringLayout.NORTH, lblSystem, 39, SpringLayout.SOUTH, lblConnectionSettings);
			sl_panelConnection.putConstraint(SpringLayout.WEST, lblSystem, 0, SpringLayout.WEST, lblConnectionSettings);
			panelConnection.add(lblSystem);

			JLabel lblUrl = new JLabel("URL:");
			sl_panelConnection.putConstraint(SpringLayout.NORTH, lblUrl, 48, SpringLayout.SOUTH, lblSystem);
			sl_panelConnection.putConstraint(SpringLayout.WEST, lblUrl, 0, SpringLayout.WEST, lblConnectionSettings);
			panelConnection.add(lblUrl);

			chkDefault = new JCheckBox("Use this URL as default");
			panelConnection.add(chkDefault);

			cmbSystemType = new JComboBox();
			sl_panelConnection.putConstraint(SpringLayout.WEST, cmbSystemType, 6, SpringLayout.EAST, lblSystem);
			sl_panelConnection.putConstraint(SpringLayout.WEST, chkDefault, 0, SpringLayout.WEST, cmbSystemType);
			cmbSystemType.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					updateConnection();
				}
			});
			cmbSystemType.setModel(new DefaultComboBoxModel(mapLogo.keySet().toArray()));
			sl_panelConnection.putConstraint(SpringLayout.NORTH, cmbSystemType, 35, SpringLayout.SOUTH, lblConnectionSettings);
			cmbSystemType.setSelectedItem(mapLogo.keySet().toArray()[0]);
			panelConnection.add(cmbSystemType);

			txtUrl = new JTextField();
			sl_panelConnection.putConstraint(SpringLayout.NORTH, chkDefault, 6, SpringLayout.SOUTH, txtUrl);
			sl_panelConnection.putConstraint(SpringLayout.WEST, txtUrl, 0, SpringLayout.WEST, cmbSystemType);
			sl_panelConnection.putConstraint(SpringLayout.EAST, txtUrl, -10, SpringLayout.EAST, panelConnection);
			sl_panelConnection.putConstraint(SpringLayout.NORTH, txtUrl, -6, SpringLayout.NORTH, lblUrl);
			panelConnection.add(txtUrl);
			txtUrl.setColumns(10);

			lblLogo = new JLabel(" ");
			sl_panelConnection.putConstraint(SpringLayout.EAST, cmbSystemType, -6, SpringLayout.WEST, lblLogo);
			sl_panelConnection.putConstraint(SpringLayout.NORTH, lblLogo, 52, SpringLayout.NORTH, panelConnection);
			sl_panelConnection.putConstraint(SpringLayout.WEST, lblLogo, 244, SpringLayout.WEST, panelConnection);
			sl_panelConnection.putConstraint(SpringLayout.SOUTH, lblLogo, -23, SpringLayout.NORTH, txtUrl);
			sl_panelConnection.putConstraint(SpringLayout.EAST, lblLogo, -10, SpringLayout.EAST, panelConnection);
			lblLogo.setIcon(new ImageIcon(SettingsDialog.class.getResource("/org/cip4/tools/easyxjdf/gui/xjdf-logo-white.png")));
			panelConnection.add(lblLogo);

			JPanel panelFields = new JPanel();
			panelFields.setBackground(Color.WHITE);
			tabs.addTab("Field Suggenstions", panelFields);
			SpringLayout sl_panelFields = new SpringLayout();
			panelFields.setLayout(sl_panelFields);

			JLabel lblFieldSuggestionSettings = new JLabel("Field Suggestion Settings");
			lblFieldSuggestionSettings.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 15));
			sl_panelFields.putConstraint(SpringLayout.NORTH, lblFieldSuggestionSettings, 10, SpringLayout.NORTH, panelFields);
			sl_panelFields.putConstraint(SpringLayout.WEST, lblFieldSuggestionSettings, 10, SpringLayout.WEST, panelFields);
			panelFields.add(lblFieldSuggestionSettings);

			cmbFields = new JComboBox();
			cmbFields.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					updateField();
				}
			});
			cmbFields.setModel(new DefaultComboBoxModel(new String[] { FIELD_AMOUNT, FIELD_MEDIA_QUALITY, FIELD_CATALOG_ID, FIELD_CUSTOMER_ID }));
			sl_panelFields.putConstraint(SpringLayout.NORTH, cmbFields, 26, SpringLayout.SOUTH, lblFieldSuggestionSettings);
			sl_panelFields.putConstraint(SpringLayout.WEST, cmbFields, 10, SpringLayout.WEST, panelFields);
			sl_panelFields.putConstraint(SpringLayout.EAST, cmbFields, 184, SpringLayout.WEST, panelFields);
			cmbFields.setSelectedIndex(0);
			panelFields.add(cmbFields);

			txtValues = new JTextArea();
			txtValues.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {
					try {
						view2Model();
					} catch (Exception ex) {
						ErrorController.processException(parent, ex);
					}
				}
			});
			sl_panelFields.putConstraint(SpringLayout.NORTH, txtValues, 0, SpringLayout.NORTH, cmbFields);
			sl_panelFields.putConstraint(SpringLayout.WEST, txtValues, 13, SpringLayout.EAST, cmbFields);
			sl_panelFields.putConstraint(SpringLayout.SOUTH, txtValues, -10, SpringLayout.SOUTH, panelFields);
			sl_panelFields.putConstraint(SpringLayout.EAST, txtValues, -10, SpringLayout.EAST, panelFields);
			txtValues.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
			panelFields.add(txtValues);

			JLabel lblDescLine1 = new JLabel("Each line in");
			sl_panelFields.putConstraint(SpringLayout.WEST, lblDescLine1, 16, SpringLayout.WEST, panelFields);
			lblDescLine1.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
			panelFields.add(lblDescLine1);

			JLabel lblDescLine2 = new JLabel("textfield");
			sl_panelFields.putConstraint(SpringLayout.NORTH, lblDescLine2, 6, SpringLayout.SOUTH, lblDescLine1);
			sl_panelFields.putConstraint(SpringLayout.WEST, lblDescLine2, 0, SpringLayout.WEST, lblDescLine1);
			lblDescLine2.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
			panelFields.add(lblDescLine2);

			JLabel lblDescLine3 = new JLabel("represents a");
			sl_panelFields.putConstraint(SpringLayout.NORTH, lblDescLine3, 6, SpringLayout.SOUTH, lblDescLine2);
			sl_panelFields.putConstraint(SpringLayout.WEST, lblDescLine3, 0, SpringLayout.WEST, lblDescLine1);
			lblDescLine3.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
			panelFields.add(lblDescLine3);

			JLabel lblDescLine4 = new JLabel("single element.");
			sl_panelFields.putConstraint(SpringLayout.NORTH, lblDescLine4, 6, SpringLayout.SOUTH, lblDescLine3);
			sl_panelFields.putConstraint(SpringLayout.WEST, lblDescLine4, 0, SpringLayout.WEST, lblDescLine1);
			lblDescLine4.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
			panelFields.add(lblDescLine4);

			chkAutoExtend = new JCheckBox("Auto-Extend");
			sl_panelFields.putConstraint(SpringLayout.SOUTH, lblDescLine4, -16, SpringLayout.NORTH, chkAutoExtend);
			sl_panelFields.putConstraint(SpringLayout.SOUTH, lblDescLine3, -36, SpringLayout.NORTH, chkAutoExtend);
			sl_panelFields.putConstraint(SpringLayout.SOUTH, lblDescLine2, -56, SpringLayout.NORTH, chkAutoExtend);
			sl_panelFields.putConstraint(SpringLayout.SOUTH, lblDescLine1, -76, SpringLayout.NORTH, chkAutoExtend);
			sl_panelFields.putConstraint(SpringLayout.SOUTH, chkAutoExtend, 0, SpringLayout.SOUTH, txtValues);
			sl_panelFields.putConstraint(SpringLayout.WEST, chkAutoExtend, 10, SpringLayout.WEST, panelFields);
			panelFields.add(chkAutoExtend);

			contentPanel.add(tabs);

			JLabel lblLogoCip4 = new JLabel("");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, tabs, 6, SpringLayout.SOUTH, lblLogoCip4);
			lblLogoCip4.setIcon(new ImageIcon(SettingsDialog.class.getResource("/org/cip4/tools/easyxjdf/gui/cip4-logo-small.png")));
			contentPanel.add(lblLogoCip4);

			JLabel lblLogoXJdf = new JLabel("");
			sl_contentPanel.putConstraint(SpringLayout.SOUTH, lblLogoXJdf, -6, SpringLayout.NORTH, tabs);
			sl_contentPanel.putConstraint(SpringLayout.EAST, lblLogoXJdf, -10, SpringLayout.EAST, contentPanel);
			lblLogoXJdf.setIcon(new ImageIcon(SettingsDialog.class.getResource("/org/cip4/tools/easyxjdf/gui/xjdf-logo-small.png")));
			contentPanel.add(lblLogoXJdf);

			// show model in view
			model2View();

			// prepare view
			updateConnection();

			// further view settings when view opens
			if (!StringUtils.isEmpty(settingsModel.getUrl())) {
				txtUrl.setText(settingsModel.getUrl());
			} else {
				chkDefault.setSelected(true);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.WHITE);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						processSave();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						processCancel();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	/**
	 * Getter for result attribute.
	 * @return the result
	 */
	public SettingsModel getResult() {
		return result;
	}

	/**
	 * Append listener for SettingsSave Event.
	 * @param settingsSaveEventListener SettingsSaveEventListener implementation to append to.
	 */
	public void addSettingsSaveEventListener(SettingsSaveEventListener settingsSaveEventListener) {
		this.settingsSaveEventListener.add(settingsSaveEventListener);
	}

	/**
	 * Show an info message in a MessageBox.
	 * @param message Message to show in a MessageBox.
	 */
	public void showInfo(String message) {

		// show message
		JOptionPane.showMessageDialog(this, message);
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

	public void showDialog() {
		setVisible(true);
	}

	/**
	 * Show model in view.
	 */
	private void model2View() {

		// connection settings
		cmbSystemType.setSelectedItem(settingsModel.getSystemType());
		txtUrl.setText(settingsModel.getUrl());
		chkDefault.setSelected(settingsModel.isDefaultUrl());
		chkAutoExtend.setSelected(settingsModel.isAutoExtend());

		// field settings
		updateField();

	}

	private void view2Model() {

		// connection settings
		settingsModel.setSystemType(cmbSystemType.getSelectedItem().toString());
		settingsModel.setUrl(txtUrl.getText());
		settingsModel.setDefaultUrl(chkDefault.isSelected());
		settingsModel.setAutoExtend(chkAutoExtend.isSelected());

		// field settings
		if (FIELD_AMOUNT.equals(cmbFields.getSelectedItem().toString())) {

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
					txtValues.requestFocus();

					return;
				}

			} else {
				settingsModel.setAmounts(new ArrayList<Integer>());
			}

		}

		if (FIELD_MEDIA_QUALITY.equals(cmbFields.getSelectedItem().toString())) {

			if (!StringUtils.isEmpty(txtValues.getText())) {
				String[] values = txtValues.getText().split(NEW_LINE);
				settingsModel.setMediaQualities(Arrays.asList(values));
			} else {
				settingsModel.setMediaQualities(new ArrayList<String>());
			}
		}

		if (FIELD_CATALOG_ID.equals(cmbFields.getSelectedItem().toString())) {

			if (!StringUtils.isEmpty(txtValues.getText())) {
				String[] values = txtValues.getText().split(NEW_LINE);
				settingsModel.setCatalogIDs(Arrays.asList(values));
			} else {
				settingsModel.setCatalogIDs(new ArrayList<String>());
			}
		}

		if (FIELD_CUSTOMER_ID.equals(cmbFields.getSelectedItem().toString())) {

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
		int resultCode = JOptionPane.showConfirmDialog(this, "Cancel editing settings?", "Cancel...", JOptionPane.YES_NO_OPTION);

		if (resultCode == JOptionPane.YES_OPTION) {
			this.result = null;
			dispose();
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
			txtUrl.requestFocus();

			// return
			return;
		}

		try {

			// read new settings
			view2Model();

			// set result
			this.result = settingsModel;

			// close window
			dispose();

		} catch (Exception ex) {

			// process exception
			ErrorController.processException(this, ex);
		}

	}

	/**
	 * Update URL Textfield
	 */
	private void updateConnection() {

		String systemType = cmbSystemType.getSelectedItem().toString();

		InputStream isLogo = SettingsDialog.class.getResourceAsStream(mapLogo.get(systemType));
		ImageIcon imgLogo = null;
		try {
			imgLogo = new ImageIcon(IOUtils.toByteArray(isLogo));
		} catch (IOException e) {
			ErrorController.processException(this, e);
			return;
		}

		int width = imgLogo.getIconWidth();
		int height = imgLogo.getIconHeight();

		double scaleFactor = 30d / height;

		width = (int) (width * scaleFactor);
		height = (int) (height * scaleFactor);

		Image tmpImg = imgLogo.getImage();
		BufferedImage resizedImage = new BufferedImage(width, height, Image.SCALE_DEFAULT);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(tmpImg, 0, 0, width, height, null);
		g.dispose();

		imgLogo = new ImageIcon(resizedImage);
		lblLogo.setIcon(imgLogo);

		// update URL text field
		String url = mapUrls.get(systemType);
		txtUrl.setText(url);
	}

	/**
	 * Update field values.
	 */
	private void updateField() {

		// get field name
		String fieldName = cmbFields.getSelectedItem().toString();

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
