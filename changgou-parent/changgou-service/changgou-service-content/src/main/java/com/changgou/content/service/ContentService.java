package com.changgou.content.service;

import content.pojo.Content;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: changgou
 * @description: ContentService 服务
 * @author: Owen Zhao
 * @create: 2020-10-29 15:27
 */
public interface ContentService {

    /***
     * 根据categoryId查询广告集合
     * @param id
     * @return
     */
    List<Content> findByCategory(Long id);
}
