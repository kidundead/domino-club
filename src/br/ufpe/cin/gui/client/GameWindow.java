package br.ufpe.cin.gui.client;

import javax.swing.JFrame;

import br.ufpe.cin.controller.ClientEngine;
import br.ufpe.cin.model.Room;

public class GameWindow extends JFrame {

	private ClientEngine engine;
	private GamePanel gamePanel;

	public GameWindow(ClientEngine clientEngine) {
		engine = clientEngine;
		gamePanel = new GamePanel();
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setContentPane(gamePanel);
		
		setVisible(true);
	}

	public void draw() {
		gamePanel.draw();
	}

	public void updateGame(Room room) {
		gamePanel.updateGame(room);
	}

}