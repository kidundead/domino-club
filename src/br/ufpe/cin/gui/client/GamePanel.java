package br.ufpe.cin.gui.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.ufpe.cin.main.ClientApp;
import br.ufpe.cin.model.Domino;
import br.ufpe.cin.model.Room;

public class GamePanel extends JPanel {

	private ArrayList<JButton> dominoes = new ArrayList<JButton>();
	private final int NUM_PLAYERS = 4;
	private ArrayList<ArrayList<JButton>> otherPlayersButtons;
	private JLabel[] playersNames = new JLabel[3];
	private JLabel scoreOurs, scoreTheirs;

	public GamePanel() {
		setLayout(new BorderLayout(0, 0));
		otherPlayersButtons = new ArrayList<ArrayList<JButton>>();
		for (int i = 0; i < 3; i++) {
			otherPlayersButtons.add(new ArrayList<JButton>());
			playersNames[i] = new JLabel("");
		}
		scoreOurs = new JLabel("0");
		scoreTheirs = new JLabel("0");

		buildPlayer();
		buildPlayerLeft();
		buildPlayerTop();
		buildPlayerRight();
	}

	public void blockDominoes() {
		for (JButton button : dominoes)
			button.setEnabled(false);
	}

	private void buildPlayer() {
		final Box horizontalBox = Box.createHorizontalBox();
		add(horizontalBox, BorderLayout.SOUTH);
		horizontalBox.add(scoreOurs);
		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue);
		JLabel playerName = new JLabel(ClientApp.getPlayerName());
		horizontalBox.add(playerName);
		for (int i = 0; i < 6; i++) {
			final JButton button = new JButton("");
			dominoes.add(button);
			button.setEnabled(false);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String str = button.getText();
					button.setVisible(false);
					dominoes.remove(button);
					int head1 = Integer.parseInt(str.replaceAll("\\s+", "")
							.substring(0, 1));
					int head2 = Integer.parseInt(str.replaceAll("\\s+", "")
							.substring(2, 3));
					String move = head1 + "|" + head2;
					ClientApp.removeDomino(head1, head2);
//					ClientApp.sendNewMove(move);
					blockDominoes();
					// for (int j = 0; j < dominoes.size(); j++) {
					// System.out.print(dominoes.get(j).getText() + " ");
					// }
					// System.out.println();
				}
			});
			horizontalBox.add(button);
		}
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue_1);
	}

	public void buildPlayerLeft() {
		Box verticalBox1 = Box.createVerticalBox();
		add(verticalBox1, BorderLayout.WEST);
		Component verticalGlue1_1 = Box.createVerticalGlue();
		verticalBox1.add(verticalGlue1_1);
		verticalBox1.add(playersNames[0]);
		for (int i = 0; i < 6; i++) {
			JButton button = new JButton("      ");
			button.setEnabled(false);
			verticalBox1.add(button);
			otherPlayersButtons.get(0).add(button);
		}
		Component verticalGlue1_2 = Box.createVerticalGlue();
		verticalBox1.add(verticalGlue1_2);
	}

	public void buildPlayerRight() {
		Box verticalBox2 = Box.createVerticalBox();
		add(verticalBox2, BorderLayout.EAST);
		Component verticalGlue2_1 = Box.createVerticalGlue();
		verticalBox2.add(verticalGlue2_1);
		verticalBox2.add(playersNames[2]);
		for (int i = 0; i < 6; i++) {
			JButton button = new JButton("      ");
			button.setEnabled(false);
			verticalBox2.add(button);
			otherPlayersButtons.get(2).add(button);
		}
		Component verticalGlue2_2 = Box.createVerticalGlue();
		verticalBox2.add(verticalGlue2_2);
	}

	public void buildPlayerTop() {
		Box horizontalBox2 = Box.createHorizontalBox();
		add(horizontalBox2, BorderLayout.NORTH);
		Component horizontalGlue2_1 = Box.createHorizontalGlue();
		horizontalBox2.add(horizontalGlue2_1);
		horizontalBox2.add(playersNames[1]);
		for (int i = 0; i < 6; i++) {
			JButton button = new JButton("      ");
			button.setEnabled(false);
			horizontalBox2.add(button);
			otherPlayersButtons.get(1).add(button);
		}
		Component horizontalGlue2_2 = Box.createHorizontalGlue();
		horizontalBox2.add(horizontalGlue2_2);
		horizontalBox2.add(scoreTheirs);
	}

	public void draw() {
		JOptionPane.showMessageDialog(this, "Empate na contagem dos pontos!!");
	}

	public void endOfGame(boolean victory) {
		String message = "";
		if (victory) {
			message = "Parabéns! Sua dupla venceu o jogo!";
		} else {
			message = "Sinto muito! Sua dupla perdeu o jogo...";
		}
		JOptionPane.showMessageDialog(this, message);
		System.exit(0);
	}

	public void newDomino(int head1, int head2) {
		int i = 0;
		while (i < 5 && dominoes.get(i).getText() != "")
			i++;
		dominoes.get(i).setText(head1 + " | " + head2);
	}

	public void newMove(int head1, int head2) {
		JButton button = new JButton(head1 + " | " + head2);
	}

	public void resetGame() {
		removeAll();
		dominoes.clear();
		buildPlayer();
		buildPlayerLeft();
		buildPlayerTop();
		buildPlayerRight();
	}

	public void setOtherPlayersDominoesAmount(String playersDominoesAmount_str) {
		String[] split = playersDominoesAmount_str.split(" ");
		for (int i = 0; i < 3; i++) {
			int playerDominoAmount = Integer.parseInt(split[i]);
			if (otherPlayersButtons.get(i).size() > playerDominoAmount) {
				int diff = otherPlayersButtons.get(i).size()
						- playerDominoAmount;
				while (diff > 0) {
					otherPlayersButtons.get(i).get(0).setVisible(false);
					otherPlayersButtons.get(i).remove(0);
					diff--;
				}
			}
		}
	}

	public void setScore(int scoreOurs, int scoreTheirs) {
		this.scoreOurs.setText(scoreOurs + "");
		this.scoreTheirs.setText(scoreTheirs + "");
	}

	private void showCongratulations() {

	}

	public void showWinner(int roundWinner, int playerNumber, boolean pointsCounting) {
		String msg;
		if (playerNumber == roundWinner)
			msg = "Você ";
		else
			msg = "Jogador " + roundWinner + " ";
		if (pointsCounting)
			msg = msg + " ganhou na contagem de pontos!!";
		else
			msg = msg + " bateu!!";
		JOptionPane.showMessageDialog(this, msg);
	}

	public void updateGame(Room room) {
		// Pegar o número do cara
		int playerNumber = 0;
		for (int i = 0; i < 4; i++) {
			if (room.getPlayer(i).getID().equals(ClientApp.getPlayer().getID())) {
				playerNumber = i;
			}
		}

		if (room.getResetBoard()) {
			if (!room.wasLastTimeDraw()) {
				if (room.getLastRoundWinner() != playerNumber)
					showWinner(room.getLastRoundWinner(), playerNumber, false);
				else
					showCongratulations();
			} else {
				if (room.getLastRoundWinner() == -1)
					draw();
				else
					showWinner(room.getLastRoundWinner(), playerNumber, true);
			}
			resetGame();
		}

		if (!room.hasGameStarted()) {
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
			for (int i = 0; i < 3; i++) {
				playersNames[i].setText(players_names.split(" ")[i]);
			}
		}

		// Atualiza as próprias pedras
		int x = 0;
		for (Domino domino : room.getPlayer(playerNumber).getDominos()) {
			dominoes.get(x).setText(
					domino.getHead1() + "  |  " + domino.getHead2());
			x++;
		}

		updateScores(playerNumber, room.getScoreA(), room.getScoreB());

		// Atualizar a quantidade de pedras dos jogadores
		int count = 0;
		String otherPlayersDominoesAmount = null;
		for (int i = playerNumber; count < 4; i++) {
			if (i != playerNumber) {
				if (otherPlayersDominoesAmount != null)
					otherPlayersDominoesAmount = otherPlayersDominoesAmount
							+ " " + room.getPlayer(i).getDominoesAmount();
				else
					otherPlayersDominoesAmount = room.getPlayer(i)
							.getDominoesAmount() + "";
			}
			if (i + 1 == 4)
				i = -1;
			count++;
		}
		x = 0;
		for (String dominoes_amount : otherPlayersDominoesAmount.split(" ")) {
			int buttons = otherPlayersButtons.get(x).size();
			int dominoes_t = Integer.parseInt(dominoes_amount);
			if (buttons > dominoes_t) {
				int diff = buttons - dominoes_t;
				while (diff > 0) {
					otherPlayersButtons.get(x).get(0).setVisible(false);
					otherPlayersButtons.get(x).remove(0);
					diff--;
				}
			}
			x++;
		}

		// Desbloqueia as peças se for sua vez
		if (room.getCurrentPlayer() == playerNumber) {
			yourTurn(room.isFirstMove(), room.getBoardHead1(),
					room.getBoardHead2());
		}
	}

	public void updateScores(int playerNumber, int scoreA, int scoreB) {
		if (playerNumber % 2 == 0) {
			scoreOurs.setText(scoreA + "");
			scoreTheirs.setText(scoreB + "");
		} else {
			scoreOurs.setText(scoreB + "");
			scoreTheirs.setText(scoreA + "");
		}
	}

	public void yourTurn(boolean firstMove, int boardHead1, int boardHead2) {

		int firstDouble = 0;

		if (firstMove) {
			for (int i = 0; i < dominoes.size(); i++)
				if (ClientApp.getPlayer().getDomino(i).isDouble()
						&& ClientApp.getPlayer().getDomino(i).getHead1() > firstDouble)
					firstDouble = ClientApp.getPlayer().getDomino(i).getHead1();

			for (int i = 0; i < dominoes.size(); i++)
				if (ClientApp.getPlayer().getDomino(i).getHead1() == firstDouble
						&& ClientApp.getPlayer().getDomino(i).getHead2() == firstDouble)
					dominoes.get(i).setEnabled(true);
		} else {
			if (boardHead1 == -1 && boardHead2 == -1) {
				for (int i = 0; i < dominoes.size(); i++)
					dominoes.get(i).setEnabled(true);
			} else {
				for (int i = 0; i < dominoes.size(); i++)
					if (ClientApp.getPlayer().getDomino(i).getHead1() == boardHead1
							|| ClientApp.getPlayer().getDomino(i).getHead1() == boardHead2
							|| ClientApp.getPlayer().getDomino(i).getHead2() == boardHead1
							|| ClientApp.getPlayer().getDomino(i).getHead2() == boardHead2) {
						dominoes.get(i).setEnabled(true);
					}
			}
		}
	}
}