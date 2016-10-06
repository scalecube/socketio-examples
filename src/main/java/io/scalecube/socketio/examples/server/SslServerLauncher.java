package io.scalecube.socketio.examples.server;

import io.scalecube.socketio.ServerConfiguration;
import io.scalecube.socketio.examples.ssl.SecureSslContextFactory;
import io.scalecube.socketio.SocketIOServer;

public class SslServerLauncher {

  public static void main(String[] args) {
    // Server config
    ServerConfiguration config = ServerConfiguration.builder()
        .port(4815)
        .eventExecutorEnabled(false)
        .sslContext(SecureSslContextFactory.getServerContext())
        .build();

    // Start server
    SocketIOServer sslServer = SocketIOServer.newInstance(config);
    sslServer.setListener(new EchoListener());
    sslServer.start();
  }

}
