/**
 * @作者 7七月
 * @微信公号 林间有风
 * @开源项目 $ http://7yue.pro
 * @免费专栏 $ http://course.7yue.pro
 * @我的课程 $ http://imooc.com/t/4294850
 * @创建时间 2020/9/1 18:05
 */
package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @Description 文件操作
 * @Author Owen Zhao
 * @Date 2020/9/1
 */
public class FastDFSClient {

    /**
     * 初始化tracker信息
     */
    static{
        try {
            //获取tracker的配置文件fdfs_client.conf的位置
            String filePath = new ClassPathResource("fdfs_client.conf").getPath();
            ClientGlobal.init(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     *
     * @param file 要上传文件信息封装 =》FastDFSFile
     * @return
     */
    public static String[] update(FastDFSFile file) {
        //获取文件作者
        NameValuePair[] mete_list = new NameValuePair[1];
        mete_list[0] = new NameValuePair(file.getAuthor());

        String[] updateResults = null;
        try {
            //创建TrackerClient客户端对象
            TrackerClient trackerClient = new TrackerClient();
            //通过TrackerClient对象获取TrackerServer信息
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取StorageClient对象
            StorageClient storageClient = new StorageClient(trackerServer, null);
            //执行文件上传
            updateResults = storageClient.upload_file(file.getContent(), file.getExt(), mete_list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updateResults;
    }

    /**
     * 获取Tracker服务地址
     * @return
     */
    public static String getTrackerUrl(){
        try {
            //创建TrackerClient对象
            TrackerClient trackerClient = new TrackerClient();
            //通过TrackerClient获取TrackerServer对象
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取Tracker地址
            return "http://" + trackerServer.getInetSocketAddress().getHostString()
                    + ":" + ClientGlobal.getG_tracker_http_port();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
