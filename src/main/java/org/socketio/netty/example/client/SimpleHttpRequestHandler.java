package org.socketio.netty.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleHttpRequestHandler extends MessageToMessageDecoder {
	
	private static final Logger log = LoggerFactory.getLogger(SimpleHttpRequestHandler.class);
	private static final String BASE_PATH = "/WEB-INF";
	
	private final String appPath;
	
	public SimpleHttpRequestHandler(final String basePath) {
		this.appPath = basePath;
	}

	private StringBuilder getResourceContent(final String path) throws IOException {
		log.info("Requested Resource: {}", path);
		URL resourceFile = getClass().getResource(BASE_PATH + path);
		if (resourceFile == null) {
			return null;
		}
		InputStreamReader reader = new InputStreamReader(resourceFile.openStream());
		BufferedReader bufread = new BufferedReader(reader);
		String read;
		StringBuilder content = new StringBuilder();
		while ((read = bufread.readLine()) != null) {
			content.append(read + "\r\n");
		}
		return content;
	}
	
	public void writeResponse(final Channel channel, final HttpRequest request,final StringBuilder responseContent ) {
		// Convert the response content to a ChannelBuffer.
		ByteBuf buf = Unpooled.copiedBuffer(responseContent, Charset.forName("UTF-8"));
		responseContent.setLength(0);
		
		// Decide whether to close the connection or not.
		final boolean close = isClose(request);

		// Build the response object.
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
		response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");

		if (!close) {
			// There's no need to add 'Content-Length' header
			// if this is the last response.
			response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, String.valueOf(buf.readableBytes()));
		}

		String cookieString = request.headers().get(HttpHeaders.Names.COOKIE);
		if (cookieString != null) {
			Set<Cookie> cookies = CookieDecoder.decode(cookieString);
			if (!cookies.isEmpty()) {
				// Reset the cookies if necessary.
				response.headers().add(HttpHeaders.Names.SET_COOKIE, ServerCookieEncoder.encode(cookies));
			}
		}

		// Write the response.
		ChannelFuture future = channel.write(response);

		// Close the connection after the write operation is done if necessary.
		if (close) {
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}
	
	
	private boolean isClose(final HttpRequest request) {
		return HttpHeaders.Values.CLOSE.equalsIgnoreCase(request.headers().get(HttpHeaders.Names.CONNECTION)) ||
			request.getProtocolVersion().equals(HttpVersion.HTTP_1_0) &&
			!HttpHeaders.Values.KEEP_ALIVE.equalsIgnoreCase(request.headers().get(HttpHeaders.Names.CONNECTION));
	}

    @Override
    protected void decode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            QueryStringDecoder queryDecoder = new QueryStringDecoder(request.getUri());
            String path = queryDecoder.path();
            if (request.getUri().contains(appPath)) {
                StringBuilder content = getResourceContent(path);
                if (content != null) {
                    writeResponse(ctx.channel(), request, content);
                    return;
                }
            }
        }
        out.add(msg);
    }
}
