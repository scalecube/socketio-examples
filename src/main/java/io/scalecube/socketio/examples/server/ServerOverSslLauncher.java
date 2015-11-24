package io.scalecube.socketio.examples.server;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
import io.scalecube.socketio.ServerConfiguration;
import io.scalecube.socketio.examples.ssl.SecureSslContextFactory;
import io.scalecube.socketio.SocketIOServer;

public class ServerOverSslLauncher {

  private static final int SOCKETIO_PORT = 4815;
  private static final int HEARTBEAT_INTERVAL = 60;
  private static final int HEARTBEAT_TIMEOUT = 90;
  private static final int CLOSE_TIMEOUT = 75;
  private static final String SOCKETIO_TRANSPORTS = "websocket,flashsocket,xhr-polling,jsonp-polling";

  public static void main(String[] args) {
    // Set Netty logger
    InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());

    ServerConfiguration configuration = ServerConfiguration.builder()
        .port(SOCKETIO_PORT)
        .heartbeatInterval(HEARTBEAT_INTERVAL)
        .heartbeatTimeout(HEARTBEAT_TIMEOUT)
        .closeTimeout(CLOSE_TIMEOUT)
        .transports(SOCKETIO_TRANSPORTS)
        .sslContext(SecureSslContextFactory.getServerContext())
        .build();

    SocketIOServer socketioOverSSLServer = SocketIOServer.newInstance(configuration);
    socketioOverSSLServer.setListener(new EchoSocketIOListener());
    socketioOverSSLServer.start();
  }

}
