package co.bugu.framework.core.exception;

/**
 * Created by QDHL on 2017/8/2.
 *
 * jedis异常信息
 */
public class TesJedisException extends Exception {
    public TesJedisException(){
        super();
    }

    public TesJedisException(String error){
        super(error);
    }

    public TesJedisException(String error, Exception e){
        super(error, e);
    }

}
