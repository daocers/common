package co.bugu.framework.core.util;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by daocers on 2016/5/26.
 * 序列化对象，采用该类的对象必须implement Serializable。
 */
public class SerializeUtil {
    private static Logger logger = LoggerFactory.getLogger(SerializeUtil.class);

    public static byte[] serialize(Object object) throws IOException {
        if(object == null){
            logger.warn("不能为null进行序列化操作");
            throw new NullPointerException("尝试为空对象进行序列化操作");
        }

        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try{
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(object);
            bytes = bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("不能被序列化的对象", e);
        }finally {
            if(os != null){
                os.close();
            }
            if(bos != null){
                bos.close();
            }
        }
        return  bytes;
    }


    public static <T extends Serializable> T deSerialize(byte[] bytes) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        try{
            ois = new ObjectInputStream(bis);
            return (T) ois.readObject();
        } catch (ClassNotFoundException e) {
            logger.warn("反序列化异常", e);
        } catch (IOException e) {
            logger.warn("反序列化异常", e);
        }finally {
            if(bis != null){
                bis.close();
            }
            if(ois != null){
                ois.close();
            }
        }
        return null;
    }
}
