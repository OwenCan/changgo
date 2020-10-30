package com.changgou.goods.feign;

import com.changgou.goods.pojo.Sku;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @program: changgou
 * @description:
 * @author: Owen Zhao
 * @create: 2020-10-30 10:59
 */
@FeignClient(name= "goods")
@RequestMapping(value = "/sku")
public interface SkuFeign {

    /**
     * 根据审核状态获取Sku信息
     *
     * @param status
     * @return
     */
    @GetMapping("/status/{status}")
    Result<List<Sku>> findByStatus(@PathVariable String status);

}
