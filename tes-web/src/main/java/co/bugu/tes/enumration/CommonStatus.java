package co.bugu.tes.enumration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daocers on 2017/8/21.
 */
public enum CommonStatus {
    ENABLE(1, "启用"), DISABLE(-1, "禁用");

    private Integer status;
    private String info;

    private static Map<Integer, String> map = new HashMap<>();

    static {
        CommonStatus[] cs = CommonStatus.values();
        for(CommonStatus s: cs){
            map.put(s.status, s.info);
        }
    }

    CommonStatus(Integer status, String info){
        this.status = status;
        this.info = info;
    }


    public Integer getStatus(){
        return this.status;
    }

    public Map<Integer, String> getStatusInfo(){
        return map;
    }
}
