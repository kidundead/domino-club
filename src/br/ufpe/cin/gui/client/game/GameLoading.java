package br.ufpe.cin.gui.client.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GameLoading extends JFrame {
	
	public GameLoading() {
		setUndecorated(true);
		setSize(377, 286);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setContentPane(new GameLoadingPanel());
		setVisible(true);
	}
	
}

class GameLoadingPanel extends JPanel {
	
	Image background;
	
	public GameLoadingPanel() {
		setLayout(new BorderLayout(0, 0));
		JLabel lblLoading = new JLabel("Esperando outros jogadores...");
		lblLoading.setFont(new Font("Arial", Font.PLAIN, 20));
		lblLoading.setHorizontalAlignment(SwingConstants.HORIZONTAL);
		lblLoading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		lblLoading.setForeground(Color.white);
		add(lblLoading, BorderLayout.SOUTH);
		ImageIcon ii = new ImageIcon(this.getClass().getResource("/images/splashscreen.jpg"));
		background = ii.getImage();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(background, 0, 0, this);
		paintComponents(g);

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
}
