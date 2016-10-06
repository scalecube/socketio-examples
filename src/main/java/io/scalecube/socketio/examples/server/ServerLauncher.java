package io.scalecube.socketio.examples.server;

import io.scalecube.socketio.ServerConfiguration;
import io.scalecube.socketio.SocketIOServer;

public class ServerLauncher {

  public static void main(String[] args) {
    // Server config
    ServerConfiguration config = ServerConfiguration.builder()
        .port(4810)
        .eventExecutorEnabled(false)
        .build();

    // Start server
    SocketIOServer server = SocketIOServer.newInstance(config);
    server.setListener(new EchoListener());
    server.start();
  }

}
