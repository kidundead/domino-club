package br.ufpe.cin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Room implements Serializable {

	private final static int num_players = 4;
	private static final long serialVersionUID = 7356294313343194154L;
	private Domino[] allDominoes;
	private int currentPlayer, lastRoundWinner, boardHead1, boardHead2, scoreA,
			scoreB, touches;
	// Pra saber se é a primeira jogada do jogo inteiro
	private boolean firstMove;
	// Pra saber se há um jogo em andamento (jogo, não partida)
	private boolean hasGameStarted;
	private String ID, roomName, creatorID;
	private String lastData;
	// Pra saber se houve um empate na rodada anterior
	private boolean lastTimeWasDraw;
	private ArrayList<Domino> map;
	private ArrayList<Player> players;
	// Pra saber se o board foi resetado (entre partidas)
	private boolean resetBoard;

	public Room(String ID, String roomName, String playerID) {
		lastData = "";
		this.ID = ID;
		this.roomName = roomName;
		this.creatorID = playerID;
		players = new ArrayList<Player>();
		map = new ArrayList<Domino>();
		allDominoes = new Domino[28];
		firstMove = true;
		hasGameStarted = resetBoard = lastTimeWasDraw = false;
		currentPlayer = lastRoundWinner = boardHead1 = boardHead2 = -1;
		scoreA = scoreB = touches = 0;

		// Gera as 28 peças do jogo
		int count = 0, count2 = 0;
		for (int i = 0; i < 7; i++) {
			for (int j = count; j < 7; j++) {
				allDominoes[count2] = new Domino(i, j);
				count2++;
			}
			count++;
		}
		shuffleDominoes();
	}

	public void addPlayer(Player p) {
		players.add(p);
	}

	// Checa se o jogador da vez tem peça que encaixa no jogo
	public boolean currentPlayerHasDomino() {
		for (Domino d : players.get(currentPlayer).getDominos()) {
			if (d.getHead1() == getBoardHead1()
					|| d.getHead1() == getBoardHead2()
					|| d.getHead2() == getBoardHead1()
					|| d.getHead2() == getBoardHead2()) {
				return true;
			}
		}
		return false;
	}

	public int getAvailablePlayers() {
		return players.size();
	}

	public int getBoardHead1() {
		return boardHead1;
	}

	public int getBoardHead2() {
		return boardHead2;
	}

	public String getCreatorID() {
		return creatorID;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public String getID() {
		return ID;
	}

	public String getLastData() {
		return lastData;
	}

	public int getLastRoundWinner() {
		return lastRoundWinner;
	}

	public String getName() {
		return roomName;
	}

	public Player getPlayer(int i) {
		return players.get(i);
	}

	public Player getPlayer(String ID) {
		for (Player p : players)
			if (p.getID().equals(ID))
				return p;
		return null;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public boolean getResetBoard() {
		return resetBoard;
	}

	public int getScoreA() {
		return scoreA;
	}

	public int getScoreB() {
		return scoreB;
	}

	public int getTouchesAmount() {
		return touches;
	}

	public boolean hasGameStarted() {
		return hasGameStarted;
	}

	public boolean isFirstMove() {
		return firstMove;
	}

	public boolean isFull() {
		return (players.size() == num_players);
	}

	public void lastTimeWasDraw() {
		lastTimeWasDraw = true;
	}

	public void newMove(int head1, int head2, int side) {
		hasGameStarted = true;
		firstMove = false;
		resetBoard = false;
		lastTimeWasDraw = false;
		Domino domino = new Domino(head1, head2);

		// side == 1 -> lado esquerdo, boardHead1
		// side == 2 -> lado direito, boardHead2
		if (getBoardHead1() == -1) {
			setBoardHead1(head1);
			setBoardHead2(head2);
		} else if ((head1 == getBoardHead1() || head1 == getBoardHead2())
				&& (head2 == getBoardHead1() || head2 == getBoardHead2())) {
			if (side == 1) {
				if (head1 == getBoardHead1())
					setBoardHead1(head2);
				else if (head2 == getBoardHead1())
					setBoardHead1(head1);
				map.add(0, domino);
			} else if (side == 2) {
				if (head1 == getBoardHead2())
					setBoardHead2(head2);
				else if (head2 == getBoardHead2())
					setBoardHead2(head1);
				map.add(domino);
			}
		} else if (head1 == getBoardHead1() && head1 == getBoardHead2()) {
			if (side == 1) {
				setBoardHead1(head2);
				map.add(0, domino);
			} else if (side == 2) {
				setBoardHead2(head2);
				map.add(domino);
			}
		} else if (head2 == getBoardHead1() && head2 == getBoardHead2()) {
			if (side == 1) {
				setBoardHead1(head1);
				map.add(0, domino);
			} else if (side == 2) {
				setBoardHead2(head1);
				map.add(domino);
			}
		} else if (head1 == getBoardHead1()) {
			setBoardHead1(head2);
			map.add(0, domino);
		} else if (head1 == getBoardHead2()) {
			setBoardHead2(head2);
			map.add(domino);
		} else if (head2 == getBoardHead1()) {
			setBoardHead1(head1);
			map.add(0, domino);
		} else if (head2 == getBoardHead2()) {
			setBoardHead2(head1);
			map.add(domino);
		}

		players.get(currentPlayer).removeDomino(head1, head2);

		nextPlayer();

	}
	
	public void nextPlayer() {
		currentPlayer++;
		if (currentPlayer == 4)
			currentPlayer = 0;
	}

	public void playerWonRound(int player, int aditionalPoints) {
		lastRoundWinner = player;
		if (player % 2 == 0)
			scoreA = scoreA + 1 + aditionalPoints;
		else
			scoreB = scoreB + 1 + aditionalPoints;
		resetBoard();
	}

	public void removePlayer(String ID) {
		players.remove(ID);
	}

	public void resetBoard() {
		map = new ArrayList<Domino>();
		firstMove = false;
		resetBoard = true;
		boardHead1 = boardHead2 = -1;
		touches = 0;
		for (int i = 0; i < num_players; i++)
			players.get(i).removeAllDominoes();
		shuffleDominoes();
		int roundWinner = currentPlayer;
		setupGame();
		if (!isFirstMove())
			currentPlayer = lastRoundWinner;
	}

	public void resetLastRoundWinner() {
		lastRoundWinner = -1;
	}

	public void resetTouches() {
		touches = 0;
	}

	public void setBoardHead1(int boardHead1) {
		this.boardHead1 = boardHead1;
	}

	public void setBoardHead2(int boardHead2) {
		this.boardHead2 = boardHead2;
	}

	// Seta o primeiro jogador da partida
	public void setCurrentPlayer(int player) {
		currentPlayer = player;
	}

	public void setFirstMove() {
		firstMove = true;
	}

	public void setLastData(String data) {
		lastData = data;
	}

	public void setScoreA(int score) {
		scoreA = score;
	}

	public void setScoreB(int score) {
		scoreB = score;
	}

	public void setupGame() {

		/********************************************
		 * Distribuição das peças
		 *******************************************/
		int playerNumber = 0, firstDouble = 0;
		for (int i = 0; i < 24; i++) {

			// Descobre quem é o primeiro a jogar
			int domino = allDominoes[i].getHead1();
			if (allDominoes[i].isDouble()) {
				if (domino > firstDouble) {
					setCurrentPlayer(playerNumber);
					firstDouble = domino;
				}
			}

			players.get(playerNumber).addDomino(allDominoes[i]);

			if ((i + 1) % 6 == 0)
				playerNumber++;
		}
	}

	// Embaralha as pecas
	private void shuffleDominoes() {
		Collections.shuffle(Arrays.asList(allDominoes));
	}

	public void someoneTouched() {
		touches++;
	}

	public boolean wasLastTimeDraw() {
		return lastTimeWasDraw;
	}
}
