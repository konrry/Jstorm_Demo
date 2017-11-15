package net.galvin.jstorm.price.strategy.callback;

import net.galvin.jstorm.price.pojo.SuppGoods;

public interface SuppGoodsCallBack {

    /**
     * 检测是否存在可售的时间价格表
     */
    boolean checkSuppGoodsTimePrice(long suppGoodsId, long categoryId);

    /**
     * 更新商品可售状态
     */
    void updateSuppGoodsSellFlag(long suppGoodsId, String saleFlag, boolean msgFlag) throws Exception;

    /**
     * 扩展方法
     */
    boolean check(SuppGoods suppGoods);

}
