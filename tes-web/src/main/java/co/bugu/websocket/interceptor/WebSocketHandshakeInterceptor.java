package co.bugu.websocket.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by user on 2017/3/23.
 */
public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    private Logger logger = LoggerFactory.getLogger(WebSocketHandshakeInterceptor.class);
    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler handler, Map<String, Object> attributes) throws Exception {
        HttpSession session = ((ServletServerHttpRequest) request).getServletRequest().getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if(userId == null){
             ((ServletServerHttpResponse)response).getServletResponse().sendRedirect("/login.do");
             return false;
        }
        if(request.getHeaders().containsKey("Sec-WebSocket-Extensions")){
            request.getHeaders().set("Sec-WebSocket-Extensions",
                    "permessage-deflate");
        }

        logger.debug("before handshake");
        return super.beforeHandshake(request, response, handler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        logger.debug("after handshake");
        super.afterHandshake(serverHttpRequest, serverHttpResponse, webSocketHandler, e);
    }
}
