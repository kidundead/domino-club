package br.ufpe.cin.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import br.ufpe.cin.main.ServerApp;
import br.ufpe.cin.model.Room;

public class TCPServer implements Runnable {

	static class TCPServerRead extends Thread {

		String ID;
		Socket sock;

		public TCPServerRead(Socket sock, String ID) {
			this.sock = sock;
			this.ID = ID;
		}

		@Override
		public void run() {
			ObjectInputStream socketIn = null;
			try {
				socketIn = new ObjectInputStream(sock.getInputStream());
				while (true) {
					Object input = socketIn.readObject();
					if (input instanceof String) {
						String line = ((String) input).replaceAll("\n", "");
						String[] cmd = line.split(" ");

						/************************************
						 * Mensagens de controle jogador/sala
						 ***********************************/
						if (cmd[0].equals("001")) {
							// 001 -> Envia lista de salas disponíveis
							writers.get(sock).sendMessage(
									"001 " + serverApp.getRoomsInfo());
							logIt("Enviando lista de salas disponíveis para "
									+ ID);
						} else if (cmd[0].equals("002")) {
							// 002 -> Recebe nome do jogador
							serverApp.setPlayerName(ID, cmd[1]);
							logIt("Nome " + cmd[1] + " recebido do jogador "
									+ ID);
						} else if (cmd[0].equals("003")) {
							// 003 -> Cria sala e adiciona o jogador criador na
							// sala
							String roomID = serverApp.createRoom(ID, cmd[1]);
							writers.get(sock).sendMessage("003 " + roomID);
							logIt("Sala " + roomID + " criada, jogador " + ID
									+ " adicionado a ela");
						} else if (cmd[0].equals("004")) {
							// 004 -> Entra na sala
							serverApp.addPlayerToRoom(ID, cmd[1]);
							logIt("Jogador " + ID + " adicionado à sala "
									+ cmd[1]);
						}

						/*******************************
						 * Mensagens de controle do jogo
						 ******************************/
						else if (cmd[0].equals("101")) {
							String roomID = cmd[1];						
							String move = cmd[2];
							
							String dados = move+" "+cmd[3]+" "+cmd[4]+" "+cmd[5]+" "+cmd[6];
							// cmd[2] = move
							// cmd[3] = rotacao
							// cmd[4] = index
							// cmd[5] = head
							// cmd[6] = headValue
							
							serverApp.newMove(ID, roomID, move, dados);
						}
					}
				}
			} catch (IOException e) {
				IDs.remove(ID);
				writers.remove(sock);
				readers.remove(sock);
				logIt("Jogador " + ID + " se desconectou");
				serverApp.removePlayer(ID);
			} catch (ClassNotFoundException e) {
				// e.printStackTrace();
			}
		}
	}
	static class TCPServerWrite {

		private String ID;
		private Socket sock;
		private ObjectOutputStream socketOut;

		public TCPServerWrite(Socket sock, String ID) {
			this.sock = sock;
			this.ID = ID;
			try {
				socketOut = new ObjectOutputStream(sock.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			sendMessage("005 " + ID);
		}

		public void sendMessage(String msg) {
			try {
				socketOut.writeObject(msg + "\n");
				socketOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void sendRoom(Room room) {
			try {
				socketOut.writeObject(room);
				socketOut.reset();
				socketOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	private static Map<String, Socket> IDs;
	private static Map<Socket, TCPServerRead> readers;
	private static ServerSocket server;
	private static final int server_port = 12345;
	private static ServerApp serverApp;

	private static Map<Socket, TCPServerWrite> writers;

	private static void logIt(String log) {
		serverApp.logIt(log);
	}

	private boolean listening = true;

	public TCPServer(ServerApp app) {
		serverApp = app;
		readers = new HashMap<Socket, TCPServerRead>();
		writers = new HashMap<Socket, TCPServerWrite>();
		IDs = new HashMap<String, Socket>();

		try {
			server = new ServerSocket(server_port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (listening) {
			try {
				Socket socket = server.accept();
				SecureRandom random = new SecureRandom();
				String ID;
				do {
					ID = new BigInteger(130, random).toString(32);
				} while (serverApp.getPlayer(ID) != null);
				serverApp.addPlayer(ID);
				IDs.put(ID, socket);
				readers.put(socket, new TCPServerRead(socket, ID));
				writers.put(socket, new TCPServerWrite(socket, ID));
				readers.get(socket).start();
				logIt("Jogador " + ID + " conectado");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendRoom(String ID, Room room) {
		if (IDs.get(ID) != null && writers.get(IDs.get(ID)) != null)
			writers.get(IDs.get(ID)).sendRoom(room);
		logIt("Enviando sala para o jogador " + ID);
	}

	public void sendRoomDestroyedSignal(String ID) {
		if (IDs.get(ID) != null && writers.get(IDs.get(ID)) != null)
			writers.get(IDs.get(ID)).sendMessage("007");
		logIt("Eniviando sinal de destruição de sala para jogador " + ID);
	}

	public void sendRoomsToPlayer(String ID, String roomsInfo) {
		if (IDs.get(ID) != null && writers.get(IDs.get(ID)) != null)
			writers.get(IDs.get(ID)).sendMessage("001 " + roomsInfo);
		logIt("Enviando informações de salas para jogador " + ID);
	}
}