/**
 * 
 */
package com.striverfeng.pojo;

import java.io.Serializable;

/**
 * @author StriverFeng
 *
 * 说明:
 */
public class Goods implements Serializable{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String goodsName;
		private String goodsId;
		public String getGoodsName() {
			return goodsName;
		}
		public void setGoodsName(String goodsName) {
			this.goodsName = goodsName;
		}
		public String getGoodsId() {
			return goodsId;
		}
		public void setGoodsId(String goodsId) {
			this.goodsId = goodsId;
		}

	@Override
	public String toString() {
		return "Goods{" +
				"goodsName='" + goodsName + '\'' +
				", goodsId='" + goodsId + '\'' +
				'}';
	}
}
