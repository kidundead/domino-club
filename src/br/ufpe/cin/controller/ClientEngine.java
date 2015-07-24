package br.ufpe.cin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import br.ufpe.cin.gui.client.MainWindow;
import br.ufpe.cin.gui.client.RoomsPanel;
import br.ufpe.cin.gui.client.game.GameFrame;
import br.ufpe.cin.gui.client.game.GameLoading;
import br.ufpe.cin.main.ClientApp;
import br.ufpe.cin.model.Room;

public class ClientEngine {

	private GameFrame gameFrame;
	// private GameWindow gameWindow;
	private GameLoading gameLoading;
	private boolean hasShowedGameFrame = false;
	private MainWindow mainWindow;
	private RoomsPanel roomsPanel;

	public ClientEngine() {
		mainWindow = new MainWindow(this);
		roomsPanel = new RoomsPanel(this);
	}

	public void askDetails() {
		mainWindow.btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.setStatus("");
				String hostname = mainWindow.txtHostname.getText();
				String playerName = mainWindow.txtPlayerName.getText();

				if (!hostname.isEmpty() && !playerName.isEmpty()) {
					try {
						ClientApp.connect(hostname, playerName);
						mainWindow.setContentPane(roomsPanel);
						mainWindow.setSize(600, 400);
						ClientApp.askForRooms();
					} catch (UnknownHostException e) {
						mainWindow.setStatus("Problema com o nome de servidor");
						// e.printStackTrace();
					} catch (IOException e) {
						// e.printStackTrace();
						mainWindow.setStatus("Erro de entrada e saída");
					}
				} else {
					mainWindow.setStatus("Preencha os campos corretamente");
				}
			}
		});
	}

	public void clearTable() {
		roomsPanel.clearTable();
	}

	public void createRoom(String roomName) {
		mainWindow.dispose();
		// gameWindow = new GameWindow(this);
		gameLoading = new GameLoading();
		gameFrame = new GameFrame(this);
		ClientApp.createRoom(roomName);
	}

	public void enterRoom(String roomID) {
		mainWindow.dispose();
		// gameWindow = new GameWindow(this);
		gameLoading = new GameLoading();
		gameFrame = new GameFrame(this);
		ClientApp.enterRoom(roomID);
	}

	public void enviarPeca(int pecaValor1, int pecaValor2, int rotacao,
			int index, int head, int headValue) {

		ClientApp.sendNewMove(pecaValor1, pecaValor2, rotacao, index, head,
				headValue);

	}

	public void refreshTable(ArrayList<ArrayList<String>> list) {
		roomsPanel.setRoomsList(list);
	}

	public void reset() {
		if (gameLoading != null) {
			gameLoading.dispose();
			gameLoading = null;
		}
		if (gameFrame != null) {
			gameFrame.dispose();
			gameFrame = null;
		}
		hasShowedGameFrame = false;
		mainWindow.setVisible(true);
		mainWindow.setContentPane(roomsPanel);
	}

	public void updateGame(Room room) {
		// gameWindow.updateGame(room);
		gameFrame.updateGame(room);
		if (!hasShowedGameFrame) {
			gameLoading.dispose();
			gameFrame.setVisible(true);
			hasShowedGameFrame = true;
		}
	}

}
