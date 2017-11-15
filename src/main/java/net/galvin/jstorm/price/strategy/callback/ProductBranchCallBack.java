package net.galvin.jstorm.price.strategy.callback;

import net.galvin.jstorm.price.pojo.ProdProductBranch;
import net.galvin.jstorm.price.pojo.SuppGoods;

import java.util.List;

public interface ProductBranchCallBack {

    /**
     * 根据规格ID获取所有的商品
     */
    List<SuppGoods> querySuppGoodsList(Long prodBranchId) throws Exception;

    /**
     * 返回规格回调
     */
    SuppGoodsCallBack getSuppGoodsCallBack();

    /**
     * 更新规格可售字段
     */
    void updateProductBranchSellFlag(long productBranchId, String saleFlag, boolean msgFlag) throws Exception;

    /**
     * 扩展方法
     */
    boolean check(ProdProductBranch prodProductBranch);

}
