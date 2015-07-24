package br.ufpe.cin.gui.client.game;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Peca {
	//rotacoes
	public static final int ROTATE0 = 0;
	public static final int ROTATE180 = 180;
	public static final int ROTATE90AH = 902;
	public static final int ROTATE90HR = 901;
	
	AffineTransform at;
	BufferedImage bi;

	public int HORIZONTAL = 1;
	private int orientacao;
	 
	private int rotacao;
	boolean subiu;

	Integer valor1;
	Integer valor2;

	Integer valorBaixo;
	Integer valorCima;

	Integer valorDireita;
	Integer valorEsquerda;
	
	public int VERTICAL = 2; 

	public Peca(BufferedImage bi, Integer valor1, Integer valor2){
		rotacao = 0;
		
		this.bi = bi;

		at = new AffineTransform();

		orientacao = VERTICAL;
		subiu = false;

		this.valor1 = valor1;
		this.valor2 = valor2;

		valorCima = valor1;
		valorEsquerda = valor1;

		valorBaixo = valor2;
		valorDireita = valor2;
	}

	public void desce(){
		if(subiu){at.translate(0,+15); subiu = false; }
	}

	public double getHeigh(){
		return bi.getHeight();
	}
	
	public int getOrientacao(){
		return orientacao;
	}

	public int getRotacao(){
		return rotacao;
	}

	public double getTranslateX(){
		return at.getTranslateX();
	}

	public double getTranslateY(){
		return at.getTranslateY();
	}

	public double getWidth(){
		return bi.getWidth();
	}

	public boolean isCarroca(){
		return valor1.toString().equals(valor2.toString());
	}

	public void resetTranslate(){
		at = new AffineTransform();
	}

	public void rotate180() {
		rotacao = ROTATE180;
		//modificando variaveis de controle
		if(orientacao == VERTICAL){
			Integer aux = valorCima;
			valorCima = valorBaixo;
			valorBaixo = aux;
		}else{//horizontal
			Integer aux = valorDireita;
			valorDireita = valorEsquerda;
			valorEsquerda = aux;
		}

		int         width  = bi.getWidth();
		int         height = bi.getHeight();

		BufferedImage   newImage = new BufferedImage( width, height, bi.getType() );

		for( int i=0 ; i < width ; i++ )
			for( int j=0 ; j < height ; j++ )
				newImage.setRGB( width-i-1, height-j-1, bi.getRGB(i,j) );

		bi = newImage;
	}

	public void rotate90ah()
	{
		rotacao = ROTATE90AH;
		
		int         width  = bi.getWidth();
		int         height = bi.getHeight();

		BufferedImage   newImage = new BufferedImage( height, width, bi.getType() );

		for( int i=0 ; i < width ; i++ )
			for( int j=0 ; j < height ; j++ )
				newImage.setRGB( j, i, bi.getRGB(i,j) );  
		bi = newImage;

		//modificando variaveis de controle
		if(orientacao == VERTICAL){
			orientacao = HORIZONTAL;
			valorDireita = valorBaixo;
			valorEsquerda = valorCima;
		}else{
			orientacao = VERTICAL;
			valorCima = valorDireita;
			valorBaixo = valorEsquerda;
		}
	}

	public void rotate90hr()
	{
		rotacao = ROTATE90HR;
		//rotacionando
		int         width  = bi.getWidth();  
		int         height = bi.getHeight();

		BufferedImage   newImage = new BufferedImage( height, width, bi.getType() );  

		for( int i=0 ; i < width ; i++ )  
			for( int j=0 ; j < height ; j++ )  
				newImage.setRGB( height-1-j, i, bi.getRGB(i,j) );

		//salvandoimagem rotacionada
		bi = newImage;

		//modificando variaveis de controle
		if(orientacao == VERTICAL){
			orientacao = HORIZONTAL;
			valorDireita = valorCima;
			valorEsquerda = valorBaixo;
		}else{
			orientacao = VERTICAL;
			valorDireita = valorBaixo;
			valorEsquerda = valorCima;
		}
	}

	public void sobe(){

		if(!subiu){
			at.translate(0, -15);
			subiu = true;
		}
	}

	public void translate(double x , double y){
		at.translate(x, y);
	}

}
