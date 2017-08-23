package co.bugu.tes.enums;

/**
 * 状态枚举类
 * Created by user on 2017/1/20.
 */
public enum StatusEnum {
    ENABLE(0), DISABLE(1);
    private Integer status;

    StatusEnum(Integer status){
        this.status = status;
    }

    public Integer getStatus(){
        return this.status;
    }
}
