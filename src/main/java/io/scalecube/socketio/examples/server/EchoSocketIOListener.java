package io.scalecube.socketio.examples.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.scalecube.socketio.Session;
import io.scalecube.socketio.SocketIOListener;

public class EchoSocketIOListener implements SocketIOListener {

  private static final Logger log = LoggerFactory.getLogger(EchoSocketIOListener.class);

  @Override
  public void onConnect(final Session client) {
    if (log.isDebugEnabled())
      log.debug("{}/{}: onConnect", client.getSessionId(), client.getLocalPort());
  }

  @Override
  public void onMessage(final Session client, final ByteBuf message) {
    if (log.isDebugEnabled())
      log.debug("{}/{}: onMessage: {}", client.getSessionId(), client.getLocalPort(), message);
    client.send(message);
  }

  @Override
  public void onDisconnect(final Session client) {
    if (log.isDebugEnabled())
      log.debug("{}/{}: onDisconnect", client.getSessionId(), client.getLocalPort());
  }

}
