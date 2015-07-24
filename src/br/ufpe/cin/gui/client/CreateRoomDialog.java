package br.ufpe.cin.gui.client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class CreateRoomDialog extends JDialog {

	public JButton btnOK;
	private JLabel lblStatus;
	private JTextField txtRoomName;

	public CreateRoomDialog() {
		setSize(360, 180);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(10, 10));

		JPanel titlePanel = new JPanel();
		getContentPane().add(titlePanel, BorderLayout.NORTH);
		titlePanel.setLayout(new BorderLayout(0, 0));
		titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		JLabel lblCriarSala = new JLabel("Criar sala");
		lblCriarSala.setFont(new Font("Dialog", Font.BOLD, 16));
		titlePanel.add(lblCriarSala);

		JPanel mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWeights = new double[] { 0.0, 1.0 };
		gbl_mainPanel.rowWeights = new double[] { 0.0, 0.0 };
		mainPanel.setLayout(gbl_mainPanel);

		JLabel lblRoomName = new JLabel("Nome da sala:");
		GridBagConstraints gbc_lblRoomName = new GridBagConstraints();
		gbc_lblRoomName.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoomName.anchor = GridBagConstraints.EAST;
		gbc_lblRoomName.gridx = 0;
		gbc_lblRoomName.gridy = 0;
		mainPanel.add(lblRoomName, gbc_lblRoomName);

		txtRoomName = new JTextField();
		GridBagConstraints gbc_txtRoomName = new GridBagConstraints();
		gbc_txtRoomName.insets = new Insets(0, 0, 5, 0);
		gbc_txtRoomName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRoomName.gridx = 1;
		gbc_txtRoomName.gridy = 0;
		mainPanel.add(txtRoomName, gbc_txtRoomName);
		txtRoomName.setColumns(10);

		btnOK = new JButton("Criar sala");
		GridBagConstraints gbc_btnOK = new GridBagConstraints();
		gbc_btnOK.anchor = GridBagConstraints.EAST;
		gbc_btnOK.gridx = 1;
		gbc_btnOK.gridy = 1;
		mainPanel.add(btnOK, gbc_btnOK);
		
		JPanel statusPanel = new JPanel();
		getContentPane().add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setLayout(new BorderLayout(0, 0));
		
		lblStatus = new JLabel("");
		statusPanel.add(lblStatus);
		
		setVisible(true);
	}
	
	public String getRoomName() {
		return txtRoomName.getText();
	}
	
	public void setStatus(String msg) {
		lblStatus.setText(msg);
	}

}
