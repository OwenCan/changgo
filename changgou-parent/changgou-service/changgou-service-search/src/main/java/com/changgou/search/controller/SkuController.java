package com.changgou.search.controller;

import com.changgou.search.service.SkuService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @program: changgou
 * @description:
 * @author: Owen Zhao
 * @create: 2020-10-30 11:26
 */
@RestController  //@Controller+ResponseBody
@RequestMapping("/search")
@CrossOrigin
public class SkuController {


    @Autowired
    private SkuService skuService;

    /**
     * 导入数据
     * @return
     */
    @GetMapping("/import")
    public Result search(){
        skuService.importSku();
        return new Result(true, StatusCode.OK,"导入数据到索引库中成功！");
    }

    /**
     * 搜索商品信息
     * @param searchMap
     * @return
     */
    @GetMapping
    public Map seach(@RequestParam(required = false) Map searchMap) {
        return skuService.search(searchMap);
    }




}
