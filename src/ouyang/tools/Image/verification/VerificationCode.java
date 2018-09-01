package ouyang.tools.Image.verification;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

public class VerificationCode {
    private int imageHeight;
    private int imageWidth;
    private String verificationCodeString;

    public VerificationCode(int imageHeight, int imageWidth) {
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
        this.verificationCodeString = getVerificationCode();
    }

    public String getVerificationCodeString() {
        return verificationCodeString;
    }


    /**
     * 1.可出现的字符0-9，a-z,随机大小写
     * 2.可出现的字体宋体，仿宋，微软雅黑，Arial，Verdana，serif
     * <p>
     * 3.格式变化/斜体，加粗，黑体，下划线
     * 4.位置变化
     * 5.干扰线
     */
    private static String getVerificationCode() {
        String vs = "";
        String showChar = "23456789abcdefghijknmpqrstuvwxyz";//10-2+26-2
        Random rdChar = new Random();
        for (int i = 0; i < 4; i++) {
            int location = rdChar.nextInt(showChar.length() - 1);
            if (rdChar.nextBoolean() && location > 7)
                vs += (char) (showChar.charAt(location) - 32);
            else
                vs += showChar.charAt(location);
        }
        return vs;
    }


    public BufferedImage getBufferedImage() {
        Random rd = new Random();
        String[] Typefaces = {"宋体", "仿宋", "微软雅黑", "Arial", "Verdana", "serif"};
        BufferedImage bf = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bf.createGraphics();
        graphics.setColor(Color.WHITE);
        char[] vs = verificationCodeString.toCharArray();
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        for (int i = 0; i < 4; i++) {
            graphics.setColor(new Color(rd.nextInt(220), rd.nextInt(220), rd.nextInt(220), rd.nextInt(100) + 60));
            graphics.setFont(new Font(Typefaces[rd.nextInt(5)], rd.nextInt(3), imageWidth / 6 + rd.nextInt(imageHeight / 2)));
            graphics.drawChars(vs, i, 1, i * imageWidth / 4 + rd.nextInt(imageHeight / 2), imageHeight);
        }
        for (int i = 0; i < imageWidth * 4; i++) {
            graphics.setColor(new Color(rd.nextInt(220), rd.nextInt(220), rd.nextInt(220)));
            graphics.fillOval(rd.nextInt(imageWidth), rd.nextInt(imageHeight), rd.nextInt(imageHeight / 10), rd.nextInt(5));//画椭圆

        }
        for (int i = 0; i < 4; i++) {
            graphics.setColor(new Color(rd.nextInt(220), rd.nextInt(220), rd.nextInt(220)));
            graphics.drawLine(rd.nextInt(imageWidth), rd.nextInt(imageHeight), rd.nextInt(imageWidth), rd.nextInt(imageHeight));
        }

        return bf;
    }

    public void ImagePrint(OutputStream out) throws IOException {
        ImageIO.write(getBufferedImage(), "JPEG", out);
    }
}
