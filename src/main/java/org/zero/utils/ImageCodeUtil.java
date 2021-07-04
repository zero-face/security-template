package org.zero.utils;

import org.zero.validator.code.ImageCode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @Author Zero
 * @Date 2021/7/2 0:48
 * @Since 1.8
 * @Description TODO
 **/
public class ImageCodeUtil {
    /**
     * 创建图片验证码
     * @return
     */
    public static ImageCode createImageCode() {
        int width = 100; // 验证码图片宽度
        int height = 36; // 验证码图片长度
        int length = 4; // 验证码位数
        int expireIn = 120; // 验证码有效时间 120s

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(getRandColor(200,250));
        g.fillRect(0,0,width,height);
        g.setFont(new Font("Times New Roman",Font.ITALIC, 35));
        g.setColor(getRandColor(160,200));
        for(int i = 0; i< 155; i++){
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        StringBuilder sRand = new StringBuilder();
        String rand = null;
        for(int i = 0; i<length; i++){
            int anInt = random.nextInt(57);
            if(anInt  >= 10) {
                if(anInt + 65 >=91 && anInt + 65 <= 96) {
                    anInt += 6;
                }
                char ch = (char) (anInt + 65);
                rand = String.valueOf(ch);
            } else {
                rand =  String.valueOf(anInt);
            }
            sRand.append(rand);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 15 * i + 15, 28);
        }
            g.dispose();
            return new ImageCode(image, sRand.toString(),expireIn);
    }

    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if(fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
