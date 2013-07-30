package org.cip4.tools.easyxjdf.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import org.cip4.tools.easyxjdf.model.InfoModel;

public class InfoDialog extends JDialog {

	private static final long serialVersionUID = 4243606892016435119L;

	private final JPanel contentPanel = new JPanel();

	private final InfoModel infoModel;
	private SpringLayout sl_contentPanel;
	private JLabel lblXJdfLogo;
	private JLabel lblFlyeralarmLogo;
	private JLabel lblVersion;
	private JLabel lblXJdfLib;
	private JLabel lblXJdfLibDate;
	private JLabel lblXPrintTalk;
	private JLabel lblXPrintTalkDate;

	/**
	 * Create the dialog.
	 * @wbp.parser.constructor
	 */
	public InfoDialog(JFrame parent, InfoModel infoModel) {

		super(parent, true);
		setResizable(false);

		this.infoModel = infoModel;

		setBounds(100, 100, 450, 347);
		getContentPane().setLayout(new BorderLayout());
		setTitle("Info about CIP4 EasyXJDF…");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);
		setLocationRelativeTo(parent);
		{
			initialize(sl_contentPanel);
		}
		{
			JButton closeButton = new JButton("Close");
			sl_contentPanel.putConstraint(SpringLayout.SOUTH, lblXPrintTalkDate, -36, SpringLayout.NORTH, closeButton);
			sl_contentPanel.putConstraint(SpringLayout.SOUTH, lblXJdfLibDate, -87, SpringLayout.NORTH, closeButton);
			sl_contentPanel.putConstraint(SpringLayout.SOUTH, closeButton, 0, SpringLayout.SOUTH, lblFlyeralarmLogo);
			sl_contentPanel.putConstraint(SpringLayout.EAST, closeButton, 0, SpringLayout.EAST, lblXJdfLogo);
			contentPanel.add(closeButton);
			closeButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					dispose();
				}
			});
			closeButton.setActionCommand("Close");
		}
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
	private void initialize(SpringLayout springLayout) {
		contentPanel.setLayout(springLayout);

		lblFlyeralarmLogo = new JLabel("");
		springLayout.putConstraint(SpringLayout.WEST, lblFlyeralarmLogo, 10, SpringLayout.WEST, contentPanel);
		springLayout.putConstraint(SpringLayout.SOUTH, lblFlyeralarmLogo, -10, SpringLayout.SOUTH, contentPanel);
		lblFlyeralarmLogo.setIcon(new ImageIcon(InfoDialog.class.getResource("/org/cip4/tools/easyxjdf/gui/fa-logo.png")));
		contentPanel.add(lblFlyeralarmLogo);

		JLabel lblAuthor = new JLabel("Author:");
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblAuthor, 0, SpringLayout.WEST, lblFlyeralarmLogo);
		lblAuthor.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		contentPanel.add(lblAuthor);

		JLabel lblStefanMeissner = new JLabel("Stefan Meissner");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblStefanMeissner, 153, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, lblAuthor, -6, SpringLayout.NORTH, lblStefanMeissner);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblStefanMeissner, 0, SpringLayout.WEST, lblFlyeralarmLogo);
		lblStefanMeissner.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		contentPanel.add(lblStefanMeissner);

		JLabel lblFlyeralarmGmbh = new JLabel("flyeralarm GmbH");
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, lblStefanMeissner, -6, SpringLayout.NORTH, lblFlyeralarmGmbh);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblFlyeralarmGmbh, 178, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, lblFlyeralarmGmbh, -39, SpringLayout.NORTH, lblFlyeralarmLogo);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblFlyeralarmGmbh, 0, SpringLayout.WEST, lblFlyeralarmLogo);
		lblFlyeralarmGmbh.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		contentPanel.add(lblFlyeralarmGmbh);

		JLabel lblCip4Logo = new JLabel("");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblCip4Logo, 0, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblCip4Logo, 0, SpringLayout.WEST, contentPanel);
		lblCip4Logo.setIcon(new ImageIcon(InfoDialog.class.getResource("/org/cip4/tools/easyxjdf/gui/cip4-logo.png")));
		contentPanel.add(lblCip4Logo);

		lblXJdfLogo = new JLabel("");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblXJdfLogo, 10, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblXJdfLogo, -10, SpringLayout.EAST, contentPanel);
		lblXJdfLogo.setIcon(new ImageIcon(InfoDialog.class.getResource("/org/cip4/tools/easyxjdf/gui/xjdf-logo-small.png")));
		contentPanel.add(lblXJdfLogo);

		JLabel lblEasyxjdf = new JLabel("EasyXJDF");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblEasyxjdf, 25, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblEasyxjdf, 48, SpringLayout.EAST, lblCip4Logo);
		lblEasyxjdf.setFont(new Font("Lucida Grande", Font.ITALIC, 26));
		contentPanel.add(lblEasyxjdf);

		lblVersion = new JLabel("Version: " + infoModel.getEasyXJdfVersion());
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblVersion, 45, SpringLayout.SOUTH, lblEasyxjdf);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblVersion, 209, SpringLayout.WEST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblVersion, -15, SpringLayout.EAST, contentPanel);
		lblVersion.setFont(new Font("Lucida Grande", Font.BOLD, 11));
		contentPanel.add(lblVersion);

		JLabel lblReleaseDate = new JLabel("Release Date: " + infoModel.getEasyXJdfBuildDate());
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblReleaseDate, 25, SpringLayout.SOUTH, lblEasyxjdf);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblReleaseDate, 209, SpringLayout.WEST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, lblReleaseDate, -11, SpringLayout.NORTH, lblVersion);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblReleaseDate, -15, SpringLayout.EAST, contentPanel);
		lblReleaseDate.setFont(new Font("Lucida Grande", Font.BOLD, 11));
		contentPanel.add(lblReleaseDate);

		lblXJdfLib = new JLabel("CIP4 xJdfLib " + infoModel.getxJdfLibVersion());
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblXJdfLib, 39, SpringLayout.SOUTH, lblVersion);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblXJdfLib, 0, SpringLayout.WEST, lblVersion);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblXJdfLib, -10, SpringLayout.EAST, contentPanel);
		lblXJdfLib.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		contentPanel.add(lblXJdfLib);

		lblXJdfLibDate = new JLabel("Release Date: " + infoModel.getxJdfLibBuildDate());
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, lblXJdfLib, -7, SpringLayout.NORTH, lblXJdfLibDate);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblXJdfLibDate, 0, SpringLayout.NORTH, lblFlyeralarmGmbh);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblXJdfLibDate, 209, SpringLayout.WEST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblXJdfLibDate, 0, SpringLayout.EAST, lblXJdfLogo);
		lblXJdfLibDate.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		contentPanel.add(lblXJdfLibDate);

		lblXPrintTalk = new JLabel("CIP4 xPrintTalkLib " + infoModel.getPtkLibVersion());
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblXPrintTalk, 13, SpringLayout.SOUTH, lblXJdfLibDate);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblXPrintTalk, 0, SpringLayout.WEST, lblVersion);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, lblXPrintTalk, -92, SpringLayout.SOUTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblXPrintTalk, 0, SpringLayout.EAST, lblXJdfLogo);
		lblXPrintTalk.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		contentPanel.add(lblXPrintTalk);

		lblXPrintTalkDate = new JLabel("Release Date: " + infoModel.getPtkLibBuildDate());
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblXPrintTalkDate, 29, SpringLayout.SOUTH, lblXJdfLibDate);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblXPrintTalkDate, 0, SpringLayout.WEST, lblVersion);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblXPrintTalkDate, -10, SpringLayout.EAST, contentPanel);
		lblXPrintTalkDate.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		contentPanel.add(lblXPrintTalkDate);
	}
}
