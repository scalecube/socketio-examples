package org.socketio.netty.example.server;

import org.socketio.netty.SocketIOServer;
import org.socketio.netty.example.ssl.SecureSslContextFactory;

public class ServerOverSslLauncher {
	
	private static final int SOCKETIO_PORT = 4815;
	private static final String SOCKETIO_TRANSPORTS = "websocket,flashsocket,xhr-polling";
	
	public static void main(String[] args) {
		SocketIOServer socketioOverSSLServer = new SocketIOServer();
		socketioOverSSLServer.setPort(SOCKETIO_PORT);
		socketioOverSSLServer.setTransports(SOCKETIO_TRANSPORTS);
		socketioOverSSLServer.setSslContext(SecureSslContextFactory.getServerContext());
		socketioOverSSLServer.setListener(new EchoSocketIOListener());
		socketioOverSSLServer.start();
	}

}
