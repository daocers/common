package co.bugu.websocket.domain;

/**
 * Created by daocers on 2017/3/25.
 * WebSocket 传递的消息信息
 * json 格式
 */
public class WebSocketMessage {
    private Integer type;
    private String content;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
