/**
 * 
 */
package com.striverfeng.service.impl;

import com.striverfeng.core.APIMapping;
import com.striverfeng.pojo.Goods;
import org.springframework.stereotype.Service;


/**
 * @author StriverFeng
 *
 * 说明:
 */
@Service("goodsService")
public class GoodsServiceImpl {
	
	
	@APIMapping("com.striverfeng.service.goodsService")
	public Goods addGoods(Goods goods, Integer id){
		return goods;
	}

}
