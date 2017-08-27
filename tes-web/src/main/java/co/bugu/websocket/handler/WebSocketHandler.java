package co.bugu.websocket.handler;

import co.bugu.tes.model.Answer;
import co.bugu.tes.model.question.CommonQuestion;
import co.bugu.tes.service.IAnswerService;
import co.bugu.tes.service.ICommonQuestionService;
import co.bugu.websocket.MessageEnum;
import co.bugu.websocket.WebSocketSessionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2017/3/23.
 */
public class WebSocketHandler extends TextWebSocketHandler {
    private Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    @Autowired
    ICommonQuestionService questionService;
    @Autowired
    IAnswerService answerService;

    //建立连接后执行
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.debug("ConnectionEstablished");
        session.sendMessage(new TextMessage("连接成功"));

        String sessionId = (String) session.getAttributes().get("HTTP.SESSION.ID");
        Integer userId = (Integer) session.getAttributes().get("userId");
        WebSocketSessionUtil.add(userId, session);

    }

    /**
     * 处理消息
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        Map<String, Object> res = new HashMap();
        Integer length = message.getPayloadLength();
        String content = message.getPayload().toString();
        if(StringUtils.isEmpty(content)){
            return;
        }
        JSONObject jsonObject = JSON.parseObject(content);
        Integer type = jsonObject.getInteger("type");
        if(type == MessageEnum.GET_QUESTION.getType()){
            Integer questionId = jsonObject.getInteger("questionId");
            CommonQuestion question = questionService.findById(questionId);
            if(question == null){
                res.put("code", -1);
                res.put("msg", "没有查到对应的题目");
            }else{
                res.put("code", 0);
                res.put("title", question.getTitle());
                res.put("content", question.getContent());
                res.put("metaInfoId", question.getMetaInfoId());
                res.put("extraInfo", question.getExtraInfo());
            }
        }else if(type == MessageEnum.COMMIT_QUESTION.getType()){
            Integer questionId = jsonObject.getInteger("questionId");
            String timeLeft = jsonObject.getString("timeLeft");//定时器经过的时间 s
            String answerInfo = jsonObject.getString("answer");
            Integer paperId = jsonObject.getInteger("paperId");
            Answer answer = new Answer();
            answer.setPaperId(paperId);
            answer.setQuestionId(questionId);
            answer.setAnswer(answerInfo);
            answer.setTimeLeft(timeLeft);
            answerService.save(answer);

        }else if(type == MessageEnum.COMMIT_PAPER.getType()){
            Integer paperId = jsonObject.getInteger("paperId");
            String answerInfo = jsonObject.getString("answerInfo");
            if(StringUtils.isEmpty(answerInfo)){
                Map<String, String> map = JSON.parseObject(answerInfo, Map.class);
                answerService.savePaperAnswer(map, paperId);
            }

        }else if(type == MessageEnum.FORSE_COMMIT_PAPER.getType()){
            logger.info("此处消息应该是服务端在考试结束后发往客户端，客户端发起非法");
        }else{
            logger.info("无效消息");
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        logger.debug("handleTransportError" + exception.getMessage());
    }

    /**
     * 连接关闭后执行
     * @param session
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String sessionId = (String) session.getAttributes().get("HTTP.SESSION.ID");
        Integer userId = (Integer) session.getAttributes().get("userId");
        WebSocketSessionUtil.remove(userId, session);
        logger.debug("afterConnectionClosed" + closeStatus.getReason());

    }

    /**
     * 支持部分信息
     * @return
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
