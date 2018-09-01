import ouyang.tools.Image.verification.VerificationCode;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        VerificationCode vc = new VerificationCode(200, 600);
        String s= vc.getVerificationCodeString();
        vc.ImagePrint(new FileOutputStream("F://ouyang.jpg"));
        System.out.println(s);
    }
}
