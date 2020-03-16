package xpu.ctrl.docker.controller.images.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import xpu.ctrl.docker.controller.images.ImageServer;
import xpu.ctrl.docker.dataobject.repository.RepositoryImageInfo;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServerImpl implements ImageServer {
    @Override
    public List<RepositoryImageInfo> getAllImageServer() throws IOException {
        List<RepositoryImageInfo> retList = Lists.newArrayList();
        URL url = new URL("http://139.159.254.242:5000/v2/_catalog");
        JSONObject jsonObject = JSONObject.parseObject(IOUtils.toString(url, StandardCharsets.UTF_8));
        JSONArray jsonArray = jsonObject.getJSONArray("repositories");
        Object[] objects = jsonArray.toArray();
        for(Object o: objects){
            RepositoryImageInfo repositoryImageInfo = new RepositoryImageInfo();
            System.out.println((String)o);
            repositoryImageInfo.setName((String)o);
            String formatUrlString = String.format("http://139.159.254.242:5000/v2/%s/tags/list", (String) o);
            URL formatUrl = new URL(formatUrlString);
            JSONArray array = JSONObject.parseObject(IOUtils.toString(formatUrl, StandardCharsets.UTF_8)).getJSONArray("tags");
            Object[] toArray = array.toArray();
            List<String> tagList = Lists.newArrayList();
            for(Object tag: toArray){
                tagList.add((String)tag);
            }
            repositoryImageInfo.setTags(tagList);
            retList.add(repositoryImageInfo);
        }
        return retList;
    }
}
