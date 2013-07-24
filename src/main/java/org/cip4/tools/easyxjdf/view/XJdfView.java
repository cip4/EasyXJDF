package org.cip4.tools.easyxjdf.view;

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
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
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

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

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

	private JFrame frame;

	private JTextField txtJobId;

	private JTextField txtJobName;

	private JTextField txtContentData;

	private JComboBox cmbAmount;

	private JComboBox cmbCustomerId;

	private JComboBox cmbMediaQuality;

	private JComboBox cmbCatalogId;

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
	 */
	public void open() {

		// create content
		initialize();
		loadSuggenstions();

		// show frame
		frame.setVisible(true);
	}

	/**
	 * Getter for frame attribute.
	 * @return the frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Show an info message in a MessageBox.
	 * @param message Message to show in a MessageBox.
	 */
	public void showMessage(String message) {

		// show message
		JOptionPane.showMessageDialog(frame, message);
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
		frame = new JFrame();
		frame.requestFocus();
		frame.setResizable(false);
		frame.setBounds(100, 100, 560, 388);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] { ColumnSpec.decode("1px"), ColumnSpec.decode("20px"), ColumnSpec.decode("86px"), FormFactory.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("161px"),
						ColumnSpec.decode("24px"), ColumnSpec.decode("79px"), FormFactory.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("108px"), FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						ColumnSpec.decode("51px"), }, new RowSpec[] { RowSpec.decode("1px"), RowSpec.decode("100px"), RowSpec.decode("24px"), FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("28px"),
						FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("27px"), FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("27px"), RowSpec.decode("17px"), RowSpec.decode("29px"),
						FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("28px"), RowSpec.decode("17px"), RowSpec.decode("29px"), }));

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(XJdfView.class.getResource("/org/cip4/tools/easyxjdf/gui/title.png")));
		frame.getContentPane().add(label, "2, 2, 10, 1, right, default");

		JLabel lblSettings = new JLabel("");
		lblSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				showSettingsDialog();
			}
		});
		lblSettings.setIcon(new ImageIcon(XJdfView.class.getResource("/org/cip4/tools/easyxjdf/gui/settings.png")));
		frame.getContentPane().add(lblSettings, "11, 3, right, center");

		JLabel lblJobId = new JLabel("Job ID");
		frame.getContentPane().add(lblJobId, "3, 5, left, center");

		txtJobId = new JTextField();
		frame.getContentPane().add(txtJobId, "5, 5, fill, center");
		txtJobId.setColumns(10);

		JLabel lblAmount = new JLabel("Amount");
		frame.getContentPane().add(lblAmount, "3, 7, left, center");

		cmbAmount = new JComboBox();
		cmbAmount.setEditable(true);
		frame.getContentPane().add(cmbAmount, "5, 7, fill, center");

		JLabel lblCustomerId = new JLabel("Customer ID");
		frame.getContentPane().add(lblCustomerId, "7, 7, left, center");

		cmbCustomerId = new JComboBox();
		cmbCustomerId.setEditable(true);
		frame.getContentPane().add(cmbCustomerId, "9, 7, 3, 1, fill, center");

		JLabel lblMediaQuality = new JLabel("Media Quality");
		frame.getContentPane().add(lblMediaQuality, "3, 9, left, center");

		cmbMediaQuality = new JComboBox();
		cmbMediaQuality.setEditable(true);
		frame.getContentPane().add(cmbMediaQuality, "5, 9, fill, center");

		JLabel lblCatalogId = new JLabel("Catalog ID");
		frame.getContentPane().add(lblCatalogId, "7, 9, left, center");

		cmbCatalogId = new JComboBox();
		cmbCatalogId.setEditable(true);
		frame.getContentPane().add(cmbCatalogId, "9, 9, 3, 1, fill, center");

		JLabel lblContentData = new JLabel("Content Data");
		frame.getContentPane().add(lblContentData, "3, 11, left, fill");

		txtContentData = new JTextField();
		txtContentData.setEditable(false);
		frame.getContentPane().add(txtContentData, "5, 11, 5, 1, fill, center");
		txtContentData.setColumns(10);

		JButton btnContentData = new JButton("...");
		btnContentData.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				JFileChooser c = new JFileChooser();
				// Demonstrate "Open" dialog:
				int rVal = c.showOpenDialog(frame);
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
		frame.getContentPane().add(btnContentData, "11, 11, center, center");

		JLabel lblJobName = new JLabel("Job Name");
		frame.getContentPane().add(lblJobName, "3, 13, left, center");

		txtJobName = new JTextField();
		frame.getContentPane().add(txtJobName, "5, 13, 7, 1, fill, center");
		txtJobName.setColumns(10);

		JLabel lblInfo = new JLabel("");
		lblInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				new InfoController(frame).showView();
			}
		});
		lblInfo.setIcon(new ImageIcon(XJdfView.class.getResource("/org/cip4/tools/easyxjdf/gui/info.png")));
		frame.getContentPane().add(lblInfo, "3, 15, left, center");

		JButton btnSaveAs = new JButton("Save As");
		btnSaveAs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				processSaveAs();
			}
		});
		frame.getContentPane().add(btnSaveAs, "9, 15, left, center");

		JButton btnSend = new JButton("Send");
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				processSend();
			}
		});
		frame.getContentPane().add(btnSend, "10, 15, 2, 1, right, center");
	}

	/**
	 * Show settings dialog.
	 */
	private void showSettingsDialog() {

		// show settings dialog and update settings
		SettingsModel tmp = new SettingsController(frame).showView();

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

		// save dialog
		JFileChooser c = new JFileChooser();
		c.setDialogTitle("Save as Package");
		c.addChoosableFileFilter(new FileNameExtensionFilter("XJDF Document (*.xjdf)", "xjdf"));
		c.addChoosableFileFilter(new FileNameExtensionFilter("XJDF Package (*.zip) (recommended)", "zip"));

		int rVal = c.showSaveDialog(frame);
		if (rVal == JFileChooser.APPROVE_OPTION) {

			// get selected path
			String path = c.getSelectedFile().getAbsolutePath();

			if (new File(path).exists()) {

				String msg = String.format("The file '%s' already exists. Do you want to replace it?", path);
				int resultCode = JOptionPane.showConfirmDialog(frame, "File already exists...", msg, JOptionPane.YES_NO_OPTION);

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
				ErrorController.processException(frame, ex);
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
			ErrorController.processException(frame, ex);
		}
	}

	/**
	 * Validate amount.
	 */
	private boolean validateAmount() {

		boolean result = false;

		try {
			// validate
			if (!StringUtils.isEmpty(cmbAmount.getEditor().getItem().toString())) {
				Integer.parseInt(cmbAmount.getEditor().getItem().toString());
			}

			result = true;

		} catch (NumberFormatException nex) {

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
		if (!StringUtils.isEmpty(cmbAmount.getEditor().getItem().toString()))
			model.setAmount(Integer.parseInt(cmbAmount.getEditor().getItem().toString()));
		model.setRunList(txtContentData.getText());
		model.setCatalogId(cmbCatalogId.getEditor().getItem().toString());
		model.setCustomerId(cmbCustomerId.getEditor().getItem().toString());
		model.setMediaQuality(cmbMediaQuality.getEditor().getItem().toString());
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

		// display CustomerIDs
		String[] catalogIDs = settingsModel.getCatalogIDs().toArray(new String[settingsModel.getCatalogIDs().size()]);
		cmbCatalogId.setModel(new DefaultComboBoxModel(catalogIDs));

		// set old values
		if (xJdfModelOld.getAmount() != 0)
			cmbAmount.getEditor().setItem(Integer.toString(xJdfModelOld.getAmount()));
		cmbMediaQuality.getEditor().setItem(xJdfModelOld.getMediaQuality());
		cmbCustomerId.getEditor().setItem(xJdfModelOld.getCustomerId());
		cmbCatalogId.getEditor().setItem(xJdfModelOld.getCatalogId());
	}

	/**
	 * Reset the view.
	 */
	private void resetView() {
		txtJobId.setText("");
		txtJobName.setText("");
		txtContentData.setText("");
		cmbAmount.getEditor().setItem("");
		cmbMediaQuality.getEditor().setItem("");
		cmbCatalogId.getEditor().setItem("");
		cmbCustomerId.getEditor().setItem("");

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
