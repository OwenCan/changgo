package com.changgou.goods.service;
import com.changgou.goods.pojo.Brand;
import java.util.List;
/****
 * @Author:admin
 * @Description:Brand业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface BrandService {

    /***
     * 查询所有Brand
     * @return
     */
    List<Brand> findAll();
}
