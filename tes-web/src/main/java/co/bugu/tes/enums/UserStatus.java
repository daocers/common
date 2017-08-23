package co.bugu.tes.enums;

/**
 * Created by user on 2017/1/19.
 */
public enum  UserStatus {
    /**
     * 0 可用
     * 1 禁用
     * 2 需要完善信息
     */
    ENABLE(0), DISABLE(1), NEEDINFO(2);
    private Integer status;

    private UserStatus(Integer status){
        this.status = status;
    }

    public Integer getStatus(){
        return this.status;
    }
}
