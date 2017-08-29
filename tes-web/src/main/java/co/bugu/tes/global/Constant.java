package co.bugu.tes.global;

/**
 * Created by daocers on 2016/8/2.
 */
public interface Constant {
    Integer AUTH_TYPE_MENU = 0;
    Integer AUTH_TYPE_OPR = 1;
    Integer AUTH_TYPE_BOX = 2;

    Integer AUTH_API_TRUE = 0;
    Integer AUTH_API_FALSE = 1;

    /*试题答案正误*/
    Integer ANSWER_TRUE = 0;
    Integer ANSWER_FALSE = 1;


    /**
     * 保存指定题型的 对应属性的题目的数量
     * 使用时候后面需要加上题型的id作为key，存储在redis上
     */
    /**
     * 用户角色
     * */
    String USER_ROLES = "USER_ROLES_";

    /**
     * 用户权限
     * */
    String USER_AUTHORITYS = "USER_AUTHORITY_";


    /**
    * 用于存储试题属性，题库，题型之类的信息
     * 试卷生成选择试题时候使用
    * */
    String QUESTION_ATTR_INFO = "QUESTION_ATTR_INFO";
    String DEFALUT_SALT = "ABC";
    String DEFAULT_PASSWORD = "888888";
    String QUESTION_ANSWER = "QUESTION_ANSWER";
    String STATION_INFO = "STATION_INFO";
    String DEPARTMENT_INFO = "DEPARTMENT_INFO";
}
