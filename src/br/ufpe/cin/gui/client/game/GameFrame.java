package br.ufpe.cin.gui.client.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import br.ufpe.cin.controller.ClientEngine;
import br.ufpe.cin.main.ClientApp;
import br.ufpe.cin.model.Room;

public class GameFrame extends JFrame {

	public class AL_door implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			WindowListener exitListener = new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					int confirm = JOptionPane.showOptionDialog(null,
							"Tem certeza que deseja sair do jogo?",
							"Confirmacao de saida", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, null, null);
					if (confirm == 0) {
						System.exit(0);
					}
				}
			};
			addWindowListener(exitListener);
			exitListener.windowClosing(null);
		}
	}

	public class AL_engine implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Clicou na engranagem");
			gameBoard.grabFocus();
		}
	}

	public class AL_question implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			gameBoard.showHelp = !gameBoard.showHelp;
			gameBoard.grabFocus();
		}

	}

	JButton btnDoor;
	JButton btnEngine;
	JButton btnQuestion;
	private ClientEngine engine;

	Font fontPlayers;

	GameBoard gameBoard;
	JLabel labelLeft;
	JLabel labelLocal;

	JLabel labelRight;

	JLabel labelTop;

	private int playerNumber;

	public GameFrame(ClientEngine clientEngine) {

		engine = clientEngine;
		setLayout(new BorderLayout(0, 0));

		//this.setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setSize(1200, 650);
		setSize(1203, 678);
		setLocationRelativeTo(null);
		setTitle("Domino Club");
		setResizable(false);
		// setVisible(true);
	}

	public void updateGame(Room room) {

		if (!room.hasGameStarted()) {
			// Pegar o número do cara
			playerNumber = 0;
			for (int i = 0; i < 4; i++) {
				if (room.getPlayer(i).getID()
						.equals(ClientApp.getPlayer().getID())) {
					playerNumber = i;
				}
			}

			gameBoard = new GameBoard(engine, room, playerNumber);
			gameBoard.setLayout(null);

			// Dar nome aos outros
			int count = 0;
			String players_names = null;
			for (int i = playerNumber; count < 4; i++) {
				if (playerNumber != i) {
					if (players_names != null)
						players_names = players_names + " "
								+ room.getPlayer(i).getName();
					else
						players_names = room.getPlayer(i).getName();
				}
				if (i + 1 == 4)
					i = -1;
				count++;
			}
			
			fontPlayers = new Font("Arial", Font.PLAIN, 15);
			
			labelLeft = new JLabel(players_names.split(" ")[0]);
			labelLeft.setBounds(83, 422, 150, 20);
			labelLeft.setFont(fontPlayers);
			labelLeft.setHorizontalAlignment(SwingConstants.LEFT);
			labelLeft.setForeground(Color.WHITE);			

			labelTop = new JLabel(players_names.split(" ")[1]);
			labelTop.setBounds(416, 60, 150, 20);
			labelTop.setFont(fontPlayers);
			labelTop.setHorizontalAlignment(SwingConstants.LEFT);
			labelTop.setForeground(Color.WHITE);

			labelRight = new JLabel(players_names.split(" ")[2]);
			labelRight.setBounds(1077, 422, 150, 20);
			labelRight.setFont(fontPlayers);
			labelRight.setHorizontalAlignment(SwingConstants.LEFT);
			labelRight.setForeground(Color.WHITE);

			labelLocal = new JLabel(ClientApp.getPlayer().getName());
			labelLocal.setBounds(371, 558, 150, 20);
			labelLocal.setFont(fontPlayers);
			labelLocal.setHorizontalAlignment(SwingConstants.LEFT);
			labelLocal.setForeground(Color.WHITE);

			BufferedImage buttonIcon1 = null;
			BufferedImage buttonIcon2 = null;
			BufferedImage buttonIcon3 = null;

			try {
				buttonIcon1 = ImageIO.read(this.getClass().getResource(
						"/images/icons/open-door.png"));
				buttonIcon2 = ImageIO.read(this.getClass().getResource(
						"/images/icons/engine.png"));
				buttonIcon3 = ImageIO.read(this.getClass().getResource(
						"/images/icons/question.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			btnDoor = new JButton(new ImageIcon(buttonIcon1));
			btnDoor.setBorder(BorderFactory.createEmptyBorder());
			btnDoor.setBounds(1120, 580, 20, 32);
			btnDoor.setBackground(new Color(0, 0, 0, 0));
			btnDoor.setCursor(new Cursor(Cursor.HAND_CURSOR));
			btnDoor.addActionListener(new AL_door());

//			btnEngine = new JButton(new ImageIcon(buttonIcon2));
//			btnEngine.setBorder(BorderFactory.createEmptyBorder());
//			btnEngine.setBounds(1070, 580, 27, 27);
//			btnEngine.setBackground(new Color(0, 0, 0, 0));
//			btnEngine.setCursor(new Cursor(Cursor.HAND_CURSOR));
//			btnEngine.addActionListener(new AL_engine());

			btnQuestion = new JButton(new ImageIcon(buttonIcon3));
			btnQuestion.setBorder(BorderFactory.createEmptyBorder());
			btnQuestion.setBounds(1070, 580, 14, 25);
			btnQuestion.setBackground(new Color(0, 0, 0, 0));
			btnQuestion.setCursor(new Cursor(Cursor.HAND_CURSOR));
			btnQuestion.addActionListener(new AL_question());

			gameBoard.add(btnDoor);
//			gameBoard.add(btnEngine);
			gameBoard.add(btnQuestion);

			gameBoard.add(labelTop);
			gameBoard.add(labelLeft);
			gameBoard.add(labelRight);
			gameBoard.add(labelLocal);

			add(gameBoard, BorderLayout.CENTER);
		}

		gameBoard.updateGame(room, playerNumber);
	}
}
