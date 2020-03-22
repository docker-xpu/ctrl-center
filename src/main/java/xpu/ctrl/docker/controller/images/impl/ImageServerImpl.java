package xpu.ctrl.docker.controller.images.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import xpu.ctrl.docker.controller.RemoteRepositoryContants;
import xpu.ctrl.docker.controller.images.ImageServer;
import xpu.ctrl.docker.controller.images.MyHttpUtils;
import xpu.ctrl.docker.dataobject.repository.RepositoryImageInfo;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class ImageServerImpl implements ImageServer {
    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    @Override
    public List<RepositoryImageInfo> getAllImageServer() throws IOException {
        List<RepositoryImageInfo> retList = Lists.newArrayList();
        URL url = new URL(String.format("http://%s:5000/v2/_catalog", RemoteRepositoryContants.REPOSITORY_IP));
        log.info("【url】{}", url.toString());
        JSONArray jsonArray = JSONObject.parseObject(IOUtils.toString(url, StandardCharsets.UTF_8)).getJSONArray("repositories");
        Object[] objects = jsonArray.toArray();
        final CountDownLatch countDownLatch = new CountDownLatch(objects.length);
        for(Object o: objects){
            cachedThreadPool.execute(()->{
                RepositoryImageInfo repositoryImageInfo = new RepositoryImageInfo();
                System.out.println("拿到的Tag=" + o);
                repositoryImageInfo.setName((String)o);
                String formatUrlString = String.format("http://%s:5000/v2/%s/tags/list", RemoteRepositoryContants.REPOSITORY_IP, o);
                URL formatUrl;
                URLConnection urlConnection;
                try {
                    formatUrl = new URL(formatUrlString);
                    urlConnection = formatUrl.openConnection();
                    urlConnection.setRequestProperty("Accept", "application/vnd.docker.distribution.manifest.v2+json");
                    String string = IOUtils.toString(urlConnection.getInputStream());
                    System.out.println(string);
                    JSONArray array = JSONObject.parseObject(string).getJSONArray("tags");
                    Object[] toArray = array.toArray();
                    List<RepositoryImageInfo.TagsBean> tagsBeanList = Lists.newArrayList();
                    for(Object tag: toArray){
                        RepositoryImageInfo.TagsBean tagsBean = new RepositoryImageInfo.TagsBean();
                        tagsBean.setTagName((String)tag);
                        tagsBean.setSha256(MyHttpUtils.getEtag((String)o, (String)tag));
                        tagsBeanList.add(tagsBean);
                    }
                    repositoryImageInfo.setTags(tagsBeanList);
                    retList.add(repositoryImageInfo);
                } catch (Exception e) {
                    log.error("【未清除的Tag标记】");
                }finally {
                    countDownLatch.countDown();
                }
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