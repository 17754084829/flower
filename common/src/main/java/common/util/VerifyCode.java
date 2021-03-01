package common.util;

import java.util.Random;

public class VerifyCode {
    public static String GenerateCode(){
        int num = (int) ((Math.random() * 9 + 1) * 100000);
        return ""+num;
    }
}
