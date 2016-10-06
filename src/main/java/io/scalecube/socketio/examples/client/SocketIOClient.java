package io.scalecube.socketio.examples.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslHandler;
import io.scalecube.socketio.pipeline.ResourceHandler;


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
    bootstrap = new ServerBootstrap().group(new NioEventLoopGroup()).channel(NioServerSocketChannel.class);

    // Set up the event pipeline factory.
    bootstrap.childHandler(new ChannelInitializer() {
      @Override
      protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // SSL
        if (isSSL()) {
          SSLEngine sslEngine = sslContext.createSSLEngine();
          sslEngine.setUseClientMode(false);
          SslHandler sslHandler = new SslHandler(sslEngine);
          //
          pipeline.addLast("ssl", sslHandler);
        }

        // HTTP
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
        pipeline.addLast("encoder", new HttpResponseEncoder());

        // Flash resources
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.addResource(appPath + "/js/socket.io/WebSocketMain.swf", "/WEB-INF/client/js/socket.io/WebSocketMain.swf");
        resourceHandler.addResource(appPath + "/js/socket.io/WebSocketMainInsecure.swf", "/WEB-INF/client/js/socket.io/WebSocketMainInsecure.swf");
        pipeline.addLast("flashResources", resourceHandler);

        // Web application
        pipeline.addLast("httpPage", new HttpRequestHandler(appPath));

      }
    });

    // Bind and start to accept incoming connections.
    InetSocketAddress addr = new InetSocketAddress(port);
    bootstrap.bind(addr);

    log.info("Socket.IO client started at: {}://localhost:{}{}/index.html",
        getProtocol(), port, appPath);
  }

  public void stop() {
    bootstrap.group().shutdownGracefully();
    log.info("Socket.IO client stopped at: {}://localhost:{}{}/index.html",
        getProtocol(), port, appPath);
  }

  private String getProtocol() {
    return isSSL() ? "https" : "http";
  }

  private boolean isSSL() {
    return sslContext != null;
  }
}
