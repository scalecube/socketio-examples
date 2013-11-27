package org.socketio.netty.example.client;


public class ClientLauncher {
	
	private static final int CLIENT_PORT = 9001;
	private static final String CLIENT_PATH = "/client";
	
	public static void main(String[] args) {
		SocketIOClient client = new SocketIOClient(CLIENT_PORT, CLIENT_PATH);
		client.start();
	}
	
}
