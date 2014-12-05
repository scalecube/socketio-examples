package org.socketio.netty.example.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socketio.netty.ISession;
import org.socketio.netty.ISocketIOListener;

public class EchoSocketIOListener implements ISocketIOListener {
	
	private static final Logger log = LoggerFactory.getLogger(EchoSocketIOListener.class);
	
	@Override
	public void onConnect(final ISession client) {
		log.debug("{}/{}: onConnect", client.getSessionId(), client.getLocalPort());
	}

	@Override
	public void onJsonObject(final ISession client, final Object obj) {
		log.debug("{}/{}: onJsonObject: {}", client.getSessionId(), client.getLocalPort(), obj);
		processReceivedMessage(client, obj.toString());
	}

	@Override
	public void onMessage(final ISession client, final String message) {
		log.debug("{}/{}: onMessage: {}", client.getSessionId(), client.getLocalPort(), message);
		processReceivedMessage(client, message);
	}
	
	private void processReceivedMessage(final ISession client, final String message) {
        client.send(message);
	}

	@Override
	public void onDisconnect(final ISession client) {
		log.debug("{}/{}: onDisconnect", client.getSessionId(), client.getLocalPort());
	}

}
