package org.socketio.netty.example.server;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socketio.netty.ISession;
import org.socketio.netty.ISocketIOListener;

public class EchoSocketIOListener implements ISocketIOListener {
	
	private static final Logger log = LoggerFactory.getLogger(EchoSocketIOListener.class);
	
	@Override
	public void onConnect(final ISession client) {
		if (log.isDebugEnabled())
			log.debug("{}/{}: onConnect", client.getSessionId(), client.getLocalPort());
	}

	@Override
	public void onMessage(final ISession client, final ByteBuf message) {
		if (log.isDebugEnabled())
			log.debug("{}/{}: onMessage: {}", client.getSessionId(), client.getLocalPort(), message);
		client.send(message);
	}

	@Override
	public void onDisconnect(final ISession client) {
		if (log.isDebugEnabled())
			log.debug("{}/{}: onDisconnect", client.getSessionId(), client.getLocalPort());
	}

}
