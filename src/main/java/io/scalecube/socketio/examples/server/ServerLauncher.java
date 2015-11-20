package io.scalecube.socketio.examples.server;


import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
import io.servicefabric.socketio.ServerConfiguration;
import io.servicefabric.socketio.SocketIOServer;

public class ServerLauncher {

  private static final int SOCKETIO_PORT = 4810;
  private static final int HEARTBEAT_INTERVAL = 25;
  private static final int HEARTBEAT_TIMEOUT = 60;
  private static final int CLOSE_TIMEOUT = 60;
  private static final String SOCKETIO_TRANSPORTS = "websocket,flashsocket,xhr-polling,jsonp-polling";

  public static void main(String[] args) {
    // Set Netty logger
    InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());
    ServerConfiguration.Builder configurationBuilder = new ServerConfiguration.Builder();
    configurationBuilder.setPort(SOCKETIO_PORT)
        .setTransports(SOCKETIO_TRANSPORTS)
        .setHeartbeatInterval(HEARTBEAT_INTERVAL)
        .setHeartbeatTimeout(HEARTBEAT_TIMEOUT)
        .setCloseTimeout(CLOSE_TIMEOUT)
        .setEventExecutorEnabled(true);

    SocketIOServer socketioServer = new SocketIOServer(configurationBuilder.build());
    socketioServer.setListener(new EchoSocketIOListener());

    socketioServer.start();
  }

}
