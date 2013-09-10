package org.cip4.tools.easyxjdf.view;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.cip4.lib.xjdf.type.Shape;
import org.cip4.tools.easyxjdf.controller.ErrorController;
import org.cip4.tools.easyxjdf.controller.InfoController;
import org.cip4.tools.easyxjdf.controller.SettingsController;
import org.cip4.tools.easyxjdf.event.XJdfSaveAsEvent;
import org.cip4.tools.easyxjdf.event.XJdfSaveAsEventListener;
import org.cip4.tools.easyxjdf.event.XJdfSendEvent;
import org.cip4.tools.easyxjdf.event.XJdfSendEventListener;
import org.cip4.tools.easyxjdf.exception.ConnectionException;
import org.cip4.tools.easyxjdf.model.SettingsModel;
import org.cip4.tools.easyxjdf.model.XJdfModel;
import org.eclipse.wb.swing.FocusTraversalOnArray;

/**
 * XJDF View class.
 * @author stefanmeissner
 * @date 21.07.2013
 */
public class XJdfView {

	private final List<XJdfSaveAsEventListener> xJdfSaveAsListener;

	private final List<XJdfSendEventListener> xJdfSendListener;

	private SettingsModel settingsModel;

	private String oldJobName = "";

	private JFrame frmCipEasyxjdf;

	private JTextField txtJobId;

	private JTextField txtJobName;

	private JTextField txtContentData;

	private JComboBox cmbAmount;

	private JComboBox cmbCustomerId;

	private JComboBox cmbMediaQuality;

	private JComboBox cmbCatalogId;

	private JComboBox cmbNumColors;

	private JComboBox cmbFinishedDim;

	private JCheckBox chkReset;

	private JTextField txtPages;

	/**
	 * Custom constructor. Accepting a SettingsModel for initializing.
	 */
	public XJdfView(SettingsModel settingsModel) {

		// init instance variables
		xJdfSaveAsListener = new ArrayList<XJdfSaveAsEventListener>();
		xJdfSendListener = new ArrayList<XJdfSendEventListener>();

		this.settingsModel = settingsModel;

	}

	/**
	 * Open the window.
	 * @wbp.parser.constructor
	 */
	public void open() {

		// create content
		initialize();
		loadSuggenstions();
		resetView();

		// show frame
		frmCipEasyxjdf.setVisible(true);
	}

	/**
	 * Getter for frame attribute.
	 * @return the frame
	 */
	public JFrame getFrame() {
		return frmCipEasyxjdf;
	}

	/**
	 * Show an info message in a MessageBox.
	 * @param message Message to show in a MessageBox.
	 */
	public void showMessage(String message) {

		// show message
		JOptionPane.showMessageDialog(frmCipEasyxjdf, message);
	}

	/**
	 * Update settings.
	 * @param settingsModel The new settings model.
	 */
	public void updateSettings(SettingsModel settingsModel) {

		// update model
		this.settingsModel = settingsModel;

		// update suggestions
		loadSuggenstions();
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frmCipEasyxjdf = new JFrame();
		frmCipEasyxjdf.setIconImage(Toolkit.getDefaultToolkit().getImage(XJdfView.class.getResource("/org/cip4/tools/easyxjdf/gui/xjdf-logo-small.png")));
		frmCipEasyxjdf.setTitle("CIP4 EasyXJDF");
		frmCipEasyxjdf.requestFocus();
		frmCipEasyxjdf.setResizable(false);
		frmCipEasyxjdf.setBounds(100, 100, 565, 444);
		frmCipEasyxjdf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblJobId = new JLabel("Job ID");

		JLabel lblAmount = new JLabel("Amount");

		JLabel lblMediaQuality = new JLabel("Media Quality");

		JLabel lblCatalogId = new JLabel("Catalog ID");

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(XJdfView.class.getResource("/org/cip4/tools/easyxjdf/gui/title.png")));

		JLabel lblSettings = new JLabel("");
		lblSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				showSettingsDialog();
			}
		});
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, lblCatalogId, 20, SpringLayout.SOUTH, lblMediaQuality);
		springLayout.putConstraint(SpringLayout.NORTH, lblMediaQuality, 12, SpringLayout.SOUTH, lblAmount);
		springLayout.putConstraint(SpringLayout.WEST, lblCatalogId, 0, SpringLayout.WEST, lblMediaQuality);
		springLayout.putConstraint(SpringLayout.WEST, lblMediaQuality, 0, SpringLayout.WEST, lblJobId);
		springLayout.putConstraint(SpringLayout.WEST, lblAmount, 0, SpringLayout.WEST, lblJobId);
		springLayout.putConstraint(SpringLayout.NORTH, lblJobId, 38, SpringLayout.SOUTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, lblJobId, 10, SpringLayout.WEST, frmCipEasyxjdf.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblSettings, 6, SpringLayout.SOUTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.EAST, lblSettings, -10, SpringLayout.EAST, frmCipEasyxjdf.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel, 0, SpringLayout.EAST, frmCipEasyxjdf.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 0, SpringLayout.NORTH, frmCipEasyxjdf.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 0, SpringLayout.WEST, frmCipEasyxjdf.getContentPane());
		frmCipEasyxjdf.getContentPane().setLayout(springLayout);
		lblSettings.setIcon(new ImageIcon(XJdfView.class.getResource("/org/cip4/tools/easyxjdf/gui/settings.png")));
		frmCipEasyxjdf.getContentPane().add(lblSettings);

		txtJobId = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, txtJobId, -6, SpringLayout.NORTH, lblJobId);
		springLayout.putConstraint(SpringLayout.WEST, txtJobId, 43, SpringLayout.EAST, lblJobId);
		txtJobId.setColumns(10);
		frmCipEasyxjdf.getContentPane().add(txtJobId);

		cmbAmount = new JComboBox();
		springLayout.putConstraint(SpringLayout.NORTH, lblAmount, 5, SpringLayout.NORTH, cmbAmount);
		springLayout.putConstraint(SpringLayout.NORTH, cmbAmount, 6, SpringLayout.SOUTH, txtJobId);
		springLayout.putConstraint(SpringLayout.WEST, cmbAmount, 88, SpringLayout.WEST, frmCipEasyxjdf.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, cmbAmount, -322, SpringLayout.EAST, frmCipEasyxjdf.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtJobId, 0, SpringLayout.EAST, cmbAmount);
		cmbAmount.setEditable(true);
		frmCipEasyxjdf.getContentPane().add(cmbAmount);

		JLabel lblCustomerId = new JLabel("Customer ID");
		springLayout.putConstraint(SpringLayout.NORTH, lblCustomerId, 5, SpringLayout.NORTH, cmbAmount);
		springLayout.putConstraint(SpringLayout.WEST, lblCustomerId, 46, SpringLayout.EAST, cmbAmount);
		frmCipEasyxjdf.getContentPane().add(lblCustomerId);

		cmbCustomerId = new JComboBox();
		springLayout.putConstraint(SpringLayout.NORTH, cmbCustomerId, 0, SpringLayout.NORTH, cmbAmount);
		springLayout.putConstraint(SpringLayout.WEST, cmbCustomerId, 312, SpringLayout.WEST, txtJobId);
		springLayout.putConstraint(SpringLayout.EAST, cmbCustomerId, 0, SpringLayout.EAST, lblSettings);
		cmbCustomerId.setEditable(true);
		frmCipEasyxjdf.getContentPane().add(cmbCustomerId);

		cmbMediaQuality = new JComboBox();
		springLayout.putConstraint(SpringLayout.NORTH, cmbMediaQuality, 6, SpringLayout.SOUTH, cmbAmount);
		springLayout.putConstraint(SpringLayout.WEST, cmbMediaQuality, 0, SpringLayout.WEST, txtJobId);
		springLayout.putConstraint(SpringLayout.EAST, cmbMediaQuality, 0, SpringLayout.EAST, txtJobId);
		cmbMediaQuality.setEditable(true);
		frmCipEasyxjdf.getContentPane().add(cmbMediaQuality);

		JLabel lblNumColors = new JLabel("NumColors / Pages");
		springLayout.putConstraint(SpringLayout.NORTH, lblNumColors, 5, SpringLayout.NORTH, cmbMediaQuality);
		springLayout.putConstraint(SpringLayout.WEST, lblNumColors, 0, SpringLayout.WEST, lblCustomerId);
		frmCipEasyxjdf.getContentPane().add(lblNumColors);

		cmbNumColors = new JComboBox();
		cmbNumColors.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				computePages();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, cmbNumColors, 0, SpringLayout.NORTH, cmbMediaQuality);
		springLayout.putConstraint(SpringLayout.WEST, cmbNumColors, 0, SpringLayout.WEST, cmbCustomerId);
		cmbNumColors.setModel(new DefaultComboBoxModel(new String[] { "", "1 0", "1 1", "4 0", "4 1", "4 4" }));
		frmCipEasyxjdf.getContentPane().add(cmbNumColors);

		cmbCatalogId = new JComboBox();
		springLayout.putConstraint(SpringLayout.NORTH, cmbCatalogId, 6, SpringLayout.SOUTH, cmbMediaQuality);
		springLayout.putConstraint(SpringLayout.WEST, cmbCatalogId, 0, SpringLayout.WEST, txtJobId);
		springLayout.putConstraint(SpringLayout.EAST, cmbCatalogId, 0, SpringLayout.EAST, txtJobId);
		cmbCatalogId.setEditable(true);
		frmCipEasyxjdf.getContentPane().add(cmbCatalogId);

		JLabel lblFinishedDimensions = new JLabel("FinishedD (mm)");
		springLayout.putConstraint(SpringLayout.NORTH, lblFinishedDimensions, 5, SpringLayout.NORTH, cmbCatalogId);
		springLayout.putConstraint(SpringLayout.WEST, lblFinishedDimensions, 0, SpringLayout.WEST, lblCustomerId);
		frmCipEasyxjdf.getContentPane().add(lblFinishedDimensions);

		cmbFinishedDim = new JComboBox();
		springLayout.putConstraint(SpringLayout.NORTH, cmbFinishedDim, 0, SpringLayout.NORTH, cmbCatalogId);
		springLayout.putConstraint(SpringLayout.WEST, cmbFinishedDim, 0, SpringLayout.WEST, cmbCustomerId);
		springLayout.putConstraint(SpringLayout.EAST, cmbFinishedDim, 0, SpringLayout.EAST, lblSettings);
		cmbFinishedDim.setToolTipText(" in millimeter");
		cmbFinishedDim.setEditable(true);
		frmCipEasyxjdf.getContentPane().add(cmbFinishedDim);

		JLabel lblContentData = new JLabel("Content Data");
		springLayout.putConstraint(SpringLayout.NORTH, lblContentData, 44, SpringLayout.SOUTH, lblCatalogId);
		springLayout.putConstraint(SpringLayout.WEST, lblContentData, 0, SpringLayout.WEST, lblMediaQuality);
		frmCipEasyxjdf.getContentPane().add(lblContentData);

		txtContentData = new JTextField();
		springLayout.putConstraint(SpringLayout.EAST, cmbNumColors, 0, SpringLayout.EAST, txtContentData);
		springLayout.putConstraint(SpringLayout.NORTH, txtContentData, -6, SpringLayout.NORTH, lblContentData);
		springLayout.putConstraint(SpringLayout.WEST, txtContentData, 6, SpringLayout.EAST, lblContentData);
		txtContentData.setEditable(false);
		txtContentData.setColumns(10);
		frmCipEasyxjdf.getContentPane().add(txtContentData);

		JButton btnContentData = new JButton("...");
		springLayout.putConstraint(SpringLayout.EAST, txtContentData, -15, SpringLayout.WEST, btnContentData);
		springLayout.putConstraint(SpringLayout.SOUTH, btnContentData, 0, SpringLayout.SOUTH, lblContentData);
		springLayout.putConstraint(SpringLayout.NORTH, btnContentData, -6, SpringLayout.NORTH, lblContentData);
		springLayout.putConstraint(SpringLayout.EAST, btnContentData, 0, SpringLayout.EAST, lblSettings);
		btnContentData.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				JFileChooser c = new JFileChooser();
				// Demonstrate "Open" dialog:
				int rVal = c.showOpenDialog(frmCipEasyxjdf);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					txtContentData.setText(c.getSelectedFile().getAbsolutePath());
					txtContentData.setToolTipText(txtContentData.getText());
					updateJobName();
				}
				if (rVal == JFileChooser.CANCEL_OPTION) {
					// nothing
				}
			}
		});
		frmCipEasyxjdf.getContentPane().add(btnContentData);

		JLabel lblJobName = new JLabel("Job Name");
		springLayout.putConstraint(SpringLayout.WEST, lblJobName, 0, SpringLayout.WEST, lblContentData);
		frmCipEasyxjdf.getContentPane().add(lblJobName);

		txtJobName = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, lblJobName, 6, SpringLayout.NORTH, txtJobName);
		springLayout.putConstraint(SpringLayout.NORTH, txtJobName, 6, SpringLayout.SOUTH, txtContentData);
		springLayout.putConstraint(SpringLayout.WEST, txtJobName, 0, SpringLayout.WEST, txtJobId);
		springLayout.putConstraint(SpringLayout.EAST, txtJobName, 0, SpringLayout.EAST, lblSettings);
		txtJobName.setColumns(10);
		frmCipEasyxjdf.getContentPane().add(txtJobName);

		JLabel lblInfo = new JLabel("");
		springLayout.putConstraint(SpringLayout.WEST, lblInfo, 0, SpringLayout.WEST, lblContentData);
		lblInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new InfoController(frmCipEasyxjdf).showView();
			}
		});
		lblInfo.setIcon(new ImageIcon(XJdfView.class.getResource("/org/cip4/tools/easyxjdf/gui/info.png")));
		frmCipEasyxjdf.getContentPane().add(lblInfo);

		chkReset = new JCheckBox("No form reset after submit");
		springLayout.putConstraint(SpringLayout.NORTH, chkReset, 0, SpringLayout.NORTH, lblInfo);
		springLayout.putConstraint(SpringLayout.WEST, chkReset, 0, SpringLayout.WEST, txtJobId);
		frmCipEasyxjdf.getContentPane().add(chkReset);

		JButton btnSaveAs = new JButton("Save As");
		springLayout.putConstraint(SpringLayout.SOUTH, lblInfo, 0, SpringLayout.SOUTH, btnSaveAs);
		springLayout.putConstraint(SpringLayout.WEST, btnSaveAs, 347, SpringLayout.WEST, frmCipEasyxjdf.getContentPane());
		btnSaveAs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				processSaveAs();
			}
		});
		frmCipEasyxjdf.getContentPane().add(btnSaveAs);
		frmCipEasyxjdf.getContentPane().add(lblNewLabel);
		frmCipEasyxjdf.getContentPane().add(lblMediaQuality);
		frmCipEasyxjdf.getContentPane().add(lblCatalogId);
		frmCipEasyxjdf.getContentPane().add(lblJobId);
		frmCipEasyxjdf.getContentPane().add(lblAmount);

		JButton btnSend = new JButton("Send");
		springLayout.putConstraint(SpringLayout.NORTH, btnSaveAs, 0, SpringLayout.NORTH, btnSend);
		springLayout.putConstraint(SpringLayout.EAST, btnSaveAs, -6, SpringLayout.WEST, btnSend);
		springLayout.putConstraint(SpringLayout.NORTH, btnSend, 20, SpringLayout.SOUTH, txtJobName);
		springLayout.putConstraint(SpringLayout.WEST, btnSend, 451, SpringLayout.WEST, frmCipEasyxjdf.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnSend, 0, SpringLayout.EAST, lblSettings);
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				processSend();
			}
		});
		frmCipEasyxjdf.getContentPane().setFocusTraversalPolicy(
				new FocusTraversalOnArray(new Component[] { txtJobId, cmbAmount, cmbCustomerId, cmbMediaQuality, cmbNumColors, cmbCatalogId, cmbFinishedDim, btnContentData, txtJobName, btnSaveAs,
						btnSend, txtContentData, lblSettings, lblJobId, lblAmount, lblCustomerId, lblMediaQuality, lblCatalogId, lblContentData, lblJobName, lblInfo, lblNewLabel, lblNumColors }));
		frmCipEasyxjdf.getContentPane().add(btnSend);

		txtPages = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, txtPages, 6, SpringLayout.SOUTH, cmbCustomerId);
		springLayout.putConstraint(SpringLayout.WEST, txtPages, 6, SpringLayout.EAST, cmbNumColors);
		txtPages.setHorizontalAlignment(SwingConstants.RIGHT);
		springLayout.putConstraint(SpringLayout.SOUTH, txtPages, 0, SpringLayout.SOUTH, cmbMediaQuality);
		springLayout.putConstraint(SpringLayout.EAST, txtPages, 0, SpringLayout.EAST, lblSettings);
		frmCipEasyxjdf.getContentPane().add(txtPages);
		txtPages.setColumns(10);
	}

	/**
	 * Show settings dialog.
	 */
	private void showSettingsDialog() {

		// show settings dialog and update settings
		SettingsModel tmp = new SettingsController(frmCipEasyxjdf).showView();

		if (tmp != null) {
			this.settingsModel = tmp;
		}

		// update suggestions
		loadSuggenstions();
	}

	/**
	 * Notify a list of SaveAsListners.
	 */
	private void processSaveAs() {

		// validate amount
		if (!validateAmount()) {

			// show info
			showMessage("Amount is not numeric.");

			// set focus
			cmbAmount.requestFocus();

			// return
			return;
		}

		// validate finished dimensions
		if (!validateAmount()) {

			// show info
			showMessage("FinishedDimensions has the wrong format..");

			// set focus
			cmbFinishedDim.requestFocus();

			// return
			return;
		}

		// save dialog
		JFileChooser c = new JFileChooser();
		c.setDialogTitle("Save as Package");
		c.addChoosableFileFilter(new FileNameExtensionFilter("XJDF Document (*.xjdf)", "xjdf"));
		c.addChoosableFileFilter(new FileNameExtensionFilter("XJDF Package (*.zip) (recommended)", "zip"));

		int rVal = c.showSaveDialog(frmCipEasyxjdf);
		if (rVal == JFileChooser.APPROVE_OPTION) {

			// get selected path
			String path = c.getSelectedFile().getAbsolutePath();

			if (new File(path).exists()) {

				String msg = String.format("The file '%s' already exists. Do you want to replace it?", path);
				int resultCode = JOptionPane.showConfirmDialog(frmCipEasyxjdf, "File already exists...", msg, JOptionPane.YES_NO_OPTION);

				if (resultCode == JOptionPane.NO_OPTION) {
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

				// reset view
				if (!chkReset.isSelected())
					resetView();

			} catch (Exception ex) {

				// process exception
				ErrorController.processException(frmCipEasyxjdf, ex);
			}

		}
	}

	/**
	 * Notify a list of SendListners.
	 */
	private void processSend() {

		// validate amount
		if (!validateAmount()) {
			return;
		}

		try {

			// create model object
			XJdfModel model = createModel();

			// notify all listeners
			for (XJdfSendEventListener l : xJdfSendListener) {
				l.notify(new XJdfSendEvent(model));
			}

			// reset view
			if (!chkReset.isSelected())
				resetView();

		} catch (ConnectionException cnEx) {

		} catch (Exception ex) {

			// process exception
			ErrorController.processException(frmCipEasyxjdf, ex);
		}
	}

	/**
	 * Validate amount.
	 */
	private boolean validateAmount() {

		boolean result = false;

		try {
			// validate
			if (!StringUtils.isEmpty(cmbAmount.getSelectedItem().toString())) {
				Integer.parseInt(cmbAmount.getSelectedItem().toString());
			}

			result = true;

		} catch (NumberFormatException nex) {

		}

		// return result
		return result;
	}

	/**
	 * Validate amount.
	 */
	private boolean validateFiishedDimensions() {

		boolean result = false;

		try {
			// validate
			Shape s = new Shape(cmbFinishedDim.getSelectedItem().toString());
			result = true;

		} catch (Exception nex) {

		}

		// return result
		return result;
	}

	/**
	 * Updates the JobNames value base on ContentData file path.
	 */
	private void updateJobName() {

		String contentData = txtContentData.getText();

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
	 * Compute and updates the pages.
	 */
	private void computePages() {

		if (txtPages == null) {
			return;
		}

		if (cmbNumColors.getSelectedItem() != null && !StringUtils.isEmpty(cmbNumColors.getSelectedItem().toString())) {

			// compute pages
			String[] numColors = cmbNumColors.getSelectedItem().toString().split(" ");

			if ("0".equals(numColors[0])) {
				txtPages.setText("1");

			} else if ("0".equals(numColors[1])) {
				txtPages.setText("1");

			} else {
				txtPages.setText("2");

			}
		} else {
			txtPages.setText("");
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
		if (cmbAmount.getSelectedItem() != null && !StringUtils.isEmpty(cmbAmount.getSelectedItem().toString())) {
			model.setAmount(Integer.parseInt(cmbAmount.getSelectedItem().toString()));
		}

		model.setRunList(txtContentData.getText());
		if (cmbCatalogId.getSelectedItem() != null && !StringUtils.isEmpty(cmbCatalogId.getSelectedItem().toString())) {
			model.setCatalogId(cmbCatalogId.getSelectedItem().toString());
		}

		if (cmbCustomerId.getSelectedItem() != null && !StringUtils.isEmpty(cmbCustomerId.getSelectedItem().toString())) {
			model.setCustomerId(cmbCustomerId.getSelectedItem().toString());
		}

		if (cmbFinishedDim.getSelectedItem() != null && !StringUtils.isEmpty(cmbFinishedDim.getSelectedItem().toString())) {
			model.setFinishedDimensions(new Shape(cmbFinishedDim.getSelectedItem().toString()));
		}

		if (cmbMediaQuality.getSelectedItem() != null && !StringUtils.isEmpty(cmbMediaQuality.getSelectedItem().toString())) {
			model.setMediaQuality(cmbMediaQuality.getSelectedItem().toString());
		}

		if (cmbNumColors.getSelectedItem() != null && !StringUtils.isEmpty(cmbNumColors.getSelectedItem().toString())) {
			model.setNumColors(cmbNumColors.getSelectedItem().toString());
		}

		model.setJobName(txtJobName.getText());

		if (StringUtils.isNumeric(txtPages.getText()) && !StringUtils.isEmpty(txtPages.getText())) {
			int pages = Integer.parseInt(txtPages.getText());
			model.setPages(pages);
		} else {
			model.setPages(0);
		}

		// return result
		return model;
	}

	/**
	 * Load field suggestions
	 */
	private void loadSuggenstions() {

		// get old values
		XJdfModel xJdfModelOld = createModel();

		// display Amounts
		Integer[] amounts = settingsModel.getAmounts().toArray(new Integer[settingsModel.getAmounts().size()]);
		String[] strAmounts = new String[amounts.length];

		for (int i = 0; i < amounts.length; i++)
			strAmounts[i] = amounts[i].toString();

		cmbAmount.setModel(new DefaultComboBoxModel(strAmounts));

		// display MediaQualities
		String[] mediaQualities = settingsModel.getMediaQualities().toArray(new String[settingsModel.getMediaQualities().size()]);
		cmbMediaQuality.setModel(new DefaultComboBoxModel(mediaQualities));

		// display CustomerIDs
		String[] customerIDs = settingsModel.getCustomerIDs().toArray(new String[settingsModel.getCustomerIDs().size()]);
		cmbCustomerId.setModel(new DefaultComboBoxModel(customerIDs));

		// display CatalogIDs
		String[] catalogIDs = settingsModel.getCatalogIDs().toArray(new String[settingsModel.getCatalogIDs().size()]);
		cmbCatalogId.setModel(new DefaultComboBoxModel(catalogIDs));

		// display FinishedDims
		String[] finishedDimensions = settingsModel.getFinishedDimensions().toArray(new String[settingsModel.getFinishedDimensions().size()]);
		cmbFinishedDim.setModel(new DefaultComboBoxModel(finishedDimensions));

		// set old values
		if (xJdfModelOld.getAmount() != 0)
			cmbAmount.setSelectedItem(Integer.toString(xJdfModelOld.getAmount()));
		cmbMediaQuality.setSelectedItem(xJdfModelOld.getMediaQuality());
		cmbCustomerId.setSelectedItem(xJdfModelOld.getCustomerId());
		cmbCatalogId.setSelectedItem(xJdfModelOld.getCatalogId());
		if (xJdfModelOld.getFinishedDimensions() != null)
			cmbFinishedDim.setSelectedItem(xJdfModelOld.getFinishedDimensions().toString());
		cmbNumColors.setSelectedItem(xJdfModelOld.getNumColors());
	}

	/**
	 * Reset the view.
	 */
	private void resetView() {
		txtJobId.setText("");
		txtJobName.setText("");
		txtContentData.setText("");
		cmbAmount.setSelectedItem("");
		cmbMediaQuality.setSelectedItem("");
		cmbCatalogId.setSelectedItem("");
		cmbCustomerId.setSelectedItem("");
		cmbNumColors.setSelectedItem("");
		cmbFinishedDim.setSelectedItem("");

		oldJobName = "";
		computePages();
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
