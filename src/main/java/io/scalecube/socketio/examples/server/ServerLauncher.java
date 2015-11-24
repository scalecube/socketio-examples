package io.scalecube.socketio.examples.server;


import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
import io.scalecube.socketio.ServerConfiguration;
import io.scalecube.socketio.SocketIOServer;

public class ServerLauncher {

  private static final int SOCKETIO_PORT = 4810;
  private static final int HEARTBEAT_INTERVAL = 25;
  private static final int HEARTBEAT_TIMEOUT = 60;
  private static final int CLOSE_TIMEOUT = 60;
  private static final String SOCKETIO_TRANSPORTS = "websocket,flashsocket,xhr-polling,jsonp-polling";

  public static void main(String[] args) {
    // Set Netty logger
    InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());

    ServerConfiguration configuration = ServerConfiguration.builder()
        .port(SOCKETIO_PORT)
        .transports(SOCKETIO_TRANSPORTS)
        .heartbeatInterval(HEARTBEAT_INTERVAL)
        .heartbeatTimeout(HEARTBEAT_TIMEOUT)
        .closeTimeout(CLOSE_TIMEOUT)
        .eventExecutorEnabled(true)
        .build();
    SocketIOServer socketioServer = SocketIOServer.newInstance(configuration);
    socketioServer.setListener(new EchoSocketIOListener());
    socketioServer.start();
  }

}
