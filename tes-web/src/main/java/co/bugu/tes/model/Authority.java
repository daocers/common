package co.bugu.tes.model;

public class Authority {
    private Integer id;

    private String action;

    private String controller;

    private String description;

    private String name;

    private String code;

    private String parameter;

    private Integer status;

    private Integer superiorId;

    private Integer type;

    private String url;

    private String acceptMethod;

    private Boolean isApi;
    private Boolean isView;
    private Boolean isBox;
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    private Integer idx;


    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public boolean isApi() {
        return isApi;
    }

    public void setApi(boolean api) {
        isApi = api;
    }

    public boolean isView() {
        return isView;
    }

    public void setView(boolean view) {
        isView = view;
    }

    public boolean isBox() {
        return isBox;
    }

    public void setBox(boolean box) {
        isBox = box;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(Integer superiorId) {
        this.superiorId = superiorId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAcceptMethod() {
        return acceptMethod;
    }

    public void setAcceptMethod(String acceptMethod) {
        this.acceptMethod = acceptMethod;
    }
}