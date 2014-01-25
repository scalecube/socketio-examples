package org.socketio.netty.example.server;

import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.logging.Slf4JLoggerFactory;
import org.socketio.netty.SocketIOServer;

public class ServerLauncher {
	
	private static final int SOCKETIO_PORT = 4810;
	private static final int HEARTBEAT_INTERVAL = 60;
	private static final int HEARTBEAT_TIMEOUT = 90;
	private static final int CLOSE_TIMEOUT = 75;
	private static final String SOCKETIO_TRANSPORTS = "websocket,flashsocket,xhr-polling,jsonp-polling";
	
	public static void main(String[] args) {
		// Set Netty logger
		InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());
		
		SocketIOServer socketioServer = new SocketIOServer();
		socketioServer.setPort(SOCKETIO_PORT);
		socketioServer.setTransports(SOCKETIO_TRANSPORTS);
		socketioServer.setHeartbeatInterval(HEARTBEAT_INTERVAL);
		socketioServer.setHeartbeatTimeout(HEARTBEAT_TIMEOUT);
		socketioServer.setCloseTimeout(CLOSE_TIMEOUT);
		socketioServer.setListener(new EchoSocketIOListener());
		socketioServer.start();
	}
	
}
