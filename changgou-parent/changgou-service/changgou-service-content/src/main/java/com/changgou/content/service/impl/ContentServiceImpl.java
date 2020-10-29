package com.changgou.content.service.impl;

import com.changgou.content.dao.ContentMapper;
import com.changgou.content.service.ContentService;
import content.pojo.Content;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @program: changgou
 * @description: 广告查询
 * @author: Owen Zhao
 * @create: 2020-10-29 15:31
 */
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentMapper contentMapper;

    /***
     * 根据分类ID查询
     * @param id
     * @return
     */
    @Override
    public List<Content> findByCategory(Long id) {
        Content content = new Content();
        content.setId(id);
        content.setStatus("1");
        return contentMapper.select(content);
    }

}
