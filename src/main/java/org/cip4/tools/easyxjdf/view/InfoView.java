package org.cip4.tools.easyxjdf.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

import org.cip4.tools.easyxjdf.model.InfoModel;

public class InfoView {

	private final InfoModel infoModel;

	private final JFrame parent;

	private JFrame frmInfoAboutCip;

	/**
	 * Custom constructor. Create the dialog.
	 * @param parent The parent dialog.
	 */
	public InfoView(JFrame parent, InfoModel infoModel) {

		// init members
		this.infoModel = infoModel;
		this.parent = parent;
	}

	/**
	 * Open the dialog.
	 * @wbp.parser.entryPoint
	 */
	public void open() {

		// init
		initialize();

		// show window
		frmInfoAboutCip.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInfoAboutCip = new JFrame();
		frmInfoAboutCip.setTitle("Info about CIP4 EasyXJDF…");
		frmInfoAboutCip.setResizable(false);
		frmInfoAboutCip.getContentPane().setBackground(Color.WHITE);
		frmInfoAboutCip.setBounds(100, 100, 450, 347);
		frmInfoAboutCip.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmInfoAboutCip.getContentPane().setLayout(springLayout);

		JLabel lblFlyeralarmLogo = new JLabel("");
		springLayout.putConstraint(SpringLayout.WEST, lblFlyeralarmLogo, 10, SpringLayout.WEST, frmInfoAboutCip.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblFlyeralarmLogo, -10, SpringLayout.SOUTH, frmInfoAboutCip.getContentPane());
		lblFlyeralarmLogo.setIcon(new ImageIcon(InfoView.class.getResource("/org/cip4/tools/easyxjdf/gui/fa-logo.png")));
		frmInfoAboutCip.getContentPane().add(lblFlyeralarmLogo);

		JLabel lblAuthor = new JLabel("Author:");
		lblAuthor.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		springLayout.putConstraint(SpringLayout.WEST, lblAuthor, 0, SpringLayout.WEST, lblFlyeralarmLogo);
		springLayout.putConstraint(SpringLayout.SOUTH, lblAuthor, -127, SpringLayout.NORTH, lblFlyeralarmLogo);
		frmInfoAboutCip.getContentPane().add(lblAuthor);

		JLabel lblStefanMeissner = new JLabel("Stefan Meissner");
		lblStefanMeissner.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		springLayout.putConstraint(SpringLayout.NORTH, lblStefanMeissner, 3, SpringLayout.SOUTH, lblAuthor);
		springLayout.putConstraint(SpringLayout.WEST, lblStefanMeissner, 0, SpringLayout.WEST, lblFlyeralarmLogo);
		springLayout.putConstraint(SpringLayout.SOUTH, lblStefanMeissner, -105, SpringLayout.NORTH, lblFlyeralarmLogo);
		frmInfoAboutCip.getContentPane().add(lblStefanMeissner);

		JLabel lblFlyeralarmGmbh = new JLabel("flyeralarm GmbH");
		lblFlyeralarmGmbh.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		springLayout.putConstraint(SpringLayout.NORTH, lblFlyeralarmGmbh, 20, SpringLayout.SOUTH, lblStefanMeissner);
		springLayout.putConstraint(SpringLayout.WEST, lblFlyeralarmGmbh, 0, SpringLayout.WEST, lblFlyeralarmLogo);
		springLayout.putConstraint(SpringLayout.SOUTH, lblFlyeralarmGmbh, -66, SpringLayout.NORTH, lblFlyeralarmLogo);
		frmInfoAboutCip.getContentPane().add(lblFlyeralarmGmbh);

		JLabel lblCip4Logo = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lblCip4Logo, 10, SpringLayout.NORTH, frmInfoAboutCip.getContentPane());
		lblCip4Logo.setIcon(new ImageIcon(InfoView.class.getResource("/org/cip4/tools/easyxjdf/gui/cip4-logo.png")));
		springLayout.putConstraint(SpringLayout.WEST, lblCip4Logo, 10, SpringLayout.WEST, frmInfoAboutCip.getContentPane());
		frmInfoAboutCip.getContentPane().add(lblCip4Logo);

		JLabel lblXJdfLogo = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lblXJdfLogo, 10, SpringLayout.NORTH, frmInfoAboutCip.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblXJdfLogo, -10, SpringLayout.EAST, frmInfoAboutCip.getContentPane());
		lblXJdfLogo.setIcon(new ImageIcon(InfoView.class.getResource("/org/cip4/tools/easyxjdf/gui/xjdf-logo-small.png")));
		frmInfoAboutCip.getContentPane().add(lblXJdfLogo);

		JLabel lblEasyxjdf = new JLabel("EasyXJDF");
		springLayout.putConstraint(SpringLayout.WEST, lblEasyxjdf, 31, SpringLayout.EAST, lblCip4Logo);
		springLayout.putConstraint(SpringLayout.SOUTH, lblEasyxjdf, -258, SpringLayout.SOUTH, frmInfoAboutCip.getContentPane());
		lblEasyxjdf.setFont(new Font("Lucida Grande", Font.ITALIC, 26));
		frmInfoAboutCip.getContentPane().add(lblEasyxjdf);

		JLabel lblVersion = new JLabel("Version: " + infoModel.getEasyXJdfVersion());
		springLayout.putConstraint(SpringLayout.WEST, lblVersion, 209, SpringLayout.WEST, frmInfoAboutCip.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblVersion, -10, SpringLayout.EAST, frmInfoAboutCip.getContentPane());
		lblVersion.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		frmInfoAboutCip.getContentPane().add(lblVersion);

		JLabel lblReleaseDate = new JLabel("Release Date: " + infoModel.getEasyXJdfBuildDate());
		springLayout.putConstraint(SpringLayout.NORTH, lblReleaseDate, 105, SpringLayout.NORTH, frmInfoAboutCip.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblReleaseDate, 209, SpringLayout.WEST, frmInfoAboutCip.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblReleaseDate, -10, SpringLayout.EAST, frmInfoAboutCip.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblVersion, -6, SpringLayout.NORTH, lblReleaseDate);
		lblReleaseDate.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		frmInfoAboutCip.getContentPane().add(lblReleaseDate);

		JLabel lblXJdfLib = new JLabel("CIP4 xJdfLib " + infoModel.getxJdfLibVersion());
		springLayout.putConstraint(SpringLayout.WEST, lblXJdfLib, 100, SpringLayout.EAST, lblStefanMeissner);
		springLayout.putConstraint(SpringLayout.EAST, lblXJdfLib, -10, SpringLayout.EAST, frmInfoAboutCip.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblReleaseDate, -45, SpringLayout.NORTH, lblXJdfLib);
		springLayout.putConstraint(SpringLayout.NORTH, lblXJdfLib, 3, SpringLayout.NORTH, lblStefanMeissner);
		lblXJdfLib.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		frmInfoAboutCip.getContentPane().add(lblXJdfLib);

		JLabel lblXJdfLibDate = new JLabel("Release Date: " + infoModel.getxJdfLibBuildDate());
		springLayout.putConstraint(SpringLayout.EAST, lblXJdfLibDate, 0, SpringLayout.EAST, lblXJdfLogo);
		springLayout.putConstraint(SpringLayout.NORTH, lblXJdfLibDate, 183, SpringLayout.NORTH, frmInfoAboutCip.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblXJdfLibDate, 209, SpringLayout.WEST, frmInfoAboutCip.getContentPane());
		lblXJdfLibDate.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		frmInfoAboutCip.getContentPane().add(lblXJdfLibDate);

		JLabel lblXPrintTalk = new JLabel("CIP4 xPrintTalkLib " + infoModel.getPtkLibVersion());
		springLayout.putConstraint(SpringLayout.NORTH, lblXPrintTalk, 16, SpringLayout.SOUTH, lblXJdfLibDate);
		springLayout.putConstraint(SpringLayout.WEST, lblXPrintTalk, 109, SpringLayout.EAST, lblFlyeralarmGmbh);
		springLayout.putConstraint(SpringLayout.EAST, lblXPrintTalk, 0, SpringLayout.EAST, lblXJdfLogo);
		lblXPrintTalk.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		frmInfoAboutCip.getContentPane().add(lblXPrintTalk);

		JLabel lblXPrintTalkDate = new JLabel("Release Date: " + infoModel.getPtkLibBuildDate());
		springLayout.putConstraint(SpringLayout.NORTH, lblXPrintTalkDate, 6, SpringLayout.SOUTH, lblXPrintTalk);
		springLayout.putConstraint(SpringLayout.WEST, lblXPrintTalkDate, 209, SpringLayout.WEST, frmInfoAboutCip.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblXPrintTalkDate, 0, SpringLayout.EAST, lblXJdfLogo);
		lblXPrintTalkDate.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		frmInfoAboutCip.getContentPane().add(lblXPrintTalkDate);

		JButton btnClose = new JButton("Close");
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frmInfoAboutCip.dispose();
			}
		});
		springLayout.putConstraint(SpringLayout.SOUTH, btnClose, 0, SpringLayout.SOUTH, lblFlyeralarmLogo);
		springLayout.putConstraint(SpringLayout.EAST, btnClose, 0, SpringLayout.EAST, lblXJdfLogo);
		frmInfoAboutCip.getContentPane().add(btnClose);
	}

}
