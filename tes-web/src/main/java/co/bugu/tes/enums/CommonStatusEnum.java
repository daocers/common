package co.bugu.tes.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2017/6/7.
 */
public enum CommonStatusEnum {

    ENABLE(0, "正常"), DISABLE(1, "禁用"), DELETE(2, "已删除");

    private Integer status;

    private String info;

    private static Map<Integer, String> map = new HashMap<>();


    public Integer getStatus() {
        return status;
    }

    public String getInfo(){
        return info;
    }

    CommonStatusEnum(Integer status, String info){
        this.status = status;
        this.info = info;
    }

    static {
        CommonStatusEnum[] enums = CommonStatusEnum.values();
        for(CommonStatusEnum e: enums){
            map.put(e.getStatus(), e.getInfo());
        }
    }
}
