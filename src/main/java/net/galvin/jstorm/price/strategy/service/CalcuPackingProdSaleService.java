package net.galvin.jstorm.price.strategy.service;

import net.galvin.jstorm.price.pojo.ComCalSaleVO;
import net.galvin.jstorm.price.pojo.ProdProduct;
import net.galvin.jstorm.price.strategy.callback.PackingProductCallBack;

public interface CalcuPackingProdSaleService {

	/**
	 * 根据产品Id计算产品是否可售
	 * msgFlag --> true: 发消息  false: 不发消息
	 * sourceFlag --> true: 只校验当前  false: 深入校验到时间价格表
	 */
	ComCalSaleVO calculate(ProdProduct prodProduct, boolean msgFlag, boolean sourceFlag,
						   PackingProductCallBack packingProductCallBack) throws Exception;
	
}
