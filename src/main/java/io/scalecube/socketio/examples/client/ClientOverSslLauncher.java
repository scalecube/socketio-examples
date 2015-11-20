package io.scalecube.socketio.examples.client;

import javax.net.ssl.SSLContext;

import io.scalecube.socketio.examples.ssl.SecureSslContextFactory;

public class ClientOverSslLauncher {

  private static final int CLIENT_PORT = 9002;
  private static final String CLIENT_PATH = "/client";

  public static void main(String[] args) {
    SSLContext sslContext = SecureSslContextFactory.getServerContext();
    SocketIOClient client = new SocketIOClient(CLIENT_PORT, CLIENT_PATH, sslContext);
    client.start();
  }

}
