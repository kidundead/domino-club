package br.ufpe.cin.gui.client.game;

import java.awt.geom.AffineTransform;

public class PecaJogada {		
	public static final int DOWN = 2;
	//Orientacao
	public static final int HORIZONTAL = 1;
	
	public static final int LEFT = 3;
	//Sentido
	public static final int NONE = 0;
	public static final int RIGHT = 4;
	public static final int UP = 1;
	public static final int VERTICAL = 2;
	
	public AffineTransform at;
	//Colocado como
	private int orientacao;
	//peca
	private Peca peca;
	private int rotacao;
	private int sentido;
	
	public PecaJogada(int orientacao, int sentido){
		at = new AffineTransform();
		this.orientacao = orientacao;
		this.sentido = sentido;
		this.peca = null;
	}
	
	public PecaJogada(Peca peca, int orientacao, int sentido){
		this.orientacao = orientacao;
		this.sentido = sentido;
		this.peca = peca;
	}
	
	public int getOrientacao(){
		return orientacao;
	}
	
	public Peca getPeca(){
		return peca;
	}
	
	public int getSentido(){
		return sentido;
	}
	
	public double getTranslateX(){
		return at.getTranslateX();
	}
	
	public double getTranslateY(){
		return at.getTranslateY();
	}
	
//	public void resetTranslate(){
//		at = new AffineTransform();
//	}
	
	public void setOrientacao(int orientacao){
		this.orientacao = orientacao;
	}
	
	public void setPeca(Peca p){
		this.peca = p;
	}
	public void setSentido(int sentido){
		this.sentido = sentido;
	}

	public void translate(double x, double y){
		at.translate(x, y);
	}
}
