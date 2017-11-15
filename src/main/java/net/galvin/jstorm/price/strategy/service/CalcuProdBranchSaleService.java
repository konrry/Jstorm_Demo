package net.galvin.jstorm.price.strategy.service;

import net.galvin.jstorm.price.pojo.ComCalSaleVO;
import net.galvin.jstorm.price.pojo.ProdProductBranch;
import net.galvin.jstorm.price.strategy.callback.ProductBranchCallBack;

public interface CalcuProdBranchSaleService {
	
	/**
	 * 根据产品规格Id计算产品规格是否可售
	 * msgFlag --> true: 发消息  false: 不发消息
	 * sourceFlag --> true: 只校验当前  false: 深入校验到时间价格表
	 */
	ComCalSaleVO calculate(ProdProductBranch prodProductBranch, boolean msgFlag,
						   boolean sourceFlag, ProductBranchCallBack productBranchCallBack)throws Exception;

}
