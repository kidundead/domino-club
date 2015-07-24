package br.ufpe.cin.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import br.ufpe.cin.main.ClientApp;
import br.ufpe.cin.model.Room;

public class TCPClient {

	static class TCPClientRead extends Thread {
		@Override
		public void run() {
			ObjectInputStream socketIn = null;
			try {
				socketIn = new ObjectInputStream(sock.getInputStream());
				while (true) {
					Object input = socketIn.readObject();
					if (input instanceof String) {
						String line = ((String) input).replaceAll("\n", "");
						String cmd = line.substring(0, 3);
						String param = null;
						if (line.length() - cmd.length() != 0) {
							param = line.substring(4, line.length());
							if (param.trim().isEmpty())
								param = null;
						}

						if (cmd.equals("001")) {
							ClientApp.showAvailableRooms(param);
						} else if (cmd.equals("003")) {
							ClientApp.setRoomID(param);
						} else if (cmd.equals("005")) {
							ClientApp.setID(param);
						} else if (cmd.equals("007")) {
							ClientApp.roomDestroyed();
						}
					} else if (input instanceof Room) {
						Room room = (Room) input;
						ClientApp.updateGame(room);
					}
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Servidor caiu!!");
				System.exit(0);
				//e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	static class TCPClientWrite {

		private static ObjectOutputStream socketOut;

		public static void sendMessage(String msg) {
			try {
				if (socketOut == null)
					socketOut = new ObjectOutputStream(sock.getOutputStream());
				socketOut.writeObject(msg + '\n');
				socketOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private static TCPClientRead read;

	private static Socket sock;

	private final int PORTA = 12345;

	public TCPClient(String address)
			throws UnknownHostException, IOException {
		sock = new Socket(address, PORTA);
		read = new TCPClientRead();
		read.start();
	}

	public void askForRooms() {
		TCPClientWrite.sendMessage("001");
	}

	public void createRoom(String roomName) {
		TCPClientWrite.sendMessage("003 " + roomName);
	}

	public void enterRoom(String roomID) {
		TCPClientWrite.sendMessage("004 " + roomID);
	}

	public boolean isConnected() {
		return sock.isConnected();
	}

	public void sendName(String name) {
		TCPClientWrite.sendMessage("002 " + name);
	}

	public void sendNewMove(String roomID, String move) {
		TCPClientWrite.sendMessage("101 " + roomID + " " + move);
	}
}