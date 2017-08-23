package co.bugu.tes.enums;

/**
 * Created by user on 2017/2/13.
 */
public enum SceneStatus {
    /**
     * 参数不完善或者其他，反正在参数设置完成，参考人员选择完成，试卷策略选择，试卷生成成功之前都为0，等待状态
     * 已处理完成，等待开场叫做 就绪状态 1
     * 当前时间大于考试开始时间且小于考试结束时间叫做begin，已开场
     * 当前时间大于考试结束时间，结束 3
     * 人工取消本场考试，取消状态，需要填写取消原因
     */
    WAITING(0), READY(1), BEGIN(2), END(3), CANCEL(4);
    private Integer status;

    SceneStatus(Integer status){
        this.status = status;
    }
    public Integer getStatus(){
        return status;
    }
}
