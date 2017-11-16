package net.galvin.jstorm.price.strategy.single;

import net.galvin.jstorm.price.pojo.ComCalSaleVO;
import net.galvin.jstorm.price.pojo.ProdProduct;
import net.galvin.jstorm.price.pojo.ProdProductBranch;
import net.galvin.jstorm.price.pojo.ProdProductCalSaleVO;
import net.galvin.jstorm.price.strategy.callback.ProdProductCallBack;
import net.galvin.jstorm.price.strategy.callback.ProductBranchCallBack;
import net.galvin.jstorm.price.strategy.service.CalcuProdBranchSaleService;
import net.galvin.jstorm.price.strategy.service.CalcuProdSaleService;
import net.galvin.jstorm.price.utils.Constant;
import net.galvin.jstorm.utils.Logging;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * 产品(单品)可售计算
 */
public class CalcuSingleProdSaleImpl implements CalcuProdSaleService {

    private CalcuProdBranchSaleService calcuSingleProdBranchSale;

    @Override
    public ComCalSaleVO calculate(ProdProduct prodProduct, boolean msgFlag, boolean sourceFlag,
                                  ProdProductCallBack prodProductCallBack) throws Exception {

        Logging.info("判断 prodProduct 是否为空。");
        if(prodProduct == null || prodProduct.getProductId() == null){
            throw new Exception("prodProduct is null or prodProduct.getProductId() is null.");
        }

        if(prodProductCallBack == null){
            throw new Exception("prodProductCallBack is null.");
        }

        Long productId = prodProduct.getProductId();

        ComCalSaleVO calSaleVO = new ComCalSaleVO();
        calSaleVO.setObjectId(productId);
        calSaleVO.setObjectType(Constant.OBJECT_TYPE.PROD_PRODUCT.name());
        calSaleVO.setSaleFlag(false);


        Logging.info("判断 prodProduct 是否有效。");
        if (Constant.N_FLAG.equalsIgnoreCase(prodProduct.getCancelFlag())) {
            checkProdProductSaleFlag(prodProduct, false, msgFlag, prodProductCallBack);
            calSaleVO.setNosaleMsg("product cancelFlag is N");
            Logging.info("product cancelFlag is N");
            return calSaleVO;
        }

        boolean isSale = prodProductCallBack.check(prodProduct);
        if(!isSale){
            Logging.info("prodProductCallBack.check(prodProduct)[productId=" + prodProduct.getProductId() + "] is not sale;");
            calSaleVO.setNosaleMsg("prodProductCallBack.check(prodProduct)[productId=" + prodProduct.getProductId() + "] is not sale;");
            checkProdProductSaleFlag(prodProduct, false, msgFlag, prodProductCallBack);
            return calSaleVO;
        }

        Logging.info("获取产品下所有的规格。");
        List<ProdProductBranch> prodProductBranchList = prodProductCallBack.queryProdProductBranchList(productId);
        if (CollectionUtils.isEmpty(prodProductBranchList)) {
            checkProdProductSaleFlag(prodProduct, false, msgFlag, prodProductCallBack);
            calSaleVO.setNosaleMsg("productBranch is empty");
            Logging.info("productBranch is empty");
            return calSaleVO;
        }

        Logging.info("判断产品下是否存在可售的规格 msgFlag:"+msgFlag+",sourceFlag"+sourceFlag);
        ProdProductCalSaleVO productCalSaleVO = checkProductBranchList(prodProductBranchList, msgFlag, sourceFlag,prodProductCallBack.getProductBranchCallBack());

        int productBranchSaleCount = productCalSaleVO.getProductBranchSaleCount();

        if (productBranchSaleCount > 0) {
            Logging.info("存在可售的规格。");
            calSaleVO.setSaleFlag(true);
            this.checkProdProductSaleFlag(prodProduct, true, msgFlag, prodProductCallBack);
        } else {
            Logging.info("无可售的规格。");
            calSaleVO.setNosaleMsg(productCalSaleVO.getNosaleMsg());
            this.checkProdProductSaleFlag(prodProduct, false, msgFlag, prodProductCallBack);
        }
        return calSaleVO;
    }

    public void checkProdProductSaleFlag(ProdProduct prodProduct, boolean saleFlag,
                                         boolean msgFlag, ProdProductCallBack prodProductCallBack) throws Exception {
        String strSaleFlag = saleFlag ? Constant.Y_FLAG : Constant.N_FLAG;

        if(!prodProduct.getSaleFlag().equalsIgnoreCase(strSaleFlag)) {
            prodProductCallBack.updateProductSaleFlag(prodProduct.getProductId(), strSaleFlag, msgFlag);
        }
    }

    private ProdProductCalSaleVO checkProductBranchList(List<ProdProductBranch> productBranchList, boolean msgFlag,
                                                        boolean sourceFlag, ProductBranchCallBack productBranchCallBack) throws Exception {

        int productBranchSaleFlagCount = 0;
        StringBuilder productBranchNosaleMsg = new StringBuilder();
        for (ProdProductBranch productBranch : productBranchList) {
            if (sourceFlag) {
                if (Constant.Y_FLAG.equalsIgnoreCase(productBranch.getSaleFlag())) {
                    productBranchSaleFlagCount++;
                    break;
                }
            }
            ComCalSaleVO productBranchCalVO = calcuSingleProdBranchSale.calculate(productBranch, msgFlag, sourceFlag,productBranchCallBack);
            if (productBranchCalVO == null) {
                continue;
            }

            if (productBranchCalVO.isSaleFlag()) {
                productBranchSaleFlagCount++;
            } else {
                productBranchNosaleMsg.append(productBranchCalVO.getNosaleMsg());
            }
        }

        ProdProductCalSaleVO productCalSaleVO = new ProdProductCalSaleVO();
        productCalSaleVO.setProductBranchSaleCount(productBranchSaleFlagCount);
        productCalSaleVO.setNosaleMsg(productBranchNosaleMsg.toString());

        return productCalSaleVO;
    }

}
