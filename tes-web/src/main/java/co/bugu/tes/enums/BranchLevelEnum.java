package co.bugu.tes.enums;

/**
 * Created by user on 2017/1/20.
 */
public enum BranchLevelEnum {
    ZONGHANG(0), FIRST(1), SECOND(2), THIRD(3), FORTH(4);
    private Integer level;

    BranchLevelEnum(Integer level) {
        this.level = level;
    }

    public Integer getLevel() {
        return level;
    }
}
