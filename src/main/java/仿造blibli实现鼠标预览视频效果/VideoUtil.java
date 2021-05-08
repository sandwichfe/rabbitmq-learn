package 仿造blibli实现鼠标预览视频效果;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @author user
 * @Description
 * @create 2021-05-08 15:02
 */
@Slf4j
public class VideoUtil {

    //创建画布,该画布用于装载所有缩略图信息,该画布只能拼接100张缩略图
    private final BufferedImage videoBufferedImage = new BufferedImage(1700, 380, BufferedImage.TYPE_INT_RGB);
    private final Graphics2D graphics2D = videoBufferedImage.createGraphics();

    public void retVideoImage(String videoFile) {
        Date startDate = new Date();
        try {
            //创建FFmpeg的对象，并传入视屏地址
            FFmpegFrameGrabber ffmpeg = FFmpegFrameGrabber.createDefault(new File(videoFile));
            log.info("视屏处理开始!");
            ffmpeg.start();
            //获取视屏的帧数
            double videoFrameRate = ffmpeg.getVideoFrameRate();
            //获取视屏的原数据
            log.info("视屏帧数：" + videoFrameRate);

            //截取视屏图片开始
            //获取视屏总整数
            int lengthInVideoFrames = ffmpeg.getLengthInVideoFrames();

            //获取视屏的时长(总帧数除以视屏帧数
            //int videoTime = (int)(lengthInVideoFrames/videoFrameRate);
            //log.info("视屏时长:"+videoTime+"s");
            //视屏的当前帧数
            int i = 0;
            //每3%就截取一帧图片
            double imageFrame = 0;

            log.info("length:" + lengthInVideoFrames);
            int index = 0;
            while (i < lengthInVideoFrames) {
                Frame frame = ffmpeg.grabImage();

                if (i == (int) (lengthInVideoFrames * imageFrame) && frame != null) {
                    this.imageGenerate(frame, index);
                    imageFrame += 0.03;
                    if (index == 4) {
                        //截取第4张作为视屏封面
                        //防止某些视屏以黑屏开场。
                        this.videoImageSaveDisk(videoFile, this.indexImage(frame));
                    }
                    index++;
                }
                i++;
            }
            log.info("生成图片张数:" + index);
            this.videoImageSaveDisk(videoFile, videoBufferedImage);

            //创建图片文件
            //写入图片数据
            ffmpeg.stop();
            ffmpeg.close();
            log.info("视屏处理结束!");

            Date endDate = new Date();
            long date = endDate.getTime() - startDate.getTime();
            log.info("程序运行时间:" + date);
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
            log.error("视屏处理失败!");
        } catch (Exception e) {
            log.error("程序出错!");
            e.printStackTrace();
        }
    }

    /***
     * 代码块描述:
     * @param frameImage:从视屏中截取的图片
     * @param index:当前截取到第几张图片
     * @return
     */
    public void imageGenerate(Frame frameImage, int index) throws IOException {
        Java2DFrameConverter java2DFrameConverter = new Java2DFrameConverter();

        BufferedImage bufferedImage = java2DFrameConverter.getBufferedImage(frameImage);
//        bufferedImage.getGraphics().drawImage(bufferedImage, 0, 0, 170, 95, null);
        //转换图片的尺寸
        BufferedImage newBufferedImage = new BufferedImage(170, 95, bufferedImage.getType());
        Graphics2D graphics = newBufferedImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.drawImage(bufferedImage, 0, 0, 170, 95, null);

        log.info("高：" + newBufferedImage.getHeight() + "--->宽：" + newBufferedImage.getWidth());

        this.jointImageBuffered(newBufferedImage, index);
        log.info("缩略图生成完成!");
    }

    //图片拼接方法
    public void jointImageBuffered(BufferedImage image, int index) {
        log.info("图片合成开始!");
        //计算图像拼接到第几张
        int imageX = image.getWidth() * (index % 10); //计算当前图片的x轴
        log.info("图片的X轴值:" + imageX);
        int imageY = image.getHeight() * (index == 0 ? 0 : index / 10); //每十张图片换行拼接
        log.info("图片的Y轴值:" + imageY);
        log.info("处理到第:" + index + "图片");
        graphics2D.drawImage(image, imageX, imageY, image.getWidth(), image.getHeight(), null);
    }

    //320*180
    public BufferedImage indexImage(Frame frameImage) throws Exception {
        //生成封面图片方法
        log.info("封面图片生成开始!");
//        String path = file+UUID.randomUUID().toString()+".jpg";

        Java2DFrameConverter java2DFrameConverter = new Java2DFrameConverter();
        BufferedImage bufferedImage = java2DFrameConverter.getBufferedImage(frameImage);
        //缩小图片(320*180)
        BufferedImage image = new BufferedImage(380, 180, bufferedImage.getType());
        Graphics2D graphics = image.createGraphics();
        //消除图片锯齿
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        image.getGraphics().drawImage(bufferedImage, 0, 0, 380, 180, null);

        log.info("封面图片申城结束!");
        return image;
    }

    public void videoImageSaveDisk(String imageFile, BufferedImage bufferedImage) throws Exception {
        log.info("图片持久化操作开始!");
        String path = imageFile + UUID.randomUUID().toString() + ".jpg";
        File file = new File(path);

        ImageIO.write(bufferedImage, "jpg", file);
        log.info("图片保存成功!");
    }


}
