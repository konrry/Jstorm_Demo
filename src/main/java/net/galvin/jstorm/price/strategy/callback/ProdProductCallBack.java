package net.galvin.jstorm.price.strategy.callback;

import net.galvin.jstorm.price.pojo.ProdProduct;
import net.galvin.jstorm.price.pojo.ProdProductBranch;

import java.util.List;

public interface ProdProductCallBack {

    /**
     * 根据产品ID，获取产品下的所有规格
     */
    List<ProdProductBranch> queryProdProductBranchList(long productId) throws Exception;

    /**
     * 获取规格回调接口
     */
    ProductBranchCallBack getProductBranchCallBack();

    /**
     * 更新产品可售字段状态
     */
    void updateProductSaleFlag(Long productId, String saleFlag, boolean msgFlag) throws Exception;

    /**
     * 扩展方法
     */
    boolean check(ProdProduct prodProduct);

}
