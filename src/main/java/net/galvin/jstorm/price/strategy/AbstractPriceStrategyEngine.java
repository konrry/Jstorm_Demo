package net.galvin.jstorm.price.strategy;

import net.galvin.jstorm.price.pojo.ComCalData;
import net.galvin.jstorm.price.pojo.ComPush;
import net.galvin.jstorm.utils.ExceptionUtil;
import net.galvin.jstorm.utils.Logging;

public abstract class AbstractPriceStrategyEngine implements PriceStrategyEngine {

    @Override
    public void calculate(ComCalData comCalData) throws Exception {
        Logging.info("定价策略开始处理");
        String objectType = comCalData.getObjectType();
        try {
            //计算商品
            if (ComPush.OBJECT_TYPE.GOODS.name().endsWith(objectType)) {
                this.calcuSuppGoods(comCalData.getObjectId());
                // 计算产品规格
            } else if (ComPush.OBJECT_TYPE.PRODUCTBRANCH.name().endsWith(objectType)) {
                this.calcuProdProductBranch(comCalData.getObjectId());
                // 计算产品
            } else if (ComPush.OBJECT_TYPE.PRODUCT.name().endsWith(objectType)) {
                this.calcuProdProduct(comCalData.getObjectId());
            }
            Logging.info("处理成功");
        }catch (Exception e){
            Logging.info("处理失败: " + ExceptionUtil.getExceptionDetails(e));
        }finally {
            Logging.info("定价策略处理完成");
        }
    }

    /**
     * 商品维度触发可售计算
     */
    protected abstract void calcuSuppGoods(long suppGoodsId) throws Exception;

    /**
     * 产品规格维度触发可售计算
     */
    protected abstract void calcuProdProductBranch(long productBranchId) throws Exception;

    /**
     * 产品维度触发可售计算
     */
    protected abstract void calcuProdProduct(long productId) throws Exception;

}
