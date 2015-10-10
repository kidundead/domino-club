package br.ufpe.cin.gui.client.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import br.ufpe.cin.controller.ClientEngine;
import br.ufpe.cin.model.Room;

public class GameBoard extends JPanel implements ActionListener {

	private class TAdapter extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			organizer.keyReleased(e);
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int PLAYERLEFT = 1;
	private static final int PLAYERLOCAL = 0;
	private static final int PLAYERRIGHT = 3;
	
	private static final int PLAYERTOP = 2;
	
	private Image background;
	private Image blueOff;

	private Image blueOn;

	private ClientEngine clientEngine;
	private int glow;
	private Image glowLeft;
	private AffineTransform glowLeftAt;

	private Image glowLocal;
	private AffineTransform glowLocalAt;
	private Image glowRight;
	private AffineTransform glowRightAt;

	private Image glowTop;
	private AffineTransform glowTopAt;
	private Image help;
	private Image leftUser;

	private AffineTransform leftUserIconAt;
	private Image localUser;
	private AffineTransform localUserIconAt;
	private DominoOrganizer organizer;

	private Image redOff;
	private Image redOn;
	
	private Image rightUser;
	private AffineTransform rightUserIconAt;
	public boolean showHelp;
	private Timer timer;

	private Image topUser;

	private AffineTransform topUserIconAt;

	public GameBoard(ClientEngine clientEngine, Room room, int playerNumber) {
		this.clientEngine = clientEngine;
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);

		topUserIconAt = new AffineTransform();
		localUserIconAt = new AffineTransform();
		rightUserIconAt = new AffineTransform();
		leftUserIconAt = new AffineTransform();

		ImageIcon ii = new ImageIcon(this.getClass().getResource(
				"/images/icons/redon.png"));

		redOn = ii.getImage();

		ii = new ImageIcon(this.getClass().getResource(
				"/images/icons/redoff.png"));

		redOff = ii.getImage();
		
		topUser = redOn;
		localUser = redOn;

		ii = new ImageIcon(this.getClass().getResource(
				"/images/icons/blueon.png"));

		blueOn = ii.getImage();

		ii = new ImageIcon(this.getClass().getResource(
				"/images/icons/blueoff.png"));

		blueOff = ii.getImage();

		leftUser = blueOn;
		rightUser = blueOn;
		
		// posiciona icone de usu�rios
		topUserIconAt.translate(384, 52);

		localUserIconAt.translate(338, 551);

		rightUserIconAt.translate(1043, 415);

		leftUserIconAt.translate(42, 415);

		// Set glows image
		ii = new ImageIcon(this.getClass()
				.getResource("/images/glow-local.png"));
		glowLocal = ii.getImage();

		ii = new ImageIcon(this.getClass().getResource("/images/glow-top.png"));
		glowTop = ii.getImage();

		ii = new ImageIcon(this.getClass().getResource("/images/glow-left.png"));
		glowLeft = ii.getImage();

		ii = new ImageIcon(this.getClass()
				.getResource("/images/glow-right.png"));
		glowRight = ii.getImage();

		//Iniciar AT de glows
		glowLocalAt = new AffineTransform();
		glowTopAt = new AffineTransform();
		glowLeftAt = new AffineTransform();
		glowRightAt = new AffineTransform();

		
		glowLocalAt.translate((this.getWidth()/2) -(glowLocal.getWidth(null)/2) ,  this.getHeight() - glowLocal.getHeight(null)+20);
		glowLeftAt.translate(20, (this.getHeight()/2)-(glowLeft.getHeight(null)/2));
		glowRightAt.translate(1200-20-80, (this.getHeight()/2)-(glowRight.getHeight(null)/2)-20);
		glowTopAt.translate((this.getWidth()/2)-(glowTop.getWidth(null)/2) ,  20);
		
		ii = new ImageIcon(this.getClass().getResource("/images/bg-game.jpg"));
		background = ii.getImage();
		
		ii = new ImageIcon(this.getClass().getResource("/images/help.png"));
		help = ii.getImage();
		
		showHelp = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	public void draw() {
		JOptionPane.showMessageDialog(this, "Empate na contagem dos pontos!!");
	}

	private void endOfGame(boolean victory) {
		if (victory)
			JOptionPane.showMessageDialog(this, "Parabéns! Sua dupla venceu o jogo!");
		else
			JOptionPane.showMessageDialog(this, "Deixa pra próxima, meu primo!!");
		clientEngine.reset();
	}

	public void enviarPeca(int pecaValor1, int pecaValor2, int rotacao,
			int index, int head, int headValue) {
		System.out.println("M�todo enviarPeca(), classe GameBoard");
		System.out.println("Peca: " + pecaValor1 + pecaValor2);
		System.out.println("Rotacao: " + rotacao);
		System.out.println("index : " + index);
		System.out.println("HEAD: " + head);
		System.out.println("Valor HEAD: " + headValue);

		clientEngine.enviarPeca(pecaValor1, pecaValor2, rotacao, index, head,
				headValue);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(background, 0, 0, this);
		
		if(glow == PLAYERLOCAL){
			g2d.drawImage(glowLocal,(this.getWidth()/2)-(glowLocal.getWidth(null)/2) ,  this.getHeight() - glowLocal.getHeight(null)+20, this);
		}else if(glow == PLAYERLEFT){
			g2d.drawImage(glowLeft, 20, (this.getHeight()/2)-(glowLeft.getHeight(null)/2), this);
		}else if(glow == PLAYERRIGHT){
			g2d.drawImage(glowRight, 1200-20-80, (this.getHeight()/2)-(glowRight.getHeight(null)/2)-20, this);
		}else if(glow == PLAYERTOP){
			g2d.drawImage(glowTop,(this.getWidth()/2)-(glowTop.getWidth(null)/2) ,  20, this);
		}

//		g2d.drawImage(glowLocal,(this.getWidth()/2)-(glowLocal.getWidth(null)/2) ,  this.getHeight() - glowLocal.getHeight(null)+20, this);
//		g2d.drawImage(glowLeft, 20, (this.getHeight()/2)-(glowLeft.getHeight(null)/2), this);
//		g2d.drawImage(glowRight, 1200-20-80, (this.getHeight()/2)-(glowRight.getHeight(null)/2)-20, this);
//		g2d.drawImage(glowTop,(this.getWidth()/2)-(glowTop.getWidth(null)/2) ,  20, this);

		g2d.drawRenderedImage(organizer.ponteiroAzul, organizer.ponteiroAzulAt);
		g2d.drawRenderedImage(organizer.ponteiroVermelho,
				organizer.ponteiroVermelhoAt);

		for (int i = 0; i < organizer.jogadorLeft.length; i++) {
			// Renderiza pe�as dos jogadores nao locais

			if (organizer.jogadorLeft[i] != null)
				g2d.drawRenderedImage(organizer.jogadorLeft[i].bi,
						organizer.jogadorLeft[i].at);
			if (organizer.jogadorRight[i] != null)
				g2d.drawRenderedImage(organizer.jogadorRight[i].bi,
						organizer.jogadorRight[i].at);
			if (organizer.jogadorTop[i] != null)
				g2d.drawRenderedImage(organizer.jogadorTop[i].bi,
						organizer.jogadorTop[i].at);

			// Renderiza pe�as do jogador Local
			if (i < organizer.jogadorLocal.size())
				g2d.drawRenderedImage(organizer.jogadorLocal.get(i).bi,
						organizer.jogadorLocal.get(i).at);
		}

		for (int i = organizer.proximaPecaA; i <= organizer.proximaPecaB; i++) {
			if (organizer.pecasJogadas.get(i).getPeca() != null)
				g2d.drawRenderedImage(
						organizer.pecasJogadas.get(i).getPeca().bi,
						organizer.pecasJogadas.get(i).at);
		}

		// IMPRIMIR SQUARES
		g2d.drawImage(topUser, topUserIconAt, null);
		g2d.drawImage(leftUser, leftUserIconAt, null);
		g2d.drawImage(localUser, localUserIconAt, null);
		g2d.drawImage(rightUser, rightUserIconAt, null);

		if(showHelp) g2d.drawImage(help,750,260, null);
		
		paintComponents(g);

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	private void showCongratulations() {

	}

	public void showWinner(int roundWinner, int playerNumber,
			boolean pointsCounting) {
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

	public void updateGame(Room room, int playerNumber) {
		if (!room.hasGameStarted()) {
			// Recebe dominos sorteados para o jogador local
			organizer = new DominoOrganizer(room.getPlayer(playerNumber)
					.getDominosAsArray(), this, room);

			timer = new Timer(100, this);
			timer.start();
		}

		if (room.getScoreA() >= 7) {
			if (playerNumber % 2 ==0)
				endOfGame(true);
			else
				endOfGame(false);
		}
		
		if (room.getScoreB() >= 7) {
			if (playerNumber % 2 ==1)
				endOfGame(true);
			else
				endOfGame(false);

		}

		organizer.room = room;

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
		int x = 0;
		for (String dominoes_amount : otherPlayersDominoesAmount.split(" ")) {
			int buttons = 0;
			if (x == 0)
				buttons = organizer.getNumberOfPecasLeft();
			else if (x == 1)
				buttons = organizer.getNumberOfPecasTop();
			else
				buttons = organizer.getNumberOfPecasRight();

			int dominoes_t = Integer.parseInt(dominoes_amount);
			if (buttons > dominoes_t) {
				int diff = buttons - dominoes_t;
				while (diff > 0) {
					if (x == 0)
						organizer.tirarPeca(DominoOrganizer.JOGADOR_LEFT);
					else if (x == 1)
						organizer.tirarPeca(DominoOrganizer.JOGADOR_TOP);
					else
						organizer.tirarPeca(DominoOrganizer.JOGADOR_RIGHT);
					diff--;
				}
			}
			x++;
		}

		if (room.hasGameStarted()) {
			// Atualiza pe�as jogadas
			String[] dados = room.getLastData().split(" ");

			System.out.println("Aqui: " + dados[0]);
			int pecaValor1 = Integer.parseInt(dados[0].substring(0, 1));
			int pecaValor2 = Integer.parseInt(dados[0].substring(2, 3));
			int rotacao = Integer.parseInt(dados[1]);
			int index = Integer.parseInt(dados[2]);
			int head = Integer.parseInt(dados[3]);
			int headValue = Integer.parseInt(dados[4]);

			organizer.updateDominosJogados(pecaValor1, pecaValor2, rotacao,
					index, head, headValue);
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
			organizer.resetGame(room.getPlayer(playerNumber)
					.getDominosAsArray());
		}

		// Desbloqueia as peças se for sua vez
		if (room.getCurrentPlayer() == playerNumber) {
			organizer.setYourTurn(true);
			glow = PLAYERLOCAL;
			// yourTurn(room.isFirstMove(), room.getBoardHead1(),
			// room.getBoardHead2());
		}else if(room.getCurrentPlayer() == (playerNumber+1)%4){
			glow = PLAYERLEFT;
		}else if(room.getCurrentPlayer() == (playerNumber+2)%4){
			glow = PLAYERTOP;
		}else if(room.getCurrentPlayer() == (playerNumber+3)%4){
			glow = PLAYERRIGHT;
		}

	}

	public void updateScores(int player, int scoreA, int scoreB) {
		if (player % 2 == 1) {
			organizer.pontuarAzul(scoreA);
			organizer.pontuarVermelho(scoreB);
		} else {
			organizer.pontuarAzul(scoreB);
			organizer.pontuarVermelho(scoreA);
		}
	}
}