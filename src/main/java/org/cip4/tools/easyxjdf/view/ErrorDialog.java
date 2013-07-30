package org.cip4.tools.easyxjdf.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import org.apache.commons.lang.StringUtils;
import org.cip4.tools.easyxjdf.model.ErrorModel;
import org.eclipse.wb.swing.FocusTraversalOnArray;

public class ErrorDialog extends JDialog {

	private static final long serialVersionUID = -4770272557945902375L;

	private final ErrorModel errorModel;

	public ErrorDialog(Dialog parent, ErrorModel errorModel) {

		super(parent, true);
		this.errorModel = errorModel;

		// init
		initialize();
		setLocationRelativeTo(parent);
	}

	/**
	 * Create the dialog.
	 * @wbp.parser.constructor
	 */
	public ErrorDialog(JFrame parent, ErrorModel errorModel) {

		super(parent, true);
		this.errorModel = errorModel;

		// init
		initialize();
		setLocationRelativeTo(parent);
	}

	/**
	 * Show dialog.
	 */
	public void showDialog() {
		setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setBackground(Color.WHITE);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		setBackground(Color.WHITE);
		setTitle("Sorry, an error has been occured...");
		setBounds(100, 100, 529, 500);
		setResizable(false);

		Border border = BorderFactory.createLineBorder(Color.BLACK);

		JLabel lblMessage = new JLabel("Message:");
		lblMessage.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		springLayout.putConstraint(SpringLayout.NORTH, lblMessage, 67, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblMessage, 10, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblMessage);

		JLabel lblStackTrace = new JLabel("Stack Trace:");
		springLayout.putConstraint(SpringLayout.NORTH, lblStackTrace, 137, SpringLayout.SOUTH, lblMessage);
		springLayout.putConstraint(SpringLayout.WEST, lblStackTrace, 0, SpringLayout.WEST, lblMessage);
		lblStackTrace.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		getContentPane().add(lblStackTrace);

		JTextArea txtMessage = new JTextArea();
		txtMessage.setLineWrap(true);
		txtMessage.setEditable(false);
		springLayout.putConstraint(SpringLayout.NORTH, txtMessage, 6, SpringLayout.SOUTH, lblMessage);
		springLayout.putConstraint(SpringLayout.WEST, txtMessage, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, txtMessage, 111, SpringLayout.SOUTH, lblMessage);
		springLayout.putConstraint(SpringLayout.EAST, txtMessage, 519, SpringLayout.WEST, getContentPane());
		txtMessage.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtMessage.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		if (!StringUtils.isEmpty(errorModel.getMessage()))
			txtMessage.setText(errorModel.getMessage());
		getContentPane().add(txtMessage);

		JTextArea txtStackTrace = new JTextArea();
		txtStackTrace.setLineWrap(true);
		txtStackTrace.setEditable(false);
		txtStackTrace.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtStackTrace.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		if (!StringUtils.isEmpty(errorModel.getStackTrace()))
			txtStackTrace.setText(errorModel.getStackTrace());

		JScrollPane scrollStackTace = new JScrollPane(txtStackTrace);
		springLayout.putConstraint(SpringLayout.NORTH, scrollStackTace, 6, SpringLayout.SOUTH, lblStackTrace);
		springLayout.putConstraint(SpringLayout.WEST, scrollStackTace, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollStackTace, 185, SpringLayout.SOUTH, lblStackTrace);
		springLayout.putConstraint(SpringLayout.EAST, scrollStackTace, 0, SpringLayout.EAST, txtMessage);
		scrollStackTace.setAutoscrolls(true);
		getContentPane().add(scrollStackTace);

		JButton btnClose = new JButton("Close");
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}
		});
		springLayout.putConstraint(SpringLayout.WEST, btnClose, -110, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnClose, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnClose, -10, SpringLayout.EAST, getContentPane());
		getContentPane().add(btnClose);

		JLabel lblXJdfLogo = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lblXJdfLogo, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblXJdfLogo, 0, SpringLayout.EAST, txtMessage);
		lblXJdfLogo.setIcon(new ImageIcon(ErrorDialog.class.getResource("/org/cip4/tools/easyxjdf/gui/xjdf-logo-small.png")));
		getContentPane().add(lblXJdfLogo);

		JLabel lblCip4Logo = new JLabel("");
		lblCip4Logo.setIcon(new ImageIcon(ErrorDialog.class.getResource("/org/cip4/tools/easyxjdf/gui/cip4-logo-small.png")));
		springLayout.putConstraint(SpringLayout.NORTH, lblCip4Logo, 0, SpringLayout.NORTH, btnClose);
		springLayout.putConstraint(SpringLayout.WEST, lblCip4Logo, 0, SpringLayout.WEST, lblMessage);
		getContentPane().add(lblCip4Logo);

		JLabel lblNewLabel = new JLabel("Sorry, an error as been occured...");
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 49, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel, -13, SpringLayout.NORTH, lblMessage);
		lblNewLabel.setIcon(new ImageIcon(ErrorDialog.class.getResource("/org/cip4/tools/easyxjdf/gui/error.png")));
		getContentPane().add(lblNewLabel);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { lblNewLabel, getContentPane(), lblMessage, lblStackTrace, txtMessage, txtStackTrace, btnClose, lblXJdfLogo, lblCip4Logo }));
	}
}
