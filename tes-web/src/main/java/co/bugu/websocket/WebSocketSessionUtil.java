package co.bugu.websocket;

import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daocers on 2017/3/25.
 */
public class WebSocketSessionUtil {
    private static Map<Integer, WebSocketSession> sessionMap = new HashMap<>();

    public static void add(Integer userId, WebSocketSession session) {
        sessionMap.put(userId, session);
    }

    public static void remove(Integer userId, WebSocketSession session) {
        sessionMap.remove(userId);
    }

    public static WebSocketSession getWebSocketSession(Integer userId) {
        return sessionMap.get(userId);
    }

    public static Map<Integer, WebSocketSession> getAllWebSocketSessions() {
        return sessionMap;
    }
}
