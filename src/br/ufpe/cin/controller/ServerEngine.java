package br.ufpe.cin.controller;

import br.ufpe.cin.gui.server.MainWindow;

public class ServerEngine {
	
	private MainWindow mainWindow;
	
	public ServerEngine(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public void logIt(String log) {
		mainWindow.logIt(log);
	}
	
}
