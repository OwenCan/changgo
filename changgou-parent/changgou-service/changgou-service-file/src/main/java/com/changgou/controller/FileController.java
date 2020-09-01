/**
 * @作者 7七月
 * @微信公号 林间有风
 * @开源项目 $ http://7yue.pro
 * @免费专栏 $ http://course.7yue.pro
 * @我的课程 $ http://imooc.com/t/4294850
 * @创建时间 2020/9/1 18:33
 */
package com.changgou.controller;

import com.changgou.file.FastDFSFile;
import com.changgou.util.FastDFSClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description 文件上传控制层
 * @Author Owen Zhao
 * @Date 2020/9/1
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/update")
public class FileController {

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    public Result update(@RequestParam("file") MultipartFile file) throws Exception {
        //封装文件信息
        FastDFSFile fastDFSFile = new FastDFSFile(
                file.getOriginalFilename(),
                file.getBytes(),
                StringUtils.getFilenameExtension(file.getOriginalFilename())//spirng的方法，获取文件或炸名
        );

        //将文件上传到FastDFS中
        FastDFSClient.update(fastDFSFile);
        return new Result(true, StatusCode.OK, "上传成功");
    }
}
