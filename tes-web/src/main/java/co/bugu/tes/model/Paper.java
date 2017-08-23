package co.bugu.tes.model;

import java.util.Date;

public class Paper {
    private Integer id;

    private Integer answerFlag;

    private Date beginTime;

    private Date endTime;

    private String mark;

    private Integer sceneId;

    private Integer status;

    private Integer userId;

    private String content;

    private String questionIds;

    /**
     * 没有百分制之前的成绩
     */
    private String originMark;

    public String getOriginMark() {
        return originMark;
    }

    public void setOriginMark(String originMark) {
        this.originMark = originMark;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(String questionIds) {
        this.questionIds = questionIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnswerFlag() {
        return answerFlag;
    }

    public void setAnswerFlag(Integer answerFlag) {
        this.answerFlag = answerFlag;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}