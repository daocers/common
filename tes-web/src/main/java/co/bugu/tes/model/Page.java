package co.bugu.tes.model;

/**
 * 页面信息
 */
public class Page {
    private Integer id;

    private String code;

    private Integer tradeId;

    private Integer status;

    private String url;
    /**
     * 用于排序，表示在一个交易中出现的先后顺序
     */
    private Integer idx;

    /**
     * 用于记录当前页面的栏位信息，包括字段的label， name， val，是否是考核字段等。
     */
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}