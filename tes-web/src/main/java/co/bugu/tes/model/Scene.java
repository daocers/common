package co.bugu.tes.model;

import java.util.Date;

public class Scene {
    private Integer id;

    private String authCode;

    private Date beginTime;

    private Integer branchId;

    private Integer changePaper;

    private String code;

    private Date createTime;

    private Integer createUserId;

    private Integer delay;

    private Integer departmentId;

    private Integer stationId;

    private Integer bankId;

    private Integer duration;

    private Date endTime;

    private String name;

    private Integer paperPolicyId;

    private String reason;

    private String remark;

    private Integer status;

    private Date updateTime;

    private Integer updateUserId;

    private Integer paperType;

    private String joinUser;

    private Department department;
    private Branch branch;
    private Station station;

    private User createUser;
    private User updateUser;

    private Integer userType;
    private String choiceInfo;

    private Double totalScore;
    private String metaScoreInfo;
    private Integer percentable;

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public String getMetaScoreInfo() {
        return metaScoreInfo;
    }

    public void setMetaScoreInfo(String metaScoreInfo) {
        this.metaScoreInfo = metaScoreInfo;
    }

    public Integer getPercentable() {
        return percentable;
    }

    public void setPercentable(Integer percentable) {
        this.percentable = percentable;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getChoiceInfo() {
        return choiceInfo;
    }

    public void setChoiceInfo(String choiceInfo) {
        this.choiceInfo = choiceInfo;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getJoinUser() {
        return joinUser;
    }

    public void setJoinUser(String joinUser) {
        this.joinUser = joinUser;
    }

    public Integer getPaperType() {
        return paperType;
    }

    public void setPaperType(Integer paperType) {
        this.paperType = paperType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getChangePaper() {
        return changePaper;
    }

    public void setChangePaper(Integer changePaper) {
        this.changePaper = changePaper;
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

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPaperPolicyId() {
        return paperPolicyId;
    }

    public void setPaperPolicyId(Integer paperPolicyId) {
        this.paperPolicyId = paperPolicyId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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