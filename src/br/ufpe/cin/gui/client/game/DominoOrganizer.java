package br.ufpe.cin.gui.client.game;

import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import br.ufpe.cin.model.Room;

public class DominoOrganizer {

	public static final int DOWN = 2;

	// Orientacao
	public static final int HORIZONTAL = 1;
	// constantes linhas guias horizontais do tabuleiro
	public static final int HORIZONTAL_CENTER = 20 + 10 + 60 + 82 + (916 / 2);
	public static final int HORIZONTAL_MAX = 1088;

	public static final int HORIZONTAL_MIN = 172 + 20;
	public static final int JOGADOR_LEFT = 2;
	public static final int JOGADOR_RIGHT = 3;

	// constantes jogadores
	public static final int JOGADOR_TOP = 1;
	public static final int LEFT = 3;
	// Sentido
	public static final int NONE = 0;

	public static final int POSICAO_A = 1;
	public static final int POSICAO_B = 2;
	// codigo para posi��o escolhida
	public static final int POSICAO_NONE = 0;

	public static final int RIGHT = 4;
	public static final int UP = 1;

	public static final int VERTICAL = 2;
	// constantes linhas guias verticais do tabuleiro
	public static final int VERTICAL_CENTER = 20 + 10 + 60 + 10 + 15 + 20
			+ (350 / 2);
	public static final int VERTICAL_MAX = 115 + 390;
	public static final int VERTICAL_MIN = 115 + 20;
	private boolean choosing;

	// variaveis de controle do jogo
	private boolean clientTurn;
	GameBoard gameboard;

	public int idJogador;
	private int indiceNavegacao;
	public Peca[] jogadorLeft;

	public ArrayList<Peca> jogadorLocal;
	public Peca[] jogadorRight;

	public Peca[] jogadorTop;
	private Peca maiorCarroca;
	private int nPecasJogadorLocal;

	private int nPecasLeft;
	private int nPecasRight;
	private int nPecasTop;

	public ArrayList<PecaJogada> pecasJogadas;

	BufferedImage ponteiroAzul;
	AffineTransform ponteiroAzulAt;

	BufferedImage ponteiroVermelho;
	AffineTransform ponteiroVermelhoAt;

	private int posicaoEscolhida;
	public int proximaPecaA;
	public int proximaPecaB;

	private Peca proxPeca;
	private Peca proxPecaHorizontal;

	private Peca proxPecaVertical;
	private PecaJogada referenciaA;

	private PecaJogada referenciaB;
	Room room;
	private int ultimoSentidoA;
	private int ultimoSentidoB;

	private int valorDominoHeadA;
	private int valorDominoHeadB;

	public DominoOrganizer(String[] pecasJogadorLocal, GameBoard gb, Room rm) {
		room = rm;
		gameboard = gb;
		// Amazena estado das pecas jogadas
		pecasJogadas = new ArrayList<PecaJogada>();

		// primeira no centro
		PecaJogada posicao = new PecaJogada(PecaJogada.VERTICAL,
				PecaJogada.NONE);
		posicao.translate(HORIZONTAL_CENTER - 40, VERTICAL_CENTER - 20);
		pecasJogadas.add(posicao);
		PecaJogada aux = posicao;

		// A: indo para a esquerda
		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.LEFT);
		posicao.translate(aux.getTranslateX() - 80, aux.getTranslateY() + 20);
		pecasJogadas.add(0, posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.LEFT);
		posicao.translate(aux.getTranslateX() - 80, aux.getTranslateY());
		pecasJogadas.add(0, posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.LEFT);
		posicao.translate(aux.getTranslateX() - 80, aux.getTranslateY());
		pecasJogadas.add(0, posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.LEFT);
		posicao.translate(aux.getTranslateX() - 80, aux.getTranslateY());
		pecasJogadas.add(0, posicao);
		aux = posicao;

		// A: subindo
		posicao = new PecaJogada(PecaJogada.VERTICAL, PecaJogada.UP);
		posicao.translate(aux.getTranslateX(), aux.getTranslateY() - 80);
		pecasJogadas.add(0, posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.VERTICAL, PecaJogada.UP);
		posicao.translate(aux.getTranslateX(), aux.getTranslateY() - 80);
		pecasJogadas.add(0, posicao);
		aux = posicao;

		// A: direita
		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.RIGHT);
		posicao.translate(aux.getTranslateX() + 40, aux.getTranslateY());
		pecasJogadas.add(0, posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.RIGHT);
		posicao.translate(aux.getTranslateX() + 80, aux.getTranslateY());
		pecasJogadas.add(0, posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.RIGHT);
		posicao.translate(aux.getTranslateX() + 80, aux.getTranslateY());
		pecasJogadas.add(0, posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.RIGHT);
		posicao.translate(aux.getTranslateX() + 80, aux.getTranslateY());
		pecasJogadas.add(0, posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.RIGHT);
		posicao.translate(aux.getTranslateX() + 80, aux.getTranslateY());
		pecasJogadas.add(0, posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.RIGHT);
		posicao.translate(aux.getTranslateX() + 80, aux.getTranslateY());
		pecasJogadas.add(0, posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.RIGHT);
		posicao.translate(aux.getTranslateX() + 80, aux.getTranslateY());
		pecasJogadas.add(0, posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.RIGHT);
		posicao.translate(aux.getTranslateX() + 80, aux.getTranslateY());
		pecasJogadas.add(0, posicao);
		aux = posicao;

		// A: descendo java.util.ConcurrentModificationException
		posicao = new PecaJogada(PecaJogada.VERTICAL, PecaJogada.DOWN);
		posicao.translate(aux.getTranslateX() + 40, aux.getTranslateY() + 40);
		pecasJogadas.add(0, posicao);
		aux = posicao;

		// A: Esquerda de novo
		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.LEFT);
		posicao.translate(aux.getTranslateX() - 80, aux.getTranslateY() + 40);
		pecasJogadas.add(0, posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.LEFT);
		posicao.translate(aux.getTranslateX() - 80, aux.getTranslateY());
		pecasJogadas.add(0, posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.LEFT);
		posicao.translate(aux.getTranslateX() - 80, aux.getTranslateY());
		pecasJogadas.add(0, posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.LEFT);
		posicao.translate(aux.getTranslateX() - 80, aux.getTranslateY());
		pecasJogadas.add(0, posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.LEFT);
		posicao.translate(aux.getTranslateX() - 80, aux.getTranslateY());
		pecasJogadas.add(0, posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.LEFT);
		posicao.translate(aux.getTranslateX() - 80, aux.getTranslateY());
		pecasJogadas.add(0, posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.LEFT);
		posicao.translate(aux.getTranslateX() - 80, aux.getTranslateY());
		pecasJogadas.add(0, posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.LEFT);
		posicao.translate(aux.getTranslateX() - 80, aux.getTranslateY());
		pecasJogadas.add(0, posicao);

		// B: direita
		aux = pecasJogadas.get(pecasJogadas.size() - 1);// pega posicao da
		// primeira peca

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.RIGHT);
		posicao.translate(aux.getTranslateX() + 40, aux.getTranslateY() + 20);
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.RIGHT);
		posicao.translate(aux.getTranslateX() + 80, aux.getTranslateY());
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.RIGHT);
		posicao.translate(aux.getTranslateX() + 80, aux.getTranslateY());
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.RIGHT);
		posicao.translate(aux.getTranslateX() + 80, aux.getTranslateY());
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		// B: descendo
		posicao = new PecaJogada(PecaJogada.VERTICAL, PecaJogada.DOWN);
		posicao.translate(aux.getTranslateX() + 40, aux.getTranslateY() + 40);
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.VERTICAL, PecaJogada.DOWN);
		posicao.translate(aux.getTranslateX(), aux.getTranslateY() + 80);
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;
		
		// B: Esquerda
		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.LEFT);
		posicao.translate(aux.getTranslateX() - 80, aux.getTranslateY() + 40);
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.LEFT);
		posicao.translate(aux.getTranslateX() - 80, aux.getTranslateY());
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.LEFT);
		posicao.translate(aux.getTranslateX() - 80, aux.getTranslateY());
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.LEFT);
		posicao.translate(aux.getTranslateX() - 80, aux.getTranslateY());
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.LEFT);
		posicao.translate(aux.getTranslateX() - 80, aux.getTranslateY());
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.LEFT);
		posicao.translate(aux.getTranslateX() - 80, aux.getTranslateY());
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.LEFT);
		posicao.translate(aux.getTranslateX() - 80, aux.getTranslateY());
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.LEFT);
		posicao.translate(aux.getTranslateX() - 80, aux.getTranslateY());
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		// B: subindo
		posicao = new PecaJogada(PecaJogada.VERTICAL, PecaJogada.UP);
		posicao.translate(aux.getTranslateX(), aux.getTranslateY() - 80);
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		// B: Direita novamente
		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.RIGHT);
		posicao.translate(aux.getTranslateX() + 40, aux.getTranslateY());
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.RIGHT);
		posicao.translate(aux.getTranslateX() + 80, aux.getTranslateY());
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.RIGHT);
		posicao.translate(aux.getTranslateX() + 80, aux.getTranslateY());
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.RIGHT);
		posicao.translate(aux.getTranslateX() + 80, aux.getTranslateY());
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.RIGHT);
		posicao.translate(aux.getTranslateX() + 80, aux.getTranslateY());
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.RIGHT);
		posicao.translate(aux.getTranslateX() + 80, aux.getTranslateY());
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.RIGHT);
		posicao.translate(aux.getTranslateX() + 80, aux.getTranslateY());
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		posicao = new PecaJogada(PecaJogada.HORIZONTAL, PecaJogada.RIGHT);
		posicao.translate(aux.getTranslateX() + 80, aux.getTranslateY());
		pecasJogadas.add(pecasJogadas.size(), posicao);
		aux = posicao;

		// Pe�as viradas dos Jogadores adversarios
		jogadorLeft = new Peca[6];
		jogadorRight = new Peca[6];
		jogadorTop = new Peca[6];

		nPecasLeft = 6;
		nPecasRight = 6;
		nPecasTop = 6;

		// Pe�as com numeros pra cima do Jogador local
		jogadorLocal = new ArrayList<Peca>();

		initPecas(pecasJogadorLocal);

		clientTurn = false;
		choosing = false;
		ultimoSentidoA = PecaJogada.NONE;
		ultimoSentidoB = PecaJogada.NONE;
		proximaPecaA = 23;
		proximaPecaB = 23;

		// pecas pontilhadas
		BufferedImage img = null;
		try {
			img = ImageIO.read(this.getClass().getResource(
					"/images/prox-peca.png"));
		} catch (IOException er) {
			er.printStackTrace();
		}
		proxPecaVertical = new Peca(img, -1, -1);
		proxPecaHorizontal = new Peca(img, -1, -1);
		proxPecaHorizontal.rotate90hr();

		try {
			img = ImageIO.read(this.getClass().getResource(
					"/images/ponteiro-azul.png"));
		} catch (IOException er) {
			er.printStackTrace();
		}
		ponteiroAzul = img;
		ponteiroAzulAt = new AffineTransform();
		ponteiroAzulAt.translate(1107, 23);

		try {
			img = ImageIO.read(this.getClass().getResource(
					"/images/ponteiro-vermelho.png"));
		} catch (IOException er) {
			er.printStackTrace();
		}
		ponteiroVermelho = img;
		ponteiroVermelhoAt = new AffineTransform();
		ponteiroVermelhoAt.translate(22, 611);

		// sobre primeira peca do jogador local
		indiceNavegacao = 0;
		jogadorLocal.get(0).sobe();

		valorDominoHeadA = -1;
		valorDominoHeadB = -1;

	}

	private void autoRotate(PecaJogada pj) {
		System.out.println("Rotate");
		if (proxPeca.isCarroca() && pj.getOrientacao() == VERTICAL) {
			if (posicaoEscolhida == POSICAO_A)
				valorDominoHeadA = proxPeca.valor1;
			else
				valorDominoHeadB = proxPeca.valor1;
		} else if (proxPeca.isCarroca() && pj.getOrientacao() == HORIZONTAL) {
			proxPeca.rotate90hr();
			if (posicaoEscolhida == POSICAO_A)
				valorDominoHeadA = proxPeca.valor1;
			else
				valorDominoHeadB = proxPeca.valor1;
		} else if (posicaoEscolhida == POSICAO_A && !proxPeca.isCarroca()) {
			System.out.println("Posicao A");
			if (pj.getOrientacao() == VERTICAL) {
				if (pj.getSentido() == UP) {
					System.out.println("A: Up");
					if (valorDominoHeadA == proxPeca.valorCima) {
						proxPeca.rotate180();
					} else {
						System.out.println("n�o precisa rotate");
					}
					valorDominoHeadA = proxPeca.valorCima;
				} else {// DOWN
					System.out.println("A: Down");
					if (valorDominoHeadA != proxPeca.valorCima) {
						proxPeca.rotate180();
					} else {
						System.out.println("n�o precisa rotate");
					}
					System.out.println("prox cima: " + proxPeca.valorCima);
					System.out.println("prox baixo: " + proxPeca.valorBaixo);
					valorDominoHeadA = proxPeca.valorBaixo;
				}
			} else {// Horizontal
				if (pj.getSentido() == LEFT) {
					System.out.println("A: left");
					if (valorDominoHeadA == proxPeca.valorCima) {
						proxPeca.rotate90hr();
					} else {
						proxPeca.rotate90ah();
					}
					valorDominoHeadA = proxPeca.valorEsquerda;
				} else {// RIGHT
					System.out.println("A: right");
					if (valorDominoHeadA == proxPeca.valorCima) {
						proxPeca.rotate90ah();
					} else {
						proxPeca.rotate90hr();
					}
					valorDominoHeadA = proxPeca.valorDireita;
				}
			}
		} else {// Posicao B
			System.out.println("Posicao B");
			if (pj.getOrientacao() == VERTICAL) {
				if (pj.getSentido() == UP) {
					System.out.println("B: UP");
					if (valorDominoHeadB == proxPeca.valorCima) {
						proxPeca.rotate180();
					} else {
						System.out.println("nao precisa rotate");
					}
					valorDominoHeadB = proxPeca.valorCima;
				} else {// DOWN
					System.out.println("B: Down");
					if (valorDominoHeadB == proxPeca.valorBaixo) {
						proxPeca.rotate180();
					}else{
						System.out.println("n�o precisa rotate");
					}
					valorDominoHeadB = proxPeca.valorBaixo;
				}
			} else {// Horizontal
				if (pj.getSentido() == LEFT) {
					if (valorDominoHeadB == proxPeca.valorCima) {
						proxPeca.rotate90hr();
					} else {
						proxPeca.rotate90ah();
					}
					valorDominoHeadB = proxPeca.valorEsquerda;
				} else {// RIGHT
					if (valorDominoHeadB == proxPeca.valorCima) {
						proxPeca.rotate90ah();
					} else {
						proxPeca.rotate90hr();
					}
					valorDominoHeadB = proxPeca.valorDireita;
				}
			}
		}
		System.out.println("valor A: " + valorDominoHeadA);
		System.out.println("valor B: " + valorDominoHeadB);
	}

	public void detectCarroca() {
		if (valorDominoHeadA == -1) {
			maiorCarroca = null;
			for (int i = 0; i < jogadorLocal.size(); i++) {
				Peca p = jogadorLocal.get(i);
				if (p.isCarroca()) {
					if (maiorCarroca != null && p.valor1 > maiorCarroca.valor1) {
						maiorCarroca = p;
					} else if (maiorCarroca == null) {
						maiorCarroca = p;
					}
				}
			}
		}
	}

	public int getNumberOfPecasLeft() {
		return nPecasLeft;
	}

	public int getNumberOfPecasRight() {
		return nPecasRight;
	}

	public int getNumberOfPecasTop() {
		return nPecasTop;
	}

	private BufferedImage getPecaImage(String pecaId) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(this.getClass().getResource(
					"/images/dominoes/" + pecaId + ".png"));
		} catch (IOException er) {
			er.printStackTrace();
		}
		return img;
	}

	public void initPecas(String[] pecasJogadorLocal) {
		jogadorLocal.clear();
		// Controle de posicionamento a cada inser��o
		double xLocal = 480;
		double yLocal = 538;

		// inicia dominos do jogador local
		for (int i = 0; i < jogadorLeft.length; i++) {
			BufferedImage img = null;

			try {
				img = ImageIO.read(this.getClass().getResource(
						"/images/dominoes/" + pecasJogadorLocal[i] + ".png"));
			} catch (IOException er) {
				er.printStackTrace();
			}

			Integer v1 = Integer.parseInt(String.valueOf(pecasJogadorLocal[i]
					.charAt(0)));
			Integer v2 = Integer.parseInt(String.valueOf(pecasJogadorLocal[i]
					.charAt(1)));

			Peca pecaLocal = new Peca(img, v1, v2);
			pecaLocal.translate(xLocal, yLocal);
			jogadorLocal.add(pecaLocal);
			xLocal += 45;
		}

		// Controle de posicionamento a cada inser��o
		double xWest = 42;
		double yWest = 231;

		double xEast = 1103;
		double yEast = 231;

		double xNorth = 512;
		double yNorth = 32;

		// Inicia domin�s dos jogadores n�o locais
		for (int i = 0; i < jogadorLeft.length; i++) {

			// Carrega imagem da pe�a virada na variavel img
			String pathImage = "/images/peca-virada.png";
			BufferedImage img = null;
			try {
				img = ImageIO.read(getClass().getResource(pathImage));
			} catch (IOException er) {
				er.printStackTrace();
			}

			// coloca nos vetores dos player em que a pe�a fica por padrao 90
			// graus
			Peca pecaWest = new Peca(img, null, null);
			pecaWest.translate(xWest, yWest);
			jogadorLeft[i] = pecaWest;
			yWest += 30;

			Peca pecaEast = new Peca(img, null, null);
			pecaEast.translate(xEast, yEast);
			jogadorRight[i] = pecaEast;
			yEast += 30;

			// rotaciona pe�a 90 para ficar no player north
			Peca pecaNorth = new Peca(img, null, null);
			pecaNorth.rotate90hr();
			pecaNorth.translate(xNorth, yNorth);
			jogadorTop[i] = pecaNorth;
			xNorth += 30;
		}
	}

	public boolean isYourTurn() {
		return clientTurn;
	}

	public boolean jogarPeca(Peca p) {
		System.out.println("Jogar Peca");
		if (clientTurn && temPeca() && podeJogar(p) || proxPeca == p) {
			System.out.println("Peca da vez: " + p.valor1 + p.valor2);
			System.out.println("valor A: " + valorDominoHeadA);
			System.out.println("valor B: " + valorDominoHeadB);
			if (!choosing) {// Verificar se jogador local tem pe�a para jogar
				if (room.isFirstMove()) {
					// Primeira jogada do jogo inteiro
					
					//System.out.println("Primera peca jogada do jogo inteiro");
					detectCarroca();
					//System.out.println("Maior carroca :" + maiorCarroca.valor1);
					if (!(p.isCarroca() && p.valor1 == maiorCarroca.valor1)) {
						JOptionPane
								.showMessageDialog(null,
										"A primeira peca do jogo deve ser a maior carroca.");
						return false;
					}

					PecaJogada pj = pecasJogadas.get(23);
					pj.setPeca(p);

					proximaPecaA--;
					proximaPecaB++;
					ultimoSentidoA = PecaJogada.LEFT;
					ultimoSentidoB = PecaJogada.RIGHT;

					valorDominoHeadA = p.valorCima;
					valorDominoHeadB = p.valorBaixo;

					System.out.println("Peca: " + p.valor1 + p.valor2);
					System.out.println("Rotacao: " + p.getRotacao());
					System.out.println("index : " + (proximaPecaA - 1));
					System.out.println("HEAD: " + POSICAO_A);
					System.out.println("Valor HEAD: " + valorDominoHeadA);
					gameboard.enviarPeca(p.valor1, p.valor2, p.getRotacao(),
							23, POSICAO_NONE, -1);
					setYourTurn(false);
				} else if (room.getResetBoard()) {
					// Primeira jogada de uma partida (a partir da segunda)
					
					PecaJogada pj = pecasJogadas.get(23);
					pj.setPeca(p);

					proximaPecaA--;
					proximaPecaB++;
					ultimoSentidoA = PecaJogada.LEFT;
					ultimoSentidoB = PecaJogada.RIGHT;

					valorDominoHeadA = p.valorCima;
					valorDominoHeadB = p.valorBaixo;

					System.out.println("Peca: " + p.valor1 + p.valor2);
					System.out.println("Rotacao: " + p.getRotacao());
					System.out.println("index : " + (proximaPecaA - 1));
					System.out.println("HEAD: " + POSICAO_A);
					System.out.println("Valor HEAD: " + valorDominoHeadA);
					gameboard.enviarPeca(p.valor1, p.valor2, p.getRotacao(),
							23, POSICAO_NONE, -1);
					setYourTurn(false);
					
				} else {
					// Se não for a 1 jogada
					this.proxPeca = p;

					// Se tiver op��es pela pe�a poder ser jogada em qualquer
					// uma das cebe�as
					if ((p.valor1 == valorDominoHeadA && p.valor2 == valorDominoHeadB)
							|| (p.valor1 == valorDominoHeadB && p.valor2 == valorDominoHeadA)
							|| (valorDominoHeadB == valorDominoHeadA)) {
						System.out.println("pode jogar dos dois lados");

						choosing = true;
						posicaoEscolhida = POSICAO_A;

						PecaJogada pj = pecasJogadas.get(proximaPecaA);

						if (pj.getOrientacao() == HORIZONTAL) {
							pj.setPeca(proxPecaHorizontal);
						} else {
							pj.setPeca(proxPecaVertical);
						}

						// Falta: Exibir notifica��o de usar setas para escolher
						// onde jogar

						// /////////////////////////////////////////////////////
						/* Por padrao a escolha inicial eh no "head A" do jogo */
						// /////////////////////////////////////////////////////

					} else {

						System.out.println("Escolhendo lado automaticamente");
						if (valorDominoHeadA == p.valor1
								|| valorDominoHeadA == p.valor2) {

							posicaoEscolhida = POSICAO_A;
							PecaJogada pj = pecasJogadas.get(proximaPecaA);
							autoRotate(pj);
							pj.setPeca(proxPeca);
							ultimoSentidoA = pj.getSentido();
							proximaPecaA--;
							// diz ao gameboard pe�a jogada para ser enviada ao
							// servidor e destribuida aos demais players
							gameboard.enviarPeca(proxPeca.valor1,
									proxPeca.valor2, proxPeca.getRotacao(),
									proximaPecaA + 1, POSICAO_A,
									valorDominoHeadA);
						} else {
							posicaoEscolhida = POSICAO_B;
							PecaJogada pj = pecasJogadas.get(proximaPecaB);
							autoRotate(pj);
							pj.setPeca(proxPeca);
							ultimoSentidoB = pj.getSentido();
							proximaPecaB++;
							// diz ao gameboard pe�a jogada para ser enviada ao
							// servidor e destribuida aos demais players
							gameboard.enviarPeca(proxPeca.valor1,
									proxPeca.valor2, proxPeca.getRotacao(),
									proximaPecaB - 1, POSICAO_B,
									valorDominoHeadB);
						}
						setYourTurn(false);

					}
				}
			} else {// desenha onde pe�a sera jogada depois de definido local
				choosing = false;
				if (posicaoEscolhida == POSICAO_A) {
					PecaJogada pj = pecasJogadas.get(proximaPecaA);
					autoRotate(pj);
					pj.setPeca(proxPeca);
					ultimoSentidoA = pj.getSentido();
					proximaPecaA--;

					// diz ao gameboard pe�a jogada para ser enviada ao servidor
					// e destribuida aos demais players
					gameboard.enviarPeca(proxPeca.valor1, proxPeca.valor2,
							proxPeca.getRotacao(), proximaPecaA + 1, POSICAO_A,
							valorDominoHeadA);
				} else {// posicao B foi escolhida
					PecaJogada pj = pecasJogadas.get(proximaPecaB);
					autoRotate(pj);
					pj.setPeca(proxPeca);
					ultimoSentidoB = pj.getSentido();
					proximaPecaB++;

					// diz ao gameboard pe�a jogada para ser enviada ao servidor
					// e destribuida aos demais players
					gameboard.enviarPeca(proxPeca.valor1, proxPeca.valor2,
							proxPeca.getRotacao(), proximaPecaB - 1, POSICAO_B,
							valorDominoHeadB);
				}
				setYourTurn(false);
			}
			return true;
		} else if (!temPeca()) {
			JOptionPane.showMessageDialog(null,
					"Que pena,voce tocou! Espere a pr�xima rodada ");
			return false;
		} else if (!clientTurn) {
			JOptionPane.showMessageDialog(null, "Aguarde sua vez para jogar!");
			return false;
		} else if (!podeJogar(p)) {
			JOptionPane
					.showMessageDialog(null,
							"Ei ei! Gato por lebre aqui nao!\n Essa peca nao pode ser jogada! Espertinho!");
			return false;
		}
		return false;
	}

	public void keyReleased(KeyEvent e) {
		//System.out.println("released");
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_SPACE) {
			// Escolhe a pe�a a ser jogada
			if (!choosing) {
				if (jogadorLocal.size() > 0) {
					if (jogarPeca(jogadorLocal.get(indiceNavegacao))) {
						System.out.println("pode jogar pow");
						jogadorLocal.remove(indiceNavegacao);
						indiceNavegacao--;
					}
				}
			} else {// choosing
				jogarPeca(proxPeca);
			}
		} else if (key == KeyEvent.VK_LEFT) {
			// System.out.println("LEFT TYPED");
			if (!choosing) {
				// Navega entre as pe�as
				if (indiceNavegacao > 0) {
					Peca p = null;

					// Pega pe�a do array list, "desce"
					if (indiceNavegacao < jogadorLocal.size()) {
						p = jogadorLocal.get(indiceNavegacao);
						p.desce();
					}

					indiceNavegacao--;

					// Pega pe�a do array list, "sobe"
					p = jogadorLocal.get(indiceNavegacao);
					p.sobe();

				} else {
					jogadorLocal.get(0).sobe();
					// System.out.println("ultima pe�a da esquerda");
				}
			} else {// choosing
				// System.out.println("Escolheu lado A");

				posicaoEscolhida = POSICAO_A;

				pecasJogadas.get(proximaPecaB).setPeca(null);

				PecaJogada pj = pecasJogadas.get(proximaPecaA);

				if (pj.getOrientacao() == HORIZONTAL) {
					pj.setPeca(proxPecaHorizontal);
				} else {
					pj.setPeca(proxPecaVertical);
				}
			}
		} else if (key == KeyEvent.VK_RIGHT) {

			// System.out.println("RIGHT TYPED");
			if (!choosing) {
				if (indiceNavegacao < jogadorLocal.size() - 1) {

					Peca p;
					// Pega pe�a do array list, "desce"
					if (indiceNavegacao >= 0) {
						p = jogadorLocal.get(indiceNavegacao);
						p.desce();
					}

					indiceNavegacao++;

					// Pega pe�a do array list, "sobe"
					p = jogadorLocal.get(indiceNavegacao);
					p.sobe();
				} else {
					jogadorLocal.get(jogadorLocal.size() - 1).sobe();
					// System.out.println("ultima pe�a da direita");
				}
			} else {// choosing

				// System.out.println("Escolheu lado B");

				posicaoEscolhida = POSICAO_B;
				pecasJogadas.get(proximaPecaA).setPeca(null);
				PecaJogada pj = pecasJogadas.get(proximaPecaB);

				if (pj.getOrientacao() == HORIZONTAL) {
					pj.setPeca(proxPecaHorizontal);
				} else {
					pj.setPeca(proxPecaVertical);
				}
				// System.out.println("Posicao B, x: " + pj.getTranslateX()
				// + ", y: " + pj.getTranslateY());
			}
		}

		// Eventos para testar tirada de pe�as dos jogadores nao locais
		if (key == KeyEvent.VK_N) {
			pontuarAzul(1);
			pontuarVermelho(0);
			// tirarPeca(JOGADOR_TOP);
		} else if (key == KeyEvent.VK_W) {
			pontuarAzul(2);
			pontuarVermelho(2);
			tirarPeca(JOGADOR_LEFT);
		} else if (key == KeyEvent.VK_E) {
			tirarPeca(JOGADOR_RIGHT);
		}
	}

	public boolean podeJogar(Peca p) {
		if (valorDominoHeadA == -1) {
			return true;
		}
		return p.valor1 == valorDominoHeadA || p.valor2 == valorDominoHeadA
				|| p.valor1 == valorDominoHeadB || p.valor2 == valorDominoHeadB;
	}

	public void pontuarAzul(int pontos) {
			ponteiroAzulAt = new AffineTransform();
			ponteiroAzulAt.translate(1107, 23);
			ponteiroAzulAt.rotate(Math.toRadians((-12.857) * pontos),
					ponteiroAzul.getWidth() - 7, ponteiroAzul.getHeight() / 2);
	}

	public void pontuarVermelho(int pontos) {
			ponteiroVermelhoAt = new AffineTransform();
			ponteiroVermelhoAt.translate(22, 611);
			ponteiroVermelhoAt.rotate(Math.toRadians((-12.857) * pontos), 7,
					ponteiroVermelho.getHeight() / 2);
	}

	public void resetGame(String[] pecas) {

		clientTurn = false;
		choosing = false;
		ultimoSentidoA = PecaJogada.NONE;
		ultimoSentidoB = PecaJogada.NONE;
		proximaPecaA = 23;
		proximaPecaB = 23;

		proxPecaHorizontal.rotate90hr();

		for (int i = 0; i < pecasJogadas.size(); i++) {
			pecasJogadas.get(i).setPeca(null);
		}

		initPecas(pecas);

		// sobre primeira peca do jogador local
		indiceNavegacao = 0;
		jogadorLocal.get(0).sobe();

		valorDominoHeadA = -1;
		valorDominoHeadB = -1;
	}

	public void setYourTurn(boolean value) {
		clientTurn = value;
	}

	public boolean temPeca() {
		if (valorDominoHeadA == -1) {
			return true;
		}
		for (int i = 0; i < jogadorLocal.size(); i++) {
			Peca p = jogadorLocal.get(i);
			if (p != null && p.valor1 != -1) {
				if (p.valor1 == valorDominoHeadA
						|| p.valor2 == valorDominoHeadA
						|| p.valor1 == valorDominoHeadB
						|| p.valor2 == valorDominoHeadB)
					return true;
			}
		}
		return false;
	}

	public void tirarPeca(int JOGADOR) {
		if (JOGADOR == JOGADOR_TOP && nPecasTop > 0) {
			jogadorTop[(-1) + nPecasTop--] = null;
		} else if (JOGADOR == JOGADOR_LEFT && nPecasLeft > 0) {
			jogadorLeft[(-1) + nPecasLeft--] = null;
		} else if (JOGADOR == JOGADOR_RIGHT && nPecasRight > 0) {
			jogadorRight[(-1) + nPecasRight--] = null;
		}
	}

	public void updateDominosJogados(int pecaValor1, int pecaValor2,
			int rotacao, int index, int head, int headValue) {

		System.out.println("Pe�a: " + pecaValor1 + pecaValor2);
		System.out.println("Rotate: " + rotacao);
		System.out.println("index: " + index);
		System.out.println("Head: " + head);
		System.out.println("HeadValue :" + headValue);

		// atualiza flags de controle
		if (head == POSICAO_NONE && proximaPecaA == proximaPecaB) {
			// garante que o jogador local nao receba a propria jogada dos
			// servidor
			// string que identifica peca jogada pelo outro jogador
			String pecaId = String.valueOf(pecaValor1)
					+ String.valueOf(pecaValor2);
			// cria peca com que o outro jogador jogou
			Peca p = new Peca(getPecaImage(pecaId), pecaValor1, pecaValor2);

			// rotaciona a peca de acordo com o mapa
			if (rotacao == Peca.ROTATE90HR) {
				p.rotate90hr();
			} else if (rotacao == Peca.ROTATE90AH) {
				p.rotate90ah();
			} else if (rotacao == Peca.ROTATE180) {
				p.rotate180();
			}

			// pega pe�a do index onde peca foi jogada
			PecaJogada pjLocal = pecasJogadas.get(index);
			// atualiza posi��o correta com nova peca
			pjLocal.setPeca(p);

			valorDominoHeadA = pecaValor1;
			valorDominoHeadB = pecaValor2;
			ultimoSentidoA = LEFT;
			ultimoSentidoB = RIGHT;
			proximaPecaA--;
			proximaPecaB++;

		} else if (head == POSICAO_A && index == proximaPecaA) {// garante que o
																// jogador local
																// nao receba a
																// propria
																// jogada dos
																// servidor
			// string que identifica peca jogada pelo outro jogador
			String pecaId = String.valueOf(pecaValor1)
					+ String.valueOf(pecaValor2);
			// cria peca com que o outro jogador jogou
			Peca p = new Peca(getPecaImage(pecaId), pecaValor1, pecaValor2);
			// rotaciona a peca de acordo com o mapa
			if (rotacao == Peca.ROTATE90HR) {
				p.rotate90hr();
			} else if (rotacao == Peca.ROTATE90AH) {
				p.rotate90ah();
			} else if (rotacao == Peca.ROTATE180) {
				p.rotate180();
			}

			// pega pe�a do index onde peca foi jogada
			PecaJogada pjLocal = pecasJogadas.get(index);
			// atualiza posi��o correta com nova peca
			pjLocal.setPeca(p);
			valorDominoHeadA = headValue;
			ultimoSentidoA = pjLocal.getSentido();
			proximaPecaA--;
		} else if (head == POSICAO_B && index == proximaPecaB){// garante que o
																// jogador local
																// nao receba a
																// propria
																// jogada dos
																// servidor
			// string que identifica peca jogada pelo outro jogador
			String pecaId = String.valueOf(pecaValor1)
					+ String.valueOf(pecaValor2);
			// cria peca com que o outro jogador jogou
			Peca p = new Peca(getPecaImage(pecaId), pecaValor1, pecaValor2);
			// rotaciona a peca de acordo com o mapa
			if (rotacao == Peca.ROTATE90HR) {
				p.rotate90hr();
			} else if (rotacao == Peca.ROTATE90AH) {
				p.rotate90ah();
			} else if (rotacao == Peca.ROTATE180) {
				p.rotate180();
			}

			// pega pe�a do index onde peca foi jogada
			PecaJogada pjLocal = pecasJogadas.get(index);
			// atualiza posi��o correta com nova peca
			pjLocal.setPeca(p);
			valorDominoHeadB = headValue;
			ultimoSentidoB = pjLocal.getSentido();
			proximaPecaB++;
		}

	}

}
