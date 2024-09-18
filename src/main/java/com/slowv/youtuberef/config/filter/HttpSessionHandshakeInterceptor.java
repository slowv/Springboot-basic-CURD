package com.slowv.youtuberef.config.filter;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class HttpSessionHandshakeInterceptor implements HandshakeInterceptor {

    public static final String IP_ADDRESS = "IP_ADDRESS";

    @Override
    public boolean beforeHandshake(final ServerHttpRequest request, final ServerHttpResponse response, final WebSocketHandler wsHandler, final Map<String, Object> attributes) throws Exception {
        if (request instanceof final ServletServerHttpRequest servletRequest) {
            attributes.put(IP_ADDRESS, servletRequest.getRemoteAddress());
        }
        return true;
    }

    @Override
    public void afterHandshake(final ServerHttpRequest request, final ServerHttpResponse response, final WebSocketHandler wsHandler, final Exception exception) {

    }
}
