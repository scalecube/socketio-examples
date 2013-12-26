package org.socketio.netty.example.client;

import static org.jboss.netty.channel.Channels.pipeline;

import java.net.InetSocketAddress;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.http.HttpChunkAggregator;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.ssl.SslHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketIOClient {
	
	private static final Logger log = LoggerFactory.getLogger(SocketIOClient.class);
	
	private ServerBootstrap bootstrap;
	
	private final int port;
	private final String appPath;
	private final SSLContext sslContext;

	public SocketIOClient(final int port, String basePath) {
		this(port, basePath, null);
	}
	
	public SocketIOClient(final int port, String basePath, SSLContext sslContext) {
		this.port = port;
		this.appPath = basePath;
		this.sslContext = sslContext;
	}

	public void start() {
		// Configure the server.
		ChannelFactory channelFactory = new NioServerSocketChannelFactory();
		bootstrap = new ServerBootstrap(channelFactory);

		// Set up the event pipeline factory.
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = pipeline();
				
				// SSL
				if (isSSL()) {
					SSLEngine sslEngine = sslContext.createSSLEngine();
					sslEngine.setUseClientMode(false);
					SslHandler sslHandler = new SslHandler(sslEngine);
					sslHandler.setIssueHandshake(true);
					pipeline.addLast("ssl", sslHandler);
				}
				
				// HTTP
				pipeline.addLast("decoder", new HttpRequestDecoder());
				pipeline.addLast("aggregator", new HttpChunkAggregator(65536));
				pipeline.addLast("encoder", new HttpResponseEncoder());
				
				// Web application
				pipeline.addLast("httpPage", new SimpleHttpRequestHandler(appPath));
				
				return pipeline;
			}
		});

		// Bind and start to accept incoming connections.
		InetSocketAddress addr = new InetSocketAddress(port);
		bootstrap.bind(addr);
		
		log.info("Socket.IO client started at: {}://localhost:{}{}/index.html", 
				new Object[] {getProtocol(), port, appPath});
	}
	
	public void stop() {
		bootstrap.releaseExternalResources();
		log.info("Socket.IO client stopped at: {}://localhost:{}{}/index.html", 
				new Object[] {getProtocol(), port, appPath});
	}
	
	private String getProtocol() {
		return isSSL() ? "https" : "http";
	}
	
	private boolean isSSL() {
		return sslContext != null;
	}
}
