package co.bugu.tes.enums;

/**
 * Created by user on 2017/2/14.
 */
public enum  PaperPolicyType {
    /**
     * 1 指定试题数量
     * 2 选择试题策略，细粒度控制试卷题目分布
     */
    COMMON(0), POLICY(1);
    PaperPolicyType(Integer type){
        this.type = type;
    }
    private Integer type;

    public Integer getType(){
        return type;
    }
}
