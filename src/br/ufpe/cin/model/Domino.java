package br.ufpe.cin.model;

import java.io.Serializable;

public class Domino implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int head1, head2;
	
	public Domino(int head1, int head2) {
		this.head1 = head1;
		this.head2 = head2;
	}
	
	public int getHead1() {
		return head1;
	}
	
	public int getHead2() {
		return head2;
	}
	
	// Checa se é carroça
	public boolean isDouble() {
		return (getHead1() == getHead2());
	}
	
	@Override
	public String toString() {
		return head1 + "" + head2;
	}
}
