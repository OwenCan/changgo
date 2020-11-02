package com.changgou.search.service;


import java.util.Map;

public interface SkuService {

    /***
     * 导入SKU数据
     */
    void importSku();


    /***
     * 搜索 涉及到品牌、分类、规格等
     * @param searchMap
     * @return
     */
    Map search(Map<String, String> searchMap);
}
