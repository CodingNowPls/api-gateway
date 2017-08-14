/**
 * 
 */
package com.cn.striverfeng.service.impl;

import com.cn.striverfeng.core.APIMapping;
import com.cn.striverfeng.pojo.Goods;
import org.springframework.stereotype.Service;


/**
 * @author StriverFeng
 *
 * 说明:
 */
@Service("goodsService")
public class GoodsServiceImpl {
	
	
	@APIMapping("com.cn.striverfeng.service.goodsService")
	public Goods addGoods(Goods goods, Integer id){
		return goods;
	}

}
