package co.bugu.tes.enums;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by user on 2017/2/20.
 */
public enum SceneStatusEnum {
    /**
     * 参数不完善或者其他，反正在参数设置完成，参考人员选择完成，试卷策略选择，试卷生成成功之前都为0，等待状态
     * 已处理完成，等待开场叫做 就绪状态 1
     * 当前时间大于考试开始时间且小于考试结束时间叫做begin，已开场
     * 当前时间大于考试结束时间，结束 3
     * 人工取消本场考试，取消状态，需要填写取消原因
     */
    WAITING(0, "待完善"), READY(1, "创建成功"), BEGIN(2, "已开场"), END(3, "已封场"), CANCEL(4, "已取消");
    private Integer status;
    private String info;
    private static Map<Integer, String> statusInfo = new TreeMap<>();

    SceneStatusEnum(Integer status, String info) {
        this.status = status;
        this.info = info;
    }

    public Integer getStatus() {
        return status;
    }

    public String getInfo() {
        return info;
    }

    static {
        SceneStatusEnum[] enums = SceneStatusEnum.values();
        for (int i = 0; i < enums.length; i++) {
            SceneStatusEnum statusEnum = enums[i];
            statusInfo.put(statusEnum.getStatus(), statusEnum.getInfo());
        }
    }

    public static Map<Integer, String> getStatusInfo() {
        return statusInfo;
    }
}
