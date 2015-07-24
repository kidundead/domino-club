package br.ufpe.cin.main;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import br.ufpe.cin.controller.ServerEngine;
import br.ufpe.cin.gui.server.MainWindow;
import br.ufpe.cin.model.Player;
import br.ufpe.cin.model.Room;
import br.ufpe.cin.net.TCPServer;

public class ServerApp {

	public static void main(String[] args) {
		new ServerApp();
	}
	private ServerEngine engine;
	private final int NUM_PLAYERS = 4;
	private ConcurrentHashMap<String, Player> players;
	private ConcurrentHashMap<String, Room> rooms;

	private TCPServer tcp;

	public ServerApp() {
		engine = new ServerEngine(new MainWindow());
		rooms = new ConcurrentHashMap<String, Room>();
		players = new ConcurrentHashMap<String, Player>();
		tcp = new TCPServer(this);
		new Thread(tcp).start();
	}

	public void addPlayer(String ID) {
		players.put(ID, new Player(ID));
	}

	public void addPlayerToRoom(String ID, String roomID) {
		if (rooms.get(roomID) != null){
			rooms.get(roomID).addPlayer(players.get(ID));
			if (rooms.get(roomID).isFull()) {
				rooms.get(roomID).setupGame();
				sendRoomToPlayers(rooms.get(roomID));
			}
			sendRoomsToPlayers();
		}
	}

	public String createRoom(String playerID, String roomName) {
		SecureRandom random = new SecureRandom();
		String roomID;
		do {
			roomID = new BigInteger(130, random).toString(32);
		} while (rooms.get(roomID) != null);
		Room room = new Room(roomID, roomName, playerID);
		room.addPlayer(players.get(playerID));
		rooms.put(roomID, room);
		sendRoomsToPlayers();
		logIt("Sala " + roomID + " criada pelo jogador " + playerID);
		return room.getID();
	}

	public void destroyRoom(Room room) {
		for (Player p : room.getPlayers())
			tcp.sendRoomDestroyedSignal(p.getID());
		rooms.remove(room.getID());
	}

	public Player getPlayer(String ID) {
		return players.get(ID);
	}

	public String getRoomsInfo() {
		String str = "";
		for (Map.Entry<String, Room> entry : rooms.entrySet()) {
			String roomID = entry.getKey();
			Room room = entry.getValue();

			if (str.isEmpty())
				str = roomID + "." + room.getName() + "." + players.get(room.getCreatorID()).getName()
						+ "." + room.getAvailablePlayers();
			else
				str = str + "#" + roomID + "." + room.getName() + "."
						+ players.get(room.getCreatorID()).getName() + "."
						+ room.getAvailablePlayers();
		}
		return str;
	}

	public void logIt(String log) {
		engine.logIt(log);
	}

	public void newMove(String playerID, String roomID, String move,
			String data) {
		boolean endOfGame = false;
		Room room = rooms.get(roomID);
		room.setLastData(data);
		int head1 = Integer.parseInt(move.substring(0, 1));
		int head2 = Integer.parseInt(move.substring(2, 3));
		logIt("Sala " + roomID + ", MOVE: " + move);

		if (room.getPlayers().get(room.getCurrentPlayer()).getDominoesAmount() == 1) {
			logIt(room.getPlayers().get(room.getCurrentPlayer())
					.getName()
					+ " bateu!!");
			if (head1 == head2
					&& ((head1 == room.getBoardHead1() && head2 == room
							.getBoardHead2()) || (head1 == room.getBoardHead2() && head2 == room
							.getBoardHead1()))) {
				room.playerWonRound(room.getCurrentPlayer(), 3);
			} else if ((head1 == room.getBoardHead1() && head2 == room
					.getBoardHead2())
					|| (head1 == room.getBoardHead2() && head2 == room
							.getBoardHead1())) {
				room.playerWonRound(room.getCurrentPlayer(), 2);
			} else if (head1 == head2) {
				room.playerWonRound(room.getCurrentPlayer(), 1);
			} else {
				room.playerWonRound(room.getCurrentPlayer(), 0);
			}
			
			if (room.getScoreA() >= 7 || room.getScoreB() >= 7) {
				endOfGame = true;
			}
			
		} else {
			// Manda a sala processar a jogada, passando os valores da pedra e o lado
			room.newMove(head1, head2, Integer.parseInt(data.split(" ")[3]));
			logIt("Sala " + roomID + ", HEADS: "
					+ room.getBoardHead1() + " " + room.getBoardHead2());

			while (!room.currentPlayerHasDomino()) {
				logIt(room.getPlayers()
						.get(room.getCurrentPlayer()).getName()
						+ " tocou!!");
				room.someoneTouched();
				logIt("Toque seguidos: " + room.getTouchesAmount());

				if (room.getTouchesAmount() == 4) {
					int[] dominoesSums = new int[NUM_PLAYERS];
					int roundWinner = 0, smallestSum = 100;
					boolean hasSameSum = false;
					for (int i = 0; i < NUM_PLAYERS; i++) {
						dominoesSums[i] = room.getPlayer(i).getDominoesSum();
						if (dominoesSums[i] == smallestSum) {
							hasSameSum = true;
						} else if (dominoesSums[i] < smallestSum) {
							roundWinner = i;
							smallestSum = dominoesSums[i];
							hasSameSum = false;
						}
					}
					if (!hasSameSum) {
						logIt("Jogador " + roundWinner
								+ " ganhou na contagem de pontos.");
						room.playerWonRound(roundWinner, 0);
						room.lastTimeWasDraw();
					} else {
						logIt("Empate na contagem de pontos.");
						room.setFirstMove();
						room.resetBoard();
						room.lastTimeWasDraw();
						room.resetLastRoundWinner();
					}
					break;
				}

				room.nextPlayer();
			}

			room.resetTouches();
		}
		sendRoomToPlayers(rooms.get(roomID));
		
		if (endOfGame) {
			destroyRoom(room);
			sendRoomsToPlayers();
		}
	}

	public void removePlayer(String ID) {
		Room room = null;
		for (Entry<String, Room> entry : rooms.entrySet())
			for (Player player : entry.getValue().getPlayers())
				if (player.getID().equals(ID))
					room = entry.getValue();

		if (room != null)
			if (room.getAvailablePlayers() == 4
					|| room.getCreatorID().equals(ID))
				destroyRoom(room);
			else
				room.removePlayer(ID);

		players.remove(ID);
		sendRoomsToPlayers();
	}

	public void sendRoomsToPlayers() {
		String roomsInfo = getRoomsInfo();
		for (String ID : players.keySet()) {
			tcp.sendRoomsToPlayer(ID, roomsInfo);
		}
	}

	private void sendRoomToPlayers(Room room) {
		for (Player player : room.getPlayers()) {
			tcp.sendRoom(player.getID(), room);
		}
	}

	public void setPlayerName(String ID, String name) {
		players.get(ID).setName(name);
		for (Room room : rooms.values())
			for (Player p : room.getPlayers())
				if (p.getID().equals(ID))
					p.setName(name);
	}
}
