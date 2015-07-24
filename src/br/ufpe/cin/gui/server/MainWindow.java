package br.ufpe.cin.gui.server;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class MainWindow extends JFrame {
	private JTextPane txtLog;

	public MainWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0};
		getContentPane().setLayout(gridBagLayout);

		txtLog = new JTextPane();
		MutableAttributeSet set = new SimpleAttributeSet(txtLog.getParagraphAttributes());
		StyleConstants.setLineSpacing(set, 0.3f);
		txtLog.setParagraphAttributes(set, true);
		GridBagConstraints gbc_txtLog = new GridBagConstraints();
		gbc_txtLog.fill = GridBagConstraints.BOTH;
		gbc_txtLog.insets = new Insets(10, 10, 10, 10);
		gbc_txtLog.gridx = 0;
		gbc_txtLog.gridy = 0;
		getContentPane().add(new JScrollPane(txtLog), gbc_txtLog);
		txtLog.setEditable(false);

		JButton btnResetLog = new JButton("Reset Log");
		GridBagConstraints gbc_btnResetLog = new GridBagConstraints();
		gbc_btnResetLog.insets = new Insets(0, 10, 10, 10);
		gbc_btnResetLog.anchor = GridBagConstraints.EAST;
		gbc_btnResetLog.gridx = 0;
		gbc_btnResetLog.gridy = 1;
		getContentPane().add(btnResetLog, gbc_btnResetLog);
		btnResetLog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				resetLog();
			}
		});

		setVisible(true);
	}

	public void logIt(String text) {
		txtLog.setText(text + '\n' + txtLog.getText());
	}

	public void resetLog() {
		txtLog.setText("");
	}
}