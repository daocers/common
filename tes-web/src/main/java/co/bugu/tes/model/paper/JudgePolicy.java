package co.bugu.tes.model.paper;

/**
 * Created by QDHL on 2017/8/23.
 * 判卷策略：
 * 完全匹配，
 * 正确栏位百分比，
 * 错一个全错，不全对得部分分，
 */
public class JudgePolicy {
    private Integer id;
    private String name;
    private Integer questionMetaInfoId;
    private Integer status;
}
