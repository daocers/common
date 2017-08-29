package co.bugu.tes.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2017/6/7.
 */
public enum PaperStatusEnum {

    ENABLE(1, "正常"), DISABLE(-1, "删除"), DELETE(0, "禁用"), COMMITED(3, "已提交");

    private Integer status;

    private String info;

    private static Map<Integer, String> map = new HashMap<>();


    public Integer getStatus() {
        return status;
    }

    public String getInfo(){
        return info;
    }

    PaperStatusEnum(Integer status, String info){
        this.status = status;
        this.info = info;
    }

    static {
        PaperStatusEnum[] enums = PaperStatusEnum.values();
        for(PaperStatusEnum e: enums){
            map.put(e.getStatus(), e.getInfo());
        }
    }
}
