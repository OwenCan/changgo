package com.changgou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.search.dao.SkuEsMapper;
import com.changgou.search.service.SkuService;
import com.search.SkuInfo;
import entity.Result;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: changgou
 * @description: Sku
 * @author: Owen Zhao
 * @create: 2020-10-30 11:09
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private SkuEsMapper skuEsMapper;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    /**
     * 导入Sku数据到ES
     */
    @Override
    public void importSku() {
        //调用changgou-service-goods微服务
        Result<List<Sku>> skuListResult = skuFeign.findByStatus("1");
        //将数据转成search.Sku
        List<SkuInfo> skuInfos=  JSON.parseArray(JSON.toJSONString(skuListResult.getData()),SkuInfo.class);
        for(SkuInfo skuInfo:skuInfos){
            Map<String, Object> specMap= JSON.parseObject(skuInfo.getSpec()) ;
            skuInfo.setSpecMap(specMap);
        }
        skuEsMapper.saveAll(skuInfos);
    }


    @Override
    public Map search(Map<String, String> searchMap) {
        String keyWord = searchMap.get("keyword");
        if (StringUtils.isEmpty(keyWord)) {
            keyWord = "华为";
        }
        //2、创建查询对象的构建对象
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //3、创建查询条件
        //设置分组条件  商品分类
        queryBuilder.addAggregation(AggregationBuilders.terms("skuCategorygroup").field("categoryName").size(50));
        queryBuilder.withQuery(QueryBuilders.matchQuery("name", keyWord));


        //4、构造查询对象
        NativeSearchQuery query = queryBuilder.build();
        //5、执行查询
        AggregatedPage<SkuInfo> skuPage = esTemplate.queryForPage(query, SkuInfo.class);

        //获取分组结果
        StringTerms stringTerms = (StringTerms) skuPage.getAggregation("skuCategorygroup");
        List<String> categoryList = getStringsCategoryList(stringTerms);

        //返回结果
        Map resultMap = new HashMap();
        resultMap.put("categoryList", categoryList);
        resultMap.put("rows", skuPage.getContent());
        resultMap.put("total", skuPage.getTotalElements());
        resultMap.put("totalPage", skuPage.getTotalPages());

        return resultMap;
    }

    /**
     * 获取分类列表数据
     *
     * @param stringTerms
     * @return
     */
    private List<String> getStringsCategoryList(StringTerms stringTerms) {
        List<String> categoryList = new ArrayList<>();
        if (stringTerms != null) {
            for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
                String keyAsString = bucket.getKeyAsString();//分组的值
                categoryList.add(keyAsString);
            }
        }
        return categoryList;
    }


}
