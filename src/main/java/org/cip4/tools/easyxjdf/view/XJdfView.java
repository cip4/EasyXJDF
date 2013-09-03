package org.cip4.tools.easyxjdf.view;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
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
		frmCipEasyxjdf.setBounds(100, 100, 560, 413);
		frmCipEasyxjdf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmCipEasyxjdf.getContentPane().setLayout(springLayout);

		JLabel lblSettings = new JLabel("");
		springLayout.putConstraint(SpringLayout.EAST, lblSettings, -10, SpringLayout.EAST, frmCipEasyxjdf.getContentPane());
		lblSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				showSettingsDialog();
			}
		});
		lblSettings.setIcon(new ImageIcon(XJdfView.class.getResource("/org/cip4/tools/easyxjdf/gui/settings.png")));
		frmCipEasyxjdf.getContentPane().add(lblSettings);

		JLabel lblJobId = new JLabel("Job ID");
		frmCipEasyxjdf.getContentPane().add(lblJobId);

		txtJobId = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, txtJobId, -2, SpringLayout.NORTH, lblJobId);
		springLayout.putConstraint(SpringLayout.WEST, txtJobId, 70, SpringLayout.EAST, lblJobId);
		springLayout.putConstraint(SpringLayout.EAST, txtJobId, -280, SpringLayout.EAST, frmCipEasyxjdf.getContentPane());
		frmCipEasyxjdf.getContentPane().add(txtJobId);
		txtJobId.setColumns(10);

		JLabel lblAmount = new JLabel("Amount");
		springLayout.putConstraint(SpringLayout.NORTH, lblAmount, 17, SpringLayout.SOUTH, lblJobId);
		springLayout.putConstraint(SpringLayout.WEST, lblJobId, 0, SpringLayout.WEST, lblAmount);
		springLayout.putConstraint(SpringLayout.WEST, lblAmount, 10, SpringLayout.WEST, frmCipEasyxjdf.getContentPane());
		frmCipEasyxjdf.getContentPane().add(lblAmount);

		cmbAmount = new JComboBox();
		springLayout.putConstraint(SpringLayout.NORTH, cmbAmount, -4, SpringLayout.NORTH, lblAmount);
		springLayout.putConstraint(SpringLayout.WEST, cmbAmount, 63, SpringLayout.EAST, lblAmount);
		cmbAmount.setEditable(true);
		frmCipEasyxjdf.getContentPane().add(cmbAmount);

		JLabel lblCustomerId = new JLabel("Customer ID");
		springLayout.putConstraint(SpringLayout.EAST, cmbAmount, -15, SpringLayout.WEST, lblCustomerId);
		springLayout.putConstraint(SpringLayout.NORTH, lblCustomerId, 0, SpringLayout.NORTH, lblAmount);
		frmCipEasyxjdf.getContentPane().add(lblCustomerId);

		cmbCustomerId = new JComboBox();
		springLayout.putConstraint(SpringLayout.NORTH, cmbCustomerId, -6, SpringLayout.NORTH, lblAmount);
		springLayout.putConstraint(SpringLayout.WEST, cmbCustomerId, 385, SpringLayout.WEST, frmCipEasyxjdf.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, cmbCustomerId, 0, SpringLayout.EAST, lblSettings);
		cmbCustomerId.setEditable(true);
		frmCipEasyxjdf.getContentPane().add(cmbCustomerId);

		JLabel lblMediaQuality = new JLabel("Media Quality");
		springLayout.putConstraint(SpringLayout.NORTH, lblMediaQuality, 16, SpringLayout.SOUTH, lblAmount);
		springLayout.putConstraint(SpringLayout.WEST, lblMediaQuality, 0, SpringLayout.WEST, lblJobId);
		frmCipEasyxjdf.getContentPane().add(lblMediaQuality);

		cmbMediaQuality = new JComboBox();
		springLayout.putConstraint(SpringLayout.NORTH, cmbMediaQuality, 6, SpringLayout.SOUTH, cmbAmount);
		springLayout.putConstraint(SpringLayout.WEST, cmbMediaQuality, 0, SpringLayout.WEST, txtJobId);
		springLayout.putConstraint(SpringLayout.EAST, cmbMediaQuality, -280, SpringLayout.EAST, frmCipEasyxjdf.getContentPane());
		cmbMediaQuality.setEditable(true);
		frmCipEasyxjdf.getContentPane().add(cmbMediaQuality);

		JLabel lblCatalogId = new JLabel("Catalog ID");
		springLayout.putConstraint(SpringLayout.WEST, lblCatalogId, 0, SpringLayout.WEST, lblJobId);
		frmCipEasyxjdf.getContentPane().add(lblCatalogId);

		cmbCatalogId = new JComboBox();
		springLayout.putConstraint(SpringLayout.NORTH, lblCatalogId, 5, SpringLayout.NORTH, cmbCatalogId);
		springLayout.putConstraint(SpringLayout.NORTH, cmbCatalogId, 6, SpringLayout.SOUTH, cmbMediaQuality);
		springLayout.putConstraint(SpringLayout.WEST, cmbCatalogId, -270, SpringLayout.WEST, cmbCustomerId);
		springLayout.putConstraint(SpringLayout.EAST, cmbCatalogId, 0, SpringLayout.EAST, txtJobId);
		cmbCatalogId.setEditable(true);
		frmCipEasyxjdf.getContentPane().add(cmbCatalogId);

		JLabel lblContentData = new JLabel("Content Data");
		springLayout.putConstraint(SpringLayout.WEST, lblContentData, 0, SpringLayout.WEST, lblJobId);
		frmCipEasyxjdf.getContentPane().add(lblContentData);

		txtContentData = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, lblContentData, 6, SpringLayout.NORTH, txtContentData);
		springLayout.putConstraint(SpringLayout.WEST, txtContentData, 115, SpringLayout.WEST, frmCipEasyxjdf.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtContentData, -54, SpringLayout.EAST, frmCipEasyxjdf.getContentPane());
		txtContentData.setEditable(false);
		frmCipEasyxjdf.getContentPane().add(txtContentData);
		txtContentData.setColumns(10);

		JButton btnContentData = new JButton("...");
		springLayout.putConstraint(SpringLayout.NORTH, btnContentData, 0, SpringLayout.NORTH, txtContentData);
		springLayout.putConstraint(SpringLayout.WEST, btnContentData, 6, SpringLayout.EAST, txtContentData);
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
		springLayout.putConstraint(SpringLayout.WEST, lblJobName, 0, SpringLayout.WEST, lblJobId);
		frmCipEasyxjdf.getContentPane().add(lblJobName);

		txtJobName = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, lblJobName, 6, SpringLayout.NORTH, txtJobName);
		springLayout.putConstraint(SpringLayout.SOUTH, txtContentData, -6, SpringLayout.NORTH, txtJobName);
		springLayout.putConstraint(SpringLayout.WEST, txtJobName, 0, SpringLayout.WEST, txtJobId);
		springLayout.putConstraint(SpringLayout.EAST, txtJobName, -4, SpringLayout.EAST, frmCipEasyxjdf.getContentPane());
		frmCipEasyxjdf.getContentPane().add(txtJobName);
		txtJobName.setColumns(10);

		JLabel lblInfo = new JLabel("");
		springLayout.putConstraint(SpringLayout.WEST, lblInfo, 0, SpringLayout.WEST, lblJobId);
		springLayout.putConstraint(SpringLayout.SOUTH, lblInfo, -10, SpringLayout.SOUTH, frmCipEasyxjdf.getContentPane());
		lblInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new InfoController(frmCipEasyxjdf).showView();
			}
		});
		lblInfo.setIcon(new ImageIcon(XJdfView.class.getResource("/org/cip4/tools/easyxjdf/gui/info.png")));
		frmCipEasyxjdf.getContentPane().add(lblInfo);

		JButton btnSaveAs = new JButton("Save As");
		springLayout.putConstraint(SpringLayout.WEST, btnSaveAs, 313, SpringLayout.EAST, lblInfo);
		springLayout.putConstraint(SpringLayout.SOUTH, txtJobName, -6, SpringLayout.NORTH, btnSaveAs);
		springLayout.putConstraint(SpringLayout.SOUTH, btnSaveAs, 0, SpringLayout.SOUTH, lblInfo);
		btnSaveAs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				processSaveAs();
			}
		});
		frmCipEasyxjdf.getContentPane().add(btnSaveAs);

		JButton btnSend = new JButton("Send");
		springLayout.putConstraint(SpringLayout.EAST, btnSaveAs, -6, SpringLayout.WEST, btnSend);
		springLayout.putConstraint(SpringLayout.WEST, btnSend, 450, SpringLayout.WEST, frmCipEasyxjdf.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnSend, -10, SpringLayout.EAST, frmCipEasyxjdf.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnSend, 0, SpringLayout.SOUTH, lblInfo);
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				processSend();
			}
		});
		frmCipEasyxjdf.getContentPane().add(btnSend);

		JLabel lblNewLabel = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lblJobId, 36, SpringLayout.SOUTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.NORTH, lblSettings, 1, SpringLayout.SOUTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 0, SpringLayout.NORTH, frmCipEasyxjdf.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 0, SpringLayout.WEST, frmCipEasyxjdf.getContentPane());
		lblNewLabel.setIcon(new ImageIcon(XJdfView.class.getResource("/org/cip4/tools/easyxjdf/gui/title.png")));
		frmCipEasyxjdf.getContentPane().add(lblNewLabel);

		cmbNumColors = new JComboBox();
		springLayout.putConstraint(SpringLayout.NORTH, cmbNumColors, -5, SpringLayout.NORTH, lblMediaQuality);
		springLayout.putConstraint(SpringLayout.WEST, cmbNumColors, 0, SpringLayout.WEST, cmbCustomerId);
		springLayout.putConstraint(SpringLayout.EAST, cmbNumColors, 0, SpringLayout.EAST, lblSettings);
		cmbNumColors.setModel(new DefaultComboBoxModel(new String[] { "", "1 0", "1 1", "4 0", "4 1", "4 4" }));
		frmCipEasyxjdf.getContentPane().add(cmbNumColors);

		JLabel lblNumColors = new JLabel("NumColors");
		springLayout.putConstraint(SpringLayout.WEST, lblCustomerId, 0, SpringLayout.WEST, lblNumColors);
		springLayout.putConstraint(SpringLayout.NORTH, lblNumColors, 0, SpringLayout.NORTH, lblMediaQuality);
		frmCipEasyxjdf.getContentPane().add(lblNumColors);

		JLabel lblFinishedDimensions = new JLabel("FinishedD (mm)");
		springLayout.putConstraint(SpringLayout.WEST, lblNumColors, 0, SpringLayout.WEST, lblFinishedDimensions);
		springLayout.putConstraint(SpringLayout.NORTH, lblFinishedDimensions, 0, SpringLayout.NORTH, lblCatalogId);
		frmCipEasyxjdf.getContentPane().add(lblFinishedDimensions);

		cmbFinishedDim = new JComboBox();
		springLayout.putConstraint(SpringLayout.EAST, lblFinishedDimensions, -6, SpringLayout.WEST, cmbFinishedDim);
		cmbFinishedDim.setToolTipText(" in millimeter");
		springLayout.putConstraint(SpringLayout.NORTH, cmbFinishedDim, 0, SpringLayout.NORTH, cmbCatalogId);
		springLayout.putConstraint(SpringLayout.WEST, cmbFinishedDim, 0, SpringLayout.WEST, cmbCustomerId);
		springLayout.putConstraint(SpringLayout.EAST, cmbFinishedDim, 0, SpringLayout.EAST, lblSettings);
		cmbFinishedDim.setEditable(true);
		frmCipEasyxjdf.getContentPane().add(cmbFinishedDim);
		frmCipEasyxjdf.getContentPane().setFocusTraversalPolicy(
				new FocusTraversalOnArray(new Component[] { txtJobId, cmbAmount, cmbCustomerId, cmbMediaQuality, cmbNumColors, cmbCatalogId, cmbFinishedDim, btnContentData, txtJobName, btnSaveAs,
						btnSend, txtContentData, lblSettings, lblJobId, lblAmount, lblCustomerId, lblMediaQuality, lblCatalogId, lblContentData, lblJobName, lblInfo, lblNewLabel, lblNumColors }));
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
