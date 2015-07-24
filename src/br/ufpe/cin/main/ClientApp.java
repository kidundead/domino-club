package br.ufpe.cin.main;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import br.ufpe.cin.controller.ClientEngine;
import br.ufpe.cin.model.Player;
import br.ufpe.cin.model.Room;
import br.ufpe.cin.net.TCPClient;

public class ClientApp {

	private static ClientEngine engine;
	private static Player player;
	private static String roomID;
	private static TCPClient tcp;

	public static void askForRooms() {
		tcp.askForRooms();
	}

	public static void connect(String hostname, String playerName)
		throws UnknownHostException, IOException {
		player.setName(playerName);
		tcp = new TCPClient(hostname);
		tcp.sendName(playerName);
	}

	public static void createRoom(String roomName) {
		tcp.createRoom(roomName);
	}

	public static void enterRoom(String roomID) {
		ClientApp.roomID = roomID;
		tcp.enterRoom(roomID);
	}

	public static Player getPlayer() {
		return player;
	}

	public static String getPlayerName() {
		return player.getName();
	}

	public static void main(String[] args) {
		new ClientApp();
	}

	public static void removeDomino(int head1, int head2) {
		player.removeDomino(head1, head2);
	}

	public static void roomDestroyed() {
		roomID = "";
		engine.reset();
	}

	public static void sendNewMove(int pecaValor1, int pecaValor2,int rotacao,int index, int head, int headValue) {
		player.removeDomino(pecaValor1, pecaValor2);
		if (player.getDominoesAmount() == 0)
			JOptionPane.showMessageDialog(null, "Você bateu!!");
		tcp.sendNewMove(roomID, pecaValor1+"|"+pecaValor2+" "+rotacao+" "+index+" "+head+" "+headValue);
	}

	public static void setID(String ID) {
		player.setID(ID);
	}

	public static void setRoomID(String roomID) {
		ClientApp.roomID = roomID;
	}

	public static void showAvailableRooms(String str) {
		if (str != null && !str.isEmpty()) {
			ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
			for (String s : str.split("#")) {
				list.add(new ArrayList<String>());
				for (String s2 : s.split("\\.")) {
					list.get(list.size() - 1).add(s2);
				}
			}
			engine.refreshTable(list);
		} else
			engine.clearTable();
	}

	public static void updateGame(Room room) {
		if (!room.hasGameStarted() || room.getResetBoard()) {
			for (Player p : room.getPlayers()) {
				if (player.getID().equals(p.getID())) {
					player.addDominos(p.getDominos());
				}
			}
		}
		engine.updateGame(room);
	}

	ClientApp() {
		player = new Player();
		engine = new ClientEngine();
		engine.askDetails();
	}
}
