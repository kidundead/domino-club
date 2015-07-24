package br.ufpe.cin.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

	private ArrayList<Domino> dominoes;
	private String name, ID;

	public Player() {
		dominoes = new ArrayList<Domino>();
	}

	public Player(String ID) {
		this.ID = ID;
		dominoes = new ArrayList<Domino>();
	}

	public void addDomino(Domino domino) {
		dominoes.add(domino);
	}
	
	public void addDomino(int head1, int head2) {
		Domino domino = new Domino(head1, head2);
		addDomino(domino);
	}

	public void addDominos(ArrayList<Domino> dominoes) {
		this.dominoes = dominoes;
	}

	public Domino getDomino(int i) {

		if (dominoes.size() != 0) {

			return dominoes.get(i);
		} else{
			return null;
		}
	}

	public int getDominoesAmount() {
		return dominoes.size();
	}
	
	public int getDominoesSum() {
		int sum = 0;
		for (Domino d : dominoes) {
			sum = sum + d.getHead1() + d.getHead2();
		}
		return sum;
	}
	
//	public void updateDominoes(ArrayList<Domino> dominoes) {
//		for (int i = 0; i < this.dominoes.) {
//			Domino new_domino = new Domino(head1, head2);
//			addDomino(new_domino);
//		}
//	}

	public ArrayList<Domino> getDominos() {
		return dominoes;
	}
	
	public String[] getDominosAsArray() {
		String[] dominoes = new String[this.dominoes.size()]; 
		for (int i = 0; i < this.dominoes.size(); i++)
			dominoes[i] = this.dominoes.get(i).getHead1() + "" + this.dominoes.get(i).getHead2();
		System.out.println();
		return dominoes;
	}
	
	public String getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public boolean hasDomino() {
		return !dominoes.isEmpty();
	}

	public void removeAllDominoes() {
		dominoes.clear();
	}

	public void removeDomino(int head1, int head2) {
		Domino aux = null;
		for (Domino domino : dominoes) {
			if (domino.getHead1() == head1 && domino.getHead2() == head2)
				aux = domino;
		}
		if (aux != null)
			dominoes.remove(aux);
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public void setName(String name) {
		this.name = name;
	}
}
