package co.bugu.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by daocers on 2016/7/4.
 */
public class ZoomUtil {
    private static Logger logger = LoggerFactory.getLogger(ZoomUtil.class);

    /**
     * 按照固定宽高进行缩放
     * @param src
     * @param dest
     * @param width
     * @param height
     * @param  inRatio 是否等比例缩放，如果不是，图片按照指定的长宽，可能失真，如果是，按照等比例，
     * @throws IOException
     */
    public static void zoomImage(String src, String dest, int width, int height, Boolean inRatio) throws IOException {

        double wr = 0, hr = 0;
        File srcFile = new File(src);
        File destFile = new File(dest);

        BufferedImage bufImg = ImageIO.read(srcFile);
        Image itemp = bufImg.getScaledInstance(width, height, bufImg.SCALE_SMOOTH);
        wr = width * 1.0 / bufImg.getWidth();
        hr = height * 1.0 / bufImg.getHeight();

        if(inRatio != null && inRatio == true){
            wr = width * 1.0 / bufImg.getWidth();
            hr = height * 1.0 / bufImg.getHeight();
            if(wr > hr){
                hr = wr;
            }else{
                wr = hr;
            }
        }

        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        itemp = ato.filter(bufImg, null);
        try{
            //写入缩减后的文件
            ImageIO.write((RenderedImage) itemp, dest.substring(dest.lastIndexOf(".") + 1), destFile);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 按照图片大小进行缩放
     * @param src
     * @param dest
     * @param size 单位kb， 当图片大于size kb时候，开始缩放
     * @throws IOException
     */
    public static void zoomImage(String src, String dest, Integer size) throws IOException {
        File srcFile = new File(src);
        File destFile = new File(dest);

        long fileSize = srcFile.length();
        if(fileSize < size * 1024)   //文件大于size k时，才进行缩放
            return;

        Double rate = (size * 1024 * 0.5) / fileSize; // 获取长宽缩放比例

        BufferedImage bufImg = ImageIO.read(srcFile);
        Image Itemp = bufImg.getScaledInstance(bufImg.getWidth(), bufImg.getHeight(), bufImg.SCALE_SMOOTH);

        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(rate, rate), null);
        Itemp = ato.filter(bufImg, null);
        try {
            ImageIO.write((BufferedImage) Itemp,dest.substring(dest.lastIndexOf(".")+1), destFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
