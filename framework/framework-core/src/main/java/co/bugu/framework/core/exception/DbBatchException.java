package co.bugu.framework.core.exception;

/**
 * Created by daocers on 2016/9/20.
 */
public class DbBatchException extends Exception {
    public DbBatchException(){
        super();
    }

    public DbBatchException(String msg){
        super(msg);
    }

    public DbBatchException(String msg, Exception e){
        super(msg, e);
    }


}
