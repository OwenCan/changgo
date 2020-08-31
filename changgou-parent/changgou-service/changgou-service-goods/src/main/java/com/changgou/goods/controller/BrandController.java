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
    public Result<Brand> findAll() {
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
        return new Result<>(true, StatusCode.OK, "查询品牌名称成功", list);
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @GetMapping
    public Result<Brand> findById(Integer id) {
        Brand brand = brandService.findById(id);
        return new Result<>(true, StatusCode.OK, "根据id查询成功", brand);
    }

    /***
     * 新增品牌数据
     * @param brand
     * @return
     */
    @PostMapping
    public Result add(Brand brand) {
        brandService.add(brand);
        return new Result(true, StatusCode.OK, "添加品牌成功");
    }

    /***
     * 修改品牌数据
     * @param brand
     * @param id
     * @return
     */
    @PostMapping(value = "/{id}")
    public Result update(@RequestBody Brand brand, @PathVariable Integer id) {
        brand.setId(id);
        brandService.update(brand);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable Integer id) {
        brandService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 查询所有商品信息
     *
     * @param brand
     * @return
     */
    @PostMapping(value = "/search")
    public Result<Brand> findList(Brand brand) {
        List<Brand> brands = brandService.findList(brand);
        return new Result<>(true, StatusCode.OK, "查询成功", brands);
    }
}
