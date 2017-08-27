package co.bugu.tes.model.question;

import co.bugu.tes.model.PropertyItem;
import co.bugu.tes.model.QuestionMetaInfo;

import java.util.Date;
import java.util.List;

/**
 * Created by daocers on 2017/8/23.
 * 常规试题
 * 单选，多选，判断
 */
public class CommonQuestion {
    private Integer id;

    private String title;

    private String answer;

    private String content;

    private String extraInfo;

    private Integer metaInfoId;

    private Integer questionBankId;

    private Integer status;

    private Integer createUserId;

    private Integer branchId;

    private Integer departmentId;

    private Date createTime;

    private String propItemIdInfo;

    private Integer isPub;

    private Integer stationId;

    private List<PropertyItem> propertyItemList;

    public List<PropertyItem> getPropertyItemList() {
        return propertyItemList;
    }

    public void setPropertyItemList(List<PropertyItem> propertyItemList) {
        this.propertyItemList = propertyItemList;
    }

    public Integer getIsPub() {
        return isPub;
    }

    public Boolean isPub() {
        return isPub == 0;
    }

    public void setIsPub(Integer isPub) {
        this.isPub = isPub;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    private QuestionMetaInfo questionMetaInfo;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public Integer getMetaInfoId() {
        return metaInfoId;
    }

    public void setMetaInfoId(Integer metaInfoId) {
        this.metaInfoId = metaInfoId;
    }

    public Integer getQuestionBankId() {
        return questionBankId;
    }

    public void setQuestionBankId(Integer questionBankId) {
        this.questionBankId = questionBankId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPropItemIdInfo() {
        return propItemIdInfo;
    }

    public void setPropItemIdInfo(String propItemIdInfo) {
        this.propItemIdInfo = propItemIdInfo;
    }

    public QuestionMetaInfo getQuestionMetaInfo() {
        return questionMetaInfo;
    }

    public void setQuestionMetaInfo(QuestionMetaInfo questionMetaInfo) {
        this.questionMetaInfo = questionMetaInfo;
    }
}
