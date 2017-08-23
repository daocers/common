package co.bugu.tes.enums;

/**
 * Created by user on 2017/1/20.
 */
public enum BranchLevel {
    ZONGHANG(0), STAIR(1), SECOND(2), THIRD(3), FORTH(4);
    private Integer level;

    BranchLevel(Integer level) {
        this.level = level;
    }

    public Integer getLevel() {
        return level;
    }
}
