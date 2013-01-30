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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.cip4.tools.easyxjdf.event.SendEvent;
import org.cip4.tools.easyxjdf.event.SendEventListener;
import org.cip4.tools.easyxjdf.model.SendModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * Send XJDF View Class.
 * @author s.meissner
 * @date 30.01.2013
 */
public class SendView extends Dialog {

	private final List<SendEventListener> sendListener;

	protected Object result;

	protected Shell shell;

	private Text txtTargetUrl;

	private Combo cmbSystemType;

	private Button chkDefault;

	private Label lblLogo;

	private final String oldUrl = "";

	private final Map<String, String> mapUrls;

	private final Map<String, String> mapLogo;

	/**
	 * Create the dialog.
	 * @param parent The parent view.
	 */
	public SendView(Shell parent) {

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
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
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
		shell = new Shell(getParent(), getStyle());
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shell.setSize(438, 265);
		shell.setText(getText());

		cmbSystemType = new Combo(shell, SWT.NONE);
		cmbSystemType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {

				Combo cmb = (Combo) event.getSource();
				updateUrl(cmb.getText());
			}
		});
		cmbSystemType.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		cmbSystemType.setItems(mapUrls.keySet().toArray(new String[mapUrls.size()]));
		cmbSystemType.setBounds(90, 27, 156, 29);
		cmbSystemType.select(0);

		Label lblSytem = new Label(shell, SWT.NONE);
		lblSytem.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblSytem.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblSytem.setBounds(10, 30, 84, 21);
		lblSytem.setText("Sytem:");

		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(10, 77, 408, 2);

		Label lblUrl = new Label(shell, SWT.NONE);
		lblUrl.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblUrl.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblUrl.setBounds(10, 105, 32, 21);
		lblUrl.setText("URL:");

		txtTargetUrl = new Text(shell, SWT.BORDER);
		txtTargetUrl.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		txtTargetUrl.setBounds(90, 102, 328, 27);

		chkDefault = new Button(shell, SWT.CHECK);
		chkDefault.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		chkDefault.setBounds(89, 144, 283, 21);
		chkDefault.setText("Always use this URL");
		chkDefault.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		Button btnSend = new Button(shell, SWT.NONE);
		btnSend.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				processSend();
			}
		});
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				processSend();
			}
		});
		btnSend.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnSend.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnSend.setBounds(334, 189, 84, 31);
		btnSend.setText("Send");

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
		btnCancel.setBounds(237, 189, 84, 31);

		lblLogo = new Label(shell, SWT.NONE);
		lblLogo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblLogo.setBounds(252, 27, 166, 29);
	}

	/**
	 * Append listener for SaveAs Event.
	 * @param saveAsListener XJdfListener to append to.
	 */
	public void addSendListener(SendEventListener sendListener) {
		this.sendListener.add(sendListener);
	}

	private void processSend() {

		try {

			// create model object
			SendModel model = createModel();

			// notify all listeners
			for (SendEventListener l : sendListener) {
				l.notify(new SendEvent(model));
			}

			// close window
			shell.close();

		} catch (Exception ex) {

			// process exception
			ErrorController.processException(shell, ex);
		}

	}

	/**
	 * Create new model object by form content.
	 * @return Model object which contains form details.
	 */
	private SendModel createModel() {

		// create model object
		SendModel model = new SendModel();

		// fill attributes
		model.setSystemType(cmbSystemType.getText());
		model.setTargetUrl(txtTargetUrl.getText());
		model.setDefault(chkDefault.getSelection());

		// return result
		return model;
	}

	/**
	 * Update URL Textfield
	 */
	private void updateUrl(String system) {

		InputStream isLogo = SendView.class.getResourceAsStream(mapLogo.get(system));
		Image imgLogo = new Image(shell.getDisplay(), isLogo);

		int width = imgLogo.getImageData().width;
		int height = imgLogo.getImageData().height;

		double scaleFactor = 30d / height;

		imgLogo = new Image(shell.getDisplay(), imgLogo.getImageData().scaledTo((int) (width * scaleFactor), (int) (height * scaleFactor)));

		lblLogo.setImage(imgLogo);

		String url = mapUrls.get(system);
		txtTargetUrl.setText(url);

	}
}
