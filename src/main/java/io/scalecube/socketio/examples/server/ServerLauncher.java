package io.scalecube.socketio.examples.server;


import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
import io.scalecube.socketio.ServerConfiguration;
import io.scalecube.socketio.SocketIOServer;

public class ServerLauncher {

  private static final int SOCKETIO_PORT = 4810;

  public static void main(String[] args) {
    // Set Netty logger
    InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());

    ServerConfiguration config = ServerConfiguration.builder().port(SOCKETIO_PORT).build();
    SocketIOServer server = SocketIOServer.newInstance(config);
    server.setListener(new EchoListener());
    server.start();
  }

}
