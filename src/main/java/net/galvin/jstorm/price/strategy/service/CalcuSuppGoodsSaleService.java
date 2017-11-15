package net.galvin.jstorm.price.strategy.service;

import net.galvin.jstorm.price.pojo.ComCalSaleVO;
import net.galvin.jstorm.price.pojo.SuppGoods;
import net.galvin.jstorm.price.strategy.callback.SuppGoodsCallBack;

public interface CalcuSuppGoodsSaleService {
	
	/**
	 * 根据商品Id计算商品是否可售
	 * msgFlag --> true: 发消息  false: 不发消息
	 */
	ComCalSaleVO calculate(SuppGoods suppGoods, boolean msgFlag, SuppGoodsCallBack suppGoodsCallBack) throws Exception;
}
