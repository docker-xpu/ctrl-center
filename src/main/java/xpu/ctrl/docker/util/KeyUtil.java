package xpu.ctrl.docker.util;

import java.util.Random;

public class KeyUtil {
    /**
     * 生成唯一的主键
     * 格式: 时间+随机数
     */
    public static synchronized Long genUniqueKey() {
        Random random = new Random();
        int number = random.nextInt(1000);
        return System.currentTimeMillis() + number;
    }

    /**
     * 生成唯一的主键
     * 格式: 时间+随机数
     */
    public static synchronized String genVerifyKey() {
        Random random = new Random();
        return String.valueOf(random.nextInt(900000) + 10000);
    }
}
