package co.bugu.tes;

/**
 * Created by daocers on 2017/8/27.
 * 数据异常
 * 试题不足异常，
 * 数据不完整异常
 */
public class DataException extends Exception {
    public DataException(){
        super();
    }

    public DataException(String error){
        super(error);
    }

    public DataException(String error, Exception e){
        super(error, e);
    }
}
