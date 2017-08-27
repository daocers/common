package co.bugu.tes.global;

/**
 * Created by daocers on 2016/8/2.
 */
public interface Constant {
    Integer STATUS_ENABLE = 0;
    Integer STATUS_DISABLE = 1;
    Integer STATUS_DELETE = 2;

    Integer AUTH_TYPE_MENU = 0;
    Integer AUTH_TYPE_OPR = 1;
    Integer AUTH_TYPE_BOX = 2;

    Integer AUTH_API_TRUE = 0;
    Integer AUTH_API_FALSE = 1;

    Integer BRANCH_LEVEL_TOP = 0;//总行
    Integer BRANCH_LEVEL_ONE = 1;
    Integer BRANCH_LEVEL_TWO = 2;
    Integer BRANCH_LEVEL_THREE = 3;
    Integer BRANCH_LEVEL_FOUR = 4;

    /*试题答案正误*/
    Integer ANSWER_TRUE = 0;
    Integer ANSWER_FALSE = 1;


    /**
     * 保存指定题型的 对应属性的题目的数量
     * 使用时候后面需要加上题型的id作为key，存储在redis上
     */
    String METAINFO_PROP_COUNT = "MetaInfo_propItem_count_";


    String USER_INFO_PREFIX = "USER_INFO_";

    String SESSION_USER_ID = "userId";

    String QUESTION_PROPITEM_ID = "QUESTION_PROPITEM_ID_";

    String QUESTION_BANK_ID = "BANK_";

    String QUESTION_PERFIX = "QUESTION_PREFIX_";

    /**
     * 用户需要参加的场次列表
    * */
    String USER_SCENE_LIST = "USER_SCENE_LIST_";

    /**
     * 同一时刻开场的序号，避免场次编号重复
     * */
    String SCENE_INDEX = "SCENE_INDEX";

    /**
     * 用户角色
     * */
    String USER_ROLES = "USER_ROLES_";

    /**
     * 用户权限
     * */
    String USER_AUTHORITYS = "USER_AUTHORITY_";

    /**
     * 试题答案
     */
    String QUESTION_ANSWER = "QUESTION_ANSWER";

    String BRANCH_INFO = "BRANCH_INFO";

    String DEPARTMENT_INFO = "DEPARTMENT_INFO";

    String STATION_INFO = "STATION_INFO";


    String DEFAULT_PASSWORD = "888888";

    String DEFALUT_SALT = "ABC";
    String BRANCH_NAME_ID_INFO = "BRANCH_NAME_ID_INFO";
    String BRANCH_ID_NAME_INFO = "BRANCH_ID_NAME_INFO";

    /**
    * 用于存储试题属性，题库，题型之类的信息
     * 试卷生成选择试题时候使用
    * */
    String QUESTION_ATTR_INFO = "QUESTION_ATTR_INFO";
}
