package io.scalecube.socketio.examples.server;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import io.scalecube.socketio.ServerConfiguration;
import io.scalecube.socketio.SocketIOServer;

public class SslServerLauncher {

  public static void main(String[] args) throws Exception {
    // SSL Context
    SSLContext sslContext = initSslContext();

    // Server config
    ServerConfiguration config = ServerConfiguration.builder()
        .port(4815)
        .eventExecutorEnabled(false)
        .sslContext(sslContext)
        .build();

    // Start server
    SocketIOServer sslServer = SocketIOServer.newInstance(config);
    sslServer.setListener(new EchoListener());
    sslServer.start();
  }

  private static SSLContext initSslContext() throws Exception {
    char[] keystorePassword = "password".toCharArray();
    File keystoreFile = new File(SslServerLauncher.class.getResource("/keystore.jks").getFile());
    KeyStore ks = KeyStore.getInstance("JKS");
    ks.load(new FileInputStream(keystoreFile), keystorePassword);
    KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
    kmf.init(ks, keystorePassword);
    TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
    tmf.init(ks);
    SSLContext sslContext = SSLContext.getInstance("TLS");
    sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());
    return sslContext;
  }

}
