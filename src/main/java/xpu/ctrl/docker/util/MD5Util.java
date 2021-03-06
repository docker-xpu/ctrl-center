package xpu.ctrl.docker.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static String getMD5(InputStream is) throws NoSuchAlgorithmException, IOException {
        StringBuilder md5 = new StringBuilder();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] dataBytes = new byte[1024];

        int nread;
        while ((nread = is.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        };
        byte[] mdbytes = md.digest();
        // convert the byte to hex format
        for (byte mdbyte : mdbytes) {
            md5.append(Integer.toString((mdbyte & 0xff) + 0x100, 16).substring(1));
        }
        return md5.toString();
    }
}