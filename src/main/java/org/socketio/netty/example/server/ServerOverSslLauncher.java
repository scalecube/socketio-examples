package org.socketio.netty.example.server;

import org.socketio.netty.SocketIOServer;
import org.socketio.netty.example.ssl.SecureSslContextFactory;

public class ServerOverSslLauncher {
	
	private static final int SOCKETIO_PORT = 4815;
	private static final int HEARTBEAT_INTERVAL = 60;
	private static final int HEARTBEAT_TIMEOUT = 90;
	private static final int CLOSE_TIMEOUT = 75;
	private static final String SOCKETIO_TRANSPORTS = "websocket,flashsocket,xhr-polling,jsonp-polling";
	
	public static void main(String[] args) {
		SocketIOServer socketioOverSSLServer = new SocketIOServer();
		socketioOverSSLServer.setPort(SOCKETIO_PORT);
		socketioOverSSLServer.setHeartbeatInterval(HEARTBEAT_INTERVAL);
		socketioOverSSLServer.setHeartbeatTimeout(HEARTBEAT_TIMEOUT);
		socketioOverSSLServer.setCloseTimeout(CLOSE_TIMEOUT);
		socketioOverSSLServer.setTransports(SOCKETIO_TRANSPORTS);
		socketioOverSSLServer.setSslContext(SecureSslContextFactory.getServerContext());
		socketioOverSSLServer.setListener(new EchoSocketIOListener());
		socketioOverSSLServer.start();
	}

}
