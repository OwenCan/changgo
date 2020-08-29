package com.changgou.goods.controller;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import entity.Result;
import entity.StatusCode;

import org.springframework.web.bind.annotation.*;
import java.util.List;

/****
 * @Author:admin
 * @Description: 品牌controller
 * @Date 2019/6/14 0:18
 *****/

@RestController
@RequestMapping("/brand")
@CrossOrigin()
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    /***
     * 查询Brand全部数据
     * @return
     */
    @GetMapping
    public Result<Brand> findAll(){
        try {
            System.out.println("aaaaa=====");
            Thread.sleep(3000);
            System.out.println("bbbb=====");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //调用BrandService实现查询所有Brand
        List<Brand> list = brandService.findAll();
        //相应结果封装
        return new Result<>(true, StatusCode.OK, "查询品牌名称成功",list);
    }

}
