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
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description 文件操作
 * @Author Owen Zhao
 * @Date 2020/9/1
 */
public class FastDFSClient {

    /**
     * 初始化tracker信息
     */
    static {
        try {
            //获取tracker的配置文件fdfs_client.conf的位置
            String filePath = new ClassPathResource("fdfs_client.conf").getPath();
            ClientGlobal.init(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传 重要
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
            //获取StorageClient对象
            StorageClient storageClient = getStorageClient();
            //执行文件上传
            //http://192.168.211.132:8080/group1/M00/00/00/wKjThF9iz3iAaMOhAADFNYB1MJ0143.png
            //updateResults[0]:group1
            //updateResults[1]:M00/00/00/wKjThF9iz3iAaMOhAADFNYB1MJ0143.png
            updateResults = storageClient.upload_file(file.getContent(), file.getExt(), mete_list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updateResults;
    }

    /**
     * 获取Tracker服务地址
     *
     * @return
     */
    public static String getTrackerUrl() {
        try {
            //通过TrackerClient获取TrackerServer对象
            TrackerServer trackerServer = getTrackerServer();
            //获取Tracker地址
            return "http://" + trackerServer.getInetSocketAddress().getHostString()
                    + ":" + ClientGlobal.getG_tracker_http_port();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件信息
     *
     * @param groupName        文件组名
     * @param remoteStringName 文件存储路径名字
     *                         <p>
     *                         source_ip_addr = 192.168.211.132, file_size = 50485, create_timestamp = 2020-09-17 10:52:40, crc32 = -2139803491
     * @return
     */
    public static FileInfo getFile(String groupName, String remoteStringName) {
        try {
            //通过TrackerServer获得StorageClient对象
            StorageClient storageClient = getStorageClient();
            //获取文件信息
            return storageClient.get_file_info(groupName, remoteStringName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        //获取文件信息
//        FileInfo group1 = getFile("group1", "M00/00/00/wKjThF9iz3iAaMOhAADFNYB1MJ0143.png");
//        System.out.println(group1.toString());

        InputStream group11 = downFile("group1", "M00/00/00/wKjThF9iz3iAaMOhAADFNYB1MJ0143.png");
        //将文件写入本地磁盘
        FileOutputStream ou = new FileOutputStream("E:/1.jpg");

        byte[] bytes = new byte[1024];
        while (group11.read(bytes) != -1) {
            ou.write(bytes);
        }
        ou.flush();
        ou.close();
        group11.close();
    }


    /**
     * 文件下载 重要
     *
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static InputStream downFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getStorageClient();
            byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
            return new ByteArrayInputStream(fileByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 文件删除实现 重要
     * @param groupName:组名
     * @param remoteFileName：文件存储完整名
     */
    public static void deleteFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getStorageClient();
            //通过StorageClient删除文件
            storageClient.delete_file(groupName, remoteFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 获取组信息
     * @param groupName :组名
     */
    public static StorageServer getStorages(String groupName) {
        try {
            TrackerClient trackerClient = new TrackerClient();
            //通过TrackerClient获取TrackerServer对象
            TrackerServer trackerServer = trackerClient.getConnection();
            //通过trackerClient获取Storage组信息
            return trackerClient.getStoreStorage(trackerServer, groupName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 根据文件组名和文件存储路径获取Storage服务的IP、端口信息
     * @param groupName :组名
     * @param remoteFileName ：文件存储完整名
     */
    public static ServerInfo[] getServerInfo(String groupName, String remoteFileName) {
        try {
            //创建TrackerClient对象
            TrackerClient trackerClient = new TrackerClient();
            //通过TrackerClient获取TrackerServer对象
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取服务信息
            return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 获取TrackerServer
     */
    public static TrackerServer getTrackerServer() throws Exception {
        //创建TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer对象
        return trackerClient.getConnection();
    }

    /***
     * 获取StorageClient
     * @return
     * @throws Exception
     */
    public static StorageClient getStorageClient() throws Exception {
        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();
        //通过TrackerServer创建StorageClient
        StorageClient storageClient = new StorageClient(trackerServer, null);
        return storageClient;
    }
}
