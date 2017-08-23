package co.bugu.tes.enums;

/**
 * Created by user on 2017/2/14.
 */
public enum PaperType {
    /**
     * 1 随机生成
     * 2 统一试卷，所有考生试卷题目一致， 生成后预览可以调整试题
     * 3 教师导入试题，所有考生题目一致
     * 4 深度随机，不指定试题策略，直接指定需要的试题数量，随机获取
     * 5 深度统一，不指定试题策略，直接指定需要的试题数量，随机获取，每个人试卷一致
     */
    RANDOM(1), UNIFY(2), IMPORT(3), DEEPRANDOM(4), DEEPUNIFY(5);
    PaperType(Integer type){
        this.type = type;
    }
    private Integer type;

    public Integer getType(){
        return type;
    }
}
