package org.socketio.netty.example.server;

import javax.net.ssl.SSLContext;

import org.socketio.netty.SocketIOServer;
import org.socketio.netty.example.ssl.SecureSslContextFactory;

public class ServerOverSslLauncher {
	
	private static final int SOCKETIO_PORT = 4815;
	private static final String SOCKETIO_TRANSPORTS = "websocket,xhr-polling";
	
	public static void main(String[] args) {
		SocketIOServer socketioOverSSLServer = new SocketIOServer();
		socketioOverSSLServer.setPort(SOCKETIO_PORT);
		socketioOverSSLServer.setTransports(SOCKETIO_TRANSPORTS);
		SSLContext sslContext = SecureSslContextFactory.getServerContext();
		socketioOverSSLServer.setSslContext(sslContext);
		socketioOverSSLServer.setListener(new EchoSocketIOListener());
		socketioOverSSLServer.start();
	}

}
