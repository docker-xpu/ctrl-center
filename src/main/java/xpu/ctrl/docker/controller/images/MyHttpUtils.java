package xpu.ctrl.docker.controller.images;

import xpu.ctrl.docker.controller.RemoteRepositoryContants;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

public class MyHttpUtils {
    public static String getEtag(String name, String tag){
        URL url;
        URLConnection conn;
        try {
            url = new URL(String.format("http://%s:5000/v2/%s/manifests/%s", RemoteRepositoryContants.REPOSITORY_IP, name, tag));
            conn = url.openConnection();
            Map headers = conn.getHeaderFields();
            Set<String> keys = headers.keySet();
            for(String key : keys){
                String val = conn.getHeaderField(key);
                System.out.println(key+"    "+val);
                if("Etag".equals(key)) return val.substring(1, val.length()-1);
            }
            return "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
