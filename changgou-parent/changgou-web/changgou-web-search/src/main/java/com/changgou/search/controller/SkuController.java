package com.changgou.search.controller;

import com.search.feign.SkuFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @program: changgou
 * @description: Sku搜索调用
 * @author: Owen Zhao
 * @create: 2020-11-10 16:49
 */
@Controller
@RequestMapping(value = "/search")
public class SkuController {

    @Autowired
    private SkuFeign skuFeign;

    /**
     * 搜索
     *
     * @param searchMap
     * @return
     */
    @GetMapping(value = "/list")
    public String search(@RequestParam(required = false) Map<String, String> searchMap, Model model) {
        //调用changgou-service-search微服务
        Map<String, Object> resultMap = skuFeign.search(searchMap);
        //搜索数据结果
        model.addAttribute("result", resultMap);
        //搜索条件
        model.addAttribute("searchMap", searchMap);
        return "search";
    }


}
