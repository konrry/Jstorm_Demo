package net.galvin.jstorm.price.strategy.callback;

import net.galvin.jstorm.price.pojo.ProdProduct;

public interface PackingProductCallBack {

    /**
     * 更新产品可售字段状态
     */
    void updateProductSaleFlag(Long productId, String saleFlag, boolean msgFlag) throws Exception;

    /**
     * 校验供应商打包
     */
    boolean checkSupplierPackProd(ProdProduct prodProduct, boolean msgFlag) throws Exception;

    /**
     * 校验自主打包
     */
    boolean checkLvmamaPackProd(ProdProduct prodProduct, boolean msgFlag) throws Exception;

    /**
     * 扩展方法
     */
    boolean check(ProdProduct prodProduct);

}
