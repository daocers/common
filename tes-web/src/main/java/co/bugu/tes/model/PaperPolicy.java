package co.bugu.tes.model;

import java.util.Date;
import java.util.List;

public class PaperPolicy {
    private Integer id;

    private Integer branchId;

    private String code;

    private String questionMetaInfoId;

    private String content;

    private Date createTime;

    private Integer createUserId;

    private Integer departmentId;

    private String name;

    private Integer stationId;

    private Integer status;

    private Date updateTime;

    private Integer count;

//试卷总分
    private Double score;
    /**
     * 选题方式 0 普通模式， 1 策略模式
     */
    private Integer selectType;

    /**
     * 保密类型， 0 公开， 1 保密
     */
    private Integer privaryType;


    private Integer updateUserId;

    /**
     * 是否百分制 0 是， 1 否
     */
    private Integer percentable;
    private List<QuestionPolicy> questionPolicyList;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSelectType() {
        return selectType;
    }

    public void setSelectType(Integer selectType) {
        this.selectType = selectType;
    }

    public List<QuestionPolicy> getQuestionPolicyList() {
        return questionPolicyList;
    }

    public void setQuestionPolicyList(List<QuestionPolicy> questionPolicyList) {
        this.questionPolicyList = questionPolicyList;
    }


    public Integer getPercentable() {
        return percentable;
    }

    public void setPercentable(Integer percentable) {
        this.percentable = percentable;
    }

    public Integer getPrivaryType() {
        return privaryType;
    }

    public void setPrivaryType(Integer privaryType) {
        this.privaryType = privaryType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getQuestionMetaInfoId() {
        return questionMetaInfoId;
    }

    public void setQuestionMetaInfoId(String questionMetaInfoId) {
        this.questionMetaInfoId = questionMetaInfoId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }
}