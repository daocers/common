package co.bugu.framework.util.exception;

/**
 * Created by daocers on 2016/8/14.
 */
public class TesException extends Exception {
    public TesException(){
        super();
    }

    public TesException(String info){
        super(info);
    }

    public TesException(String info, Exception e){
        super(info, e);
    }
}
