package com.changgou.content.controller;

import com.changgou.content.service.ContentService;
import com.netflix.discovery.converters.Auto;
import content.pojo.Content;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: changgou
 * @description: 广告 Controller
 * @author: Owen Zhao
 * @create: 2020-10-29 15:37
 */
@Api(value = "ContentController")
@RestController
@RequestMapping(value = "/content")
@CrossOrigin
public class ContentController {

    @Autowired
    private ContentService contentService;

    /***
     * 根据categoryId查询广告集合
     */
    @GetMapping(value = "/list/category/{id}")
    public Result<List<Content>> findByCategory(@PathVariable Long id){
        //根据分类ID查询广告集合
        List<Content> contents = contentService.findByCategory(id);
        return new Result<>(true, StatusCode.OK, "查询成功！", contents);
    }
}
