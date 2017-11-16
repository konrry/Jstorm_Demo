package net.galvin.jstorm.price.strategy.packing;

import net.galvin.jstorm.price.pojo.ComCalSaleVO;
import net.galvin.jstorm.price.pojo.ProdProduct;
import net.galvin.jstorm.price.strategy.callback.PackingProductCallBack;
import net.galvin.jstorm.price.strategy.service.CalcuPackingProdSaleService;
import net.galvin.jstorm.price.utils.Constant;
import net.galvin.jstorm.utils.Logging;

/**
 * 组合产品(自主打包和供应商打包)可售计算
 */
public class CalcuPackingProdSaleImpl implements CalcuPackingProdSaleService {

    @Override
    public ComCalSaleVO calculate(ProdProduct prodProduct, boolean msgFlag,
                                  boolean sourceFlag, PackingProductCallBack packingProductCallBack) throws Exception {

        Logging.info("判断组合产品对象是否为空。");
        if(prodProduct == null || prodProduct.getProductId() == null){
            throw new Exception("prodProduct is null or prodProduct.getProductId() is null.");
        }

        Long productId = prodProduct.getProductId();

        ComCalSaleVO calSaleVO = new ComCalSaleVO();
        calSaleVO.setObjectId(productId);
        calSaleVO.setObjectType(Constant.OBJECT_TYPE.PROD_PRODUCT.name());
        calSaleVO.setSaleFlag(false);

        //产品有效性校验
        Logging.info("判断组合产品对象是否有效。");
        if (!Constant.Y_FLAG.equals(prodProduct.getCancelFlag())) {
            calSaleVO.setNosaleMsg("无效的产品");
            Logging.info("无效的产品");
            this.checkProdProductSaleFlag(prodProduct, false, msgFlag, packingProductCallBack);
            return calSaleVO;
        }

        if(packingProductCallBack == null){
            throw new Exception("packingProductCallBack is null.");
        }

        boolean isSaleFlag = packingProductCallBack.check(prodProduct);
        if(!isSaleFlag){
            calSaleVO.setNosaleMsg("校验为无效");
            Logging.info("校验为无效");
            this.checkProdProductSaleFlag(prodProduct, false, msgFlag, packingProductCallBack);
            return calSaleVO;
        }


        String packageType = prodProduct.getPackageType();
        //组合产品供应商打包
        if (ProdProduct.PACKAGETYPE.SUPPLIER.name().equals(packageType)) {
            Logging.info("当前产品为供应商组合产品。");
            isSaleFlag = packingProductCallBack.checkSupplierPackProd(prodProduct, msgFlag);
            //组合产品自主打包
        } else {
            Logging.info("当前产品为lvmama组合产品。");
            isSaleFlag = packingProductCallBack.checkLvmamaPackProd(prodProduct, msgFlag);
        }

        if (isSaleFlag) {
            // 更新产品可售
            Logging.info("组合产品可售。");
            checkProdProductSaleFlag(prodProduct, true, msgFlag, packingProductCallBack);
            calSaleVO.setSaleFlag(true);
        } else {
            // 更新产品可售
            Logging.info("组合产品不可售。");
            checkProdProductSaleFlag(prodProduct, false, msgFlag, packingProductCallBack);
            calSaleVO.setNosaleMsg("游轮组合舱房组不可售");
        }
        return calSaleVO;
    }


    private void checkProdProductSaleFlag(ProdProduct prodProduct, boolean saleFlag,
                                          boolean msgFlag, PackingProductCallBack packingProductCallBack) throws Exception {
        String strSaleFlag = saleFlag ? Constant.Y_FLAG : Constant.N_FLAG;
        if(!prodProduct.getSaleFlag().equalsIgnoreCase(strSaleFlag)){
            packingProductCallBack.updateProductSaleFlag(prodProduct.getProductId(), strSaleFlag , msgFlag);
        }
    }
}
