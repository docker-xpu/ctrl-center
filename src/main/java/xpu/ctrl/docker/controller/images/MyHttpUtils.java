package xpu.ctrl.docker.controller.images;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import xpu.ctrl.docker.controller.RemoteRepositoryContants;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class MyHttpUtils {
    public static String getEtag(String name, String tag){
        URL url;
        URLConnection conn;
        try {
            url = new URL(String.format("http://%s:5000/v2/%s/manifests/%s", RemoteRepositoryContants.REPOSITORY_IP, name, tag));
            conn = url.openConnection();
            conn.setRequestProperty("Accept", "application/vnd.docker.distribution.manifest.v2+json");
//            String string = IOUtils.toString(conn.getInputStream());
//            TagInfo tagInfo = JSONObject.parseObject(string, TagInfo.class);
//            log.info("【解析得到的Digest】{}", tagInfo.getConfig().getDigest());
//            return tagInfo.getConfig().getDigest();
            Map headers = conn.getHeaderFields();
            Set<String> keys = headers.keySet();
            for(String key: keys){
                String val = conn.getHeaderField(key);
                System.out.println(key+"="+val);
                if("Docker-Content-Digest".equals(key)){
                    return val;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}


@Data
class TagInfo{
    private int schemaVersion;
    private String mediaType;
    private ConfigBean config;
    private List<LayersBean> layers;

    @Data
    public static class ConfigBean {
        private String mediaType;
        private BigInteger size;
        private String digest;
    }

    @Data
    public static class LayersBean {
        private String mediaType;
        private BigInteger size;
        private String digest;
    }
}