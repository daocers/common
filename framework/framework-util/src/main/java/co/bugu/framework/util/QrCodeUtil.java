package co.bugu.framework.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by daocers on 2016/9/15.
 * 二维码
 */
public class QrCodeUtil {
    private static Logger logger = LoggerFactory.getLogger(QrCodeUtil.class);

    private static final int IMAGE_WIDTH = 80;
    private static final int IMAGE_HEIGTH = 80;
    private static final int IMAGE_HALF_WIDTH = IMAGE_WIDTH /2;
    private static final int FRAME_WIDTH = 2;

//    二维码写码器
    private static MultiFormatWriter multiWriter = new MultiFormatWriter();

    public static void encode(String content, int width, int height,
                              String srcImagePath, String destImagePath){
        try{
            ImageIO.write(genBarCode(content, width, height, srcImagePath), "jpg", new File(destImagePath));
        }catch (IOException e){
            logger.error("生成二维码失败", e);
        } catch (WriterException e) {
            logger.error("写入二维码失败", e);
        }
    }

    private static BufferedImage genBarCode(String content, int width, int height, String srcImagePath) throws WriterException, IOException {
        //读取原图像
        BufferedImage scaleImage = scale(srcImagePath, IMAGE_WIDTH, IMAGE_HEIGTH, true);
        int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGTH];
        for(int i = 0; i < scaleImage.getWidth(); i++){
            for(int j = 0; j < scaleImage.getHeight(); j++){
                srcPixels[i][j] = scaleImage.getRGB(i, j);
            }
        }

        Map<EncodeHintType, Object> hint = new HashMap<>();
        hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //生成二维码
        BitMatrix matrix = multiWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hint);

//       二维矩阵转为一维像素数组
        int halfW = matrix.getWidth() / 2;
        int halfH = matrix.getHeight() / 2;
        int[] pixels = new int[width * height];

        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {
                // 读取图片
                if (x > halfW - IMAGE_HALF_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH
                        && y < halfH + IMAGE_HALF_WIDTH) {
                    pixels[y * width + x] = srcPixels[x - halfW
                            + IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];
                }
                // 在图片四周形成边框
                else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)
                        || (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)
                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        - IMAGE_HALF_WIDTH + FRAME_WIDTH)
                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)) {
                    pixels[y * width + x] = 0xfffffff;
                } else {
                    // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
                    pixels[y * width + x] = matrix.get(x, y) ? 0xff000000
                            : 0xfffffff;
                }
            }
        }
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        image.getRaster().setDataElements(0, 0, width, height, pixels);
        return image;
    }

    /**
     * 把传入的原始图像按高度和宽度进行缩放，生成符合要求的图标
     *
     * @param srcImageFile
     *            源文件地址
     * @param height
     *            目标高度
     * @param width
     *            目标宽度
     * @param hasFiller
     *            比例不对时是否需要补白：true为补白; false为不补白;
     * @throws IOException
     */
    private static BufferedImage scale(String srcImageFile, int width, int height, boolean hasFiller) throws IOException {
        double ratio = 0.0; // 缩放比例
        File file = new File(srcImageFile);
        BufferedImage srcImage = ImageIO.read(file);
        Image destImage = srcImage.getScaledInstance(width, height,
                BufferedImage.SCALE_SMOOTH);
        // 计算比例
        if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {
            if (srcImage.getHeight() > srcImage.getWidth()) {
                ratio = (new Integer(height)).doubleValue()
                        / srcImage.getHeight();
            } else {
                ratio = (new Integer(width)).doubleValue()
                        / srcImage.getWidth();
            }
            AffineTransformOp op = new AffineTransformOp(
                    AffineTransform.getScaleInstance(ratio, ratio), null);
            destImage = op.filter(srcImage, null);
        }
        if (hasFiller) {// 补白
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D graphic = image.createGraphics();
            graphic.setColor(Color.white);
            graphic.fillRect(0, 0, width, height);
            if (width == destImage.getWidth(null))
                graphic.drawImage(destImage, 0,
                        (height - destImage.getHeight(null)) / 2,
                        destImage.getWidth(null), destImage.getHeight(null),
                        Color.white, null);
            else
                graphic.drawImage(destImage,
                        (width - destImage.getWidth(null)) / 2, 0,
                        destImage.getWidth(null), destImage.getHeight(null),
                        Color.white, null);
            graphic.dispose();
            destImage = image;
        }
        return (BufferedImage) destImage;
    }

    public static void main(String[] args){
        String tar = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb4eaf9e9d8c8c2ba" +
                "&redirect_uri=http://520622a1.nat123.net/payment/notify.do" +
                "&response_type=code&scope=snsapi_userinfo" +
                "&state=1#wechat_redirect";
//        QrCodeUtil.encode(tar, 320, 320, "g:/a.png", "g:/dest.jpg");
//        QrCodeUtil.encode("http://520622a1.nat123.net/payment/notify.do?pa=a", 320, 320, "G:/a.png", "g:/dest.jpg");
        QrCodeUtil.createQrcode(tar);
    }

    public static String createQrcode(String msg){
        String qrcodeFilePath = "";
        try{
            int width = 300;
            int heigth = 300;
            String format = "png";
            HashMap<EncodeHintType, String> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(
                    msg, BarcodeFormat.QR_CODE, width, heigth, hints);
            BufferedImage image = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
            Random random = new Random();
            File qrcodeFile = new File("e://qrcode/qr" + random.nextInt() + "." + format);
            qrcodeFile.mkdirs();
            ImageIO.write(image, format, qrcodeFile);
            MatrixToImageWriter.writeToPath(bitMatrix, format, FileSystems.getDefault().getPath(qrcodeFile.getAbsolutePath()));
            qrcodeFilePath = qrcodeFile.getAbsolutePath();
            System.out.println(qrcodeFilePath);
        }catch (Exception e){
            logger.error("生成二维码失败", e);
        }
        return null;
    }
}
