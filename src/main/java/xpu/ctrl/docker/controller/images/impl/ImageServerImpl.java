package xpu.ctrl.docker.controller.images.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import xpu.ctrl.docker.controller.RemoteRepositoryContants;
import xpu.ctrl.docker.controller.images.ImageServer;
import xpu.ctrl.docker.controller.images.MyHttpUtils;
import xpu.ctrl.docker.dataobject.repository.RepositoryImageInfo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ImageServerImpl implements ImageServer {
    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    @Override
    public List<RepositoryImageInfo> getAllImageServer() throws IOException {
        List<RepositoryImageInfo> retList = Lists.newArrayList();
        URL url = new URL(String.format("http://%s:5000/v2/_catalog", RemoteRepositoryContants.REPOSITORY_IP));
        JSONObject jsonObject = JSONObject.parseObject(IOUtils.toString(url, StandardCharsets.UTF_8));
        JSONArray jsonArray = jsonObject.getJSONArray("repositories");
        Object[] objects = jsonArray.toArray();

        final CountDownLatch countDownLatch = new CountDownLatch(objects.length);

        for(Object o: objects){
            cachedThreadPool.execute(()->{
                RepositoryImageInfo repositoryImageInfo = new RepositoryImageInfo();
                System.out.println((String)o);
                repositoryImageInfo.setName((String)o);
                String formatUrlString = String.format("http://%s:5000/v2/%s/tags/list", RemoteRepositoryContants.REPOSITORY_IP, o);
                URL formatUrl = null;
                try {
                    formatUrl = new URL(formatUrlString);
                } catch (Exception e) {
                    countDownLatch.countDown();
                }
                JSONArray array = null;
                try {
                    array = JSONObject.parseObject(IOUtils.toString(formatUrl, StandardCharsets.UTF_8)).getJSONArray("tags");
                } catch (Exception e) {
                    countDownLatch.countDown();
                }
                Object[] toArray = array.toArray();
                //List<Map<String, String>> tagList = Lists.newArrayList();
                List<RepositoryImageInfo.TagsBean> tagsBeanList = Lists.newArrayList();

                HashMap<String, String> tagMap;
                for(Object tag: toArray){
//                    tagMap = new HashMap<>();
//                    tagMap.put((String)tag, MyHttpUtils.getEtag((String)o, (String)tag));
                    RepositoryImageInfo.TagsBean tagsBean = new RepositoryImageInfo.TagsBean();
                    tagsBean.setTagName((String)tag);
                    tagsBean.setSha256(MyHttpUtils.getEtag((String)o, (String)tag));
                    tagsBeanList.add(tagsBean);
                }

                repositoryImageInfo.setTags(tagsBeanList);
                retList.add(repositoryImageInfo);
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return retList;
    }
}