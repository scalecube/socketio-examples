package org.socketio.netty.example.server;

import org.socketio.netty.SocketIOServer;

public class ServerLauncher {
	
	private static final int SOCKETIO_PORT = 4810;
	private static final String SOCKETIO_TRANSPORTS = "websocket,flashsocket,xhr-polling,jsonp-polling";
	
	public static void main(String[] args) {
		SocketIOServer socketioServer = new SocketIOServer();
		socketioServer.setPort(SOCKETIO_PORT);
		socketioServer.setTransports(SOCKETIO_TRANSPORTS);
		socketioServer.setListener(new EchoSocketIOListener());
		socketioServer.start();
	}
	
}
