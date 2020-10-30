package com.changgou.search.dao;

import com.changgou.goods.pojo.Sku;
import com.search.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuEsMapper extends ElasticsearchRepository<SkuInfo, Long> {

}
