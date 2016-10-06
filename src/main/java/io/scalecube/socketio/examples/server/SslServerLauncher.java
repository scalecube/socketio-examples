package io.scalecube.socketio.examples.server;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
import io.scalecube.socketio.ServerConfiguration;
import io.scalecube.socketio.examples.ssl.SecureSslContextFactory;
import io.scalecube.socketio.SocketIOServer;

public class SslServerLauncher {

  private static final int SOCKETIO_PORT = 4815;

  public static void main(String[] args) {
    // Set Netty logger
    InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());

    ServerConfiguration config = ServerConfiguration.builder()
        .port(SOCKETIO_PORT)
        .sslContext(SecureSslContextFactory.getServerContext())
        .build();

    SocketIOServer sslServer = SocketIOServer.newInstance(config);
    sslServer.setListener(new EchoListener());
    sslServer.start();
  }

}
