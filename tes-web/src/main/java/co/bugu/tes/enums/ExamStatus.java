package co.bugu.tes.enums;

/**
 * Created by user on 2017/1/19.
 */
public enum  ExamStatus {
    /**
     * 0 正常， 1 缺考， 2 作弊未解冻
     */
    ENABLE(0), PASSED(1), CHEATFROZEN(2);
    private Integer status;

    ExamStatus(Integer status){
        this.status = status;
    }

    public Integer getStatus(){
        return status;
    }
}
