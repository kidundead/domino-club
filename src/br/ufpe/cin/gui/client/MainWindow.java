package br.ufpe.cin.gui.client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import br.ufpe.cin.controller.ClientEngine;

public class MainWindow extends JFrame {

	public JButton btnOK;
	private final ClientEngine engine;
	private JLabel lblStatus;
	public JTextField txtHostname;
	public JTextField txtPlayerName;

	public MainWindow(ClientEngine _engine) {
		engine = _engine;
		setSize(480, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(new EmptyBorder(0, 10, 0, 10));
		getContentPane().add(titlePanel, BorderLayout.NORTH);
		titlePanel.setLayout(new BorderLayout(0, 0));

		JLabel lblTitle = new JLabel("Domino Club");
		lblTitle.setFont(new Font("Dialog", Font.BOLD, 20));
		lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
		titlePanel.add(lblTitle);

		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 0, 30));
		getContentPane().add(mainPanel);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWeights = new double[] { 0.0, 1.0 };
		gbl_mainPanel.rowHeights = new int[] { 20, 40, 20 };
		mainPanel.setLayout(gbl_mainPanel);

		JLabel lblHostname = new JLabel("Host Address");
		GridBagConstraints gbc_lblHostname = new GridBagConstraints();
		gbc_lblHostname.anchor = GridBagConstraints.EAST;
		gbc_lblHostname.insets = new Insets(0, 0, 5, 5);
		gbc_lblHostname.gridx = 0;
		gbc_lblHostname.gridy = 0;
		mainPanel.add(lblHostname, gbc_lblHostname);

		txtHostname = new JTextField("localhost");
		GridBagConstraints gbc_txtHostname = new GridBagConstraints();
		gbc_txtHostname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtHostname.insets = new Insets(0, 0, 5, 0);
		gbc_txtHostname.gridx = 1;
		gbc_txtHostname.gridy = 0;
		mainPanel.add(txtHostname, gbc_txtHostname);
		txtHostname.setColumns(15);

		JLabel lblPlayerName = new JLabel("Player Name");
		GridBagConstraints gbc_lblPlayerName = new GridBagConstraints();
		gbc_lblPlayerName.anchor = GridBagConstraints.EAST;
		gbc_lblPlayerName.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlayerName.gridx = 0;
		gbc_lblPlayerName.gridy = 1;
		mainPanel.add(lblPlayerName, gbc_lblPlayerName);

		txtPlayerName = new JTextField();
		GridBagConstraints gbc_txtPlayerName = new GridBagConstraints();
		gbc_txtPlayerName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPlayerName.insets = new Insets(0, 0, 5, 0);
		gbc_txtPlayerName.gridx = 1;
		gbc_txtPlayerName.gridy = 1;
		mainPanel.add(txtPlayerName, gbc_txtPlayerName);
		txtPlayerName.setColumns(15);

		btnOK = new JButton("Done");
		GridBagConstraints gbc_btnOK = new GridBagConstraints();
		gbc_btnOK.anchor = GridBagConstraints.EAST;
		gbc_btnOK.gridx = 1;
		gbc_btnOK.gridy = 2;
		mainPanel.add(btnOK, gbc_btnOK);

		JPanel statusPanel = new JPanel();
		FlowLayout fl_statusPanel = (FlowLayout) statusPanel.getLayout();
		fl_statusPanel.setAlignment(FlowLayout.LEFT);
		getContentPane().add(statusPanel, BorderLayout.SOUTH);

		lblStatus = new JLabel("");
		lblStatus.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(lblStatus);

		setVisible(true);
	}

	public String getHostname() {
		return txtHostname.getText();
	}

	public String getPlayerName() {
		return txtPlayerName.getText();
	}

	public void setStatus(String msg) {
		lblStatus.setText(msg);
	}
}