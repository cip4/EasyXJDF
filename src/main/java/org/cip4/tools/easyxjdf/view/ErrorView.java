package org.cip4.tools.easyxjdf.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import org.apache.commons.lang.StringUtils;
import org.cip4.tools.easyxjdf.model.ErrorModel;
import org.eclipse.wb.swing.FocusTraversalOnArray;

public class ErrorView {

	private JFrame frmError;

	private final ErrorModel errorModel;

	private final Component parent;

	/**
	 * Custom constructor. Creation of the dialog.
	 * @param parent The parent Component
	 * @param errorModel The error object for initializing.
	 */
	public ErrorView(Component parent, ErrorModel errorModel) {

		this.errorModel = errorModel;
		this.parent = parent;
	}

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {

		// create content
		initialize();

		// show frame
		frmError.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Border border = BorderFactory.createLineBorder(Color.BLACK);

		frmError = new JFrame();
		frmError.setResizable(false);
		frmError.getContentPane().setBackground(Color.WHITE);
		frmError.setBackground(Color.WHITE);
		frmError.setTitle("Sorry, an error has been occured...");
		frmError.setBounds(100, 100, 529, 500);
		frmError.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmError.getContentPane().setLayout(springLayout);

		JLabel lblMessage = new JLabel("Message:");
		lblMessage.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		springLayout.putConstraint(SpringLayout.NORTH, lblMessage, 67, SpringLayout.NORTH, frmError.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblMessage, 10, SpringLayout.WEST, frmError.getContentPane());
		frmError.getContentPane().add(lblMessage);

		JLabel lblStackTrace = new JLabel("Stack Trace:");
		springLayout.putConstraint(SpringLayout.NORTH, lblStackTrace, 137, SpringLayout.SOUTH, lblMessage);
		springLayout.putConstraint(SpringLayout.WEST, lblStackTrace, 0, SpringLayout.WEST, lblMessage);
		lblStackTrace.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		frmError.getContentPane().add(lblStackTrace);

		JTextArea txtMessage = new JTextArea();
		txtMessage.setLineWrap(true);
		txtMessage.setEditable(false);
		springLayout.putConstraint(SpringLayout.NORTH, txtMessage, 6, SpringLayout.SOUTH, lblMessage);
		springLayout.putConstraint(SpringLayout.WEST, txtMessage, 10, SpringLayout.WEST, frmError.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, txtMessage, 111, SpringLayout.SOUTH, lblMessage);
		springLayout.putConstraint(SpringLayout.EAST, txtMessage, 519, SpringLayout.WEST, frmError.getContentPane());
		txtMessage.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtMessage.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		if (!StringUtils.isEmpty(errorModel.getMessage()))
			txtMessage.setText(errorModel.getMessage());
		frmError.getContentPane().add(txtMessage);

		JTextArea txtStackTrace = new JTextArea();
		txtStackTrace.setLineWrap(true);
		txtStackTrace.setEditable(false);
		txtStackTrace.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtStackTrace.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		if (!StringUtils.isEmpty(errorModel.getStackTrace()))
			txtStackTrace.setText(errorModel.getStackTrace());

		JScrollPane scrollStackTace = new JScrollPane(txtStackTrace);
		springLayout.putConstraint(SpringLayout.NORTH, scrollStackTace, 6, SpringLayout.SOUTH, lblStackTrace);
		springLayout.putConstraint(SpringLayout.WEST, scrollStackTace, 10, SpringLayout.WEST, frmError.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollStackTace, 185, SpringLayout.SOUTH, lblStackTrace);
		springLayout.putConstraint(SpringLayout.EAST, scrollStackTace, 0, SpringLayout.EAST, txtMessage);
		scrollStackTace.setAutoscrolls(true);
		frmError.getContentPane().add(scrollStackTace);

		JButton btnClose = new JButton("Close");
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frmError.dispose();
			}
		});
		springLayout.putConstraint(SpringLayout.WEST, btnClose, -110, SpringLayout.EAST, frmError.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnClose, -10, SpringLayout.SOUTH, frmError.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnClose, -10, SpringLayout.EAST, frmError.getContentPane());
		frmError.getContentPane().add(btnClose);

		JLabel lblXJdfLogo = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lblXJdfLogo, 10, SpringLayout.NORTH, frmError.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblXJdfLogo, 0, SpringLayout.EAST, txtMessage);
		lblXJdfLogo.setIcon(new ImageIcon(ErrorView.class.getResource("/org/cip4/tools/easyxjdf/gui/xjdf-logo-small.png")));
		frmError.getContentPane().add(lblXJdfLogo);

		JLabel lblCip4Logo = new JLabel("");
		lblCip4Logo.setIcon(new ImageIcon(ErrorView.class.getResource("/org/cip4/tools/easyxjdf/gui/cip4-logo-small.png")));
		springLayout.putConstraint(SpringLayout.NORTH, lblCip4Logo, 0, SpringLayout.NORTH, btnClose);
		springLayout.putConstraint(SpringLayout.WEST, lblCip4Logo, 0, SpringLayout.WEST, lblMessage);
		frmError.getContentPane().add(lblCip4Logo);

		JLabel lblNewLabel = new JLabel("Sorry, an error as been occured...");
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 49, SpringLayout.WEST, frmError.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel, -13, SpringLayout.NORTH, lblMessage);
		lblNewLabel.setIcon(new ImageIcon(ErrorView.class.getResource("/org/cip4/tools/easyxjdf/gui/error.png")));
		frmError.getContentPane().add(lblNewLabel);
		frmError.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { lblNewLabel, frmError.getContentPane(), lblMessage, lblStackTrace, txtMessage, txtStackTrace, btnClose,
				lblXJdfLogo, lblCip4Logo }));
	}
}
