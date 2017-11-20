package net.galvin.jstorm.price.strategy.single;

import net.galvin.jstorm.price.pojo.ComCalSaleVO;
import net.galvin.jstorm.price.pojo.ProdProductBranch;
import net.galvin.jstorm.price.pojo.ProdProductBranchCalSaleVO;
import net.galvin.jstorm.price.pojo.SuppGoods;
import net.galvin.jstorm.price.strategy.callback.ProductBranchCallBack;
import net.galvin.jstorm.price.strategy.callback.SuppGoodsCallBack;
import net.galvin.jstorm.price.strategy.service.CalcuProdBranchSaleService;
import net.galvin.jstorm.price.strategy.service.CalcuSuppGoodsSaleService;
import net.galvin.jstorm.price.utils.Constant;
import net.galvin.jstorm.utils.Logging;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品(单品)规格可售计算
 */
@Service
public class CalcuSingleProdBranchSaleImpl implements CalcuProdBranchSaleService {

    private CalcuSuppGoodsSaleService calcuSingleSuppGoodsSale;

    @Override
    public ComCalSaleVO calculate(ProdProductBranch prodProductBranch, boolean msgFlag,
                                  boolean sourceFlag, ProductBranchCallBack productBranchCallBack) throws Exception {

        Logging.info("判断 prodProductBranch 是否为空");
        if (prodProductBranch == null || prodProductBranch.getProductBranchId() == null) {
            Logging.info("prodProductBranch is null or prodProductBranch.getProductBranchId() is null.");
            throw new Exception("prodProductBranch is null or prodProductBranch.getProductBranchId() is null.");
        }

        if(productBranchCallBack == null){
            throw new Exception("productBranchCallBack is null.");
        }

        long productBranchId = prodProductBranch.getProductBranchId();

        ComCalSaleVO calSaleVO = new ComCalSaleVO();
        calSaleVO.setObjectId(productBranchId);
        calSaleVO.setObjectType(Constant.OBJECT_TYPE.PROD_PRODUCT_BRANCH.name());
        calSaleVO.setSaleFlag(false);

        //规格有效性判断
        Logging.info("判断 prodProductBranch 是否有效");
        if (Constant.N_FLAG.equalsIgnoreCase(prodProductBranch.getCancelFlag())) {
            calSaleVO.setNosaleMsg("productBranch[名称=" + prodProductBranch.getBranchName() + "] cancelFlag is N;");
            Logging.info("productBranch[名称=" + prodProductBranch.getBranchName() + "] cancelFlag is N;");
            checkProductBranchSaleFlag(prodProductBranch, false, msgFlag, productBranchCallBack);
            return calSaleVO;
        }

        boolean isSale = productBranchCallBack.check(prodProductBranch);
        if(!isSale){
            Logging.info("suppGoodsCallBack.check(suppGoods)[suppGoodsId=" + prodProductBranch.getProductBranchId() + "] is not sale;");
            calSaleVO.setNosaleMsg("suppGoodsCallBack.check(suppGoods)[suppGoodsId=" + prodProductBranch.getProductBranchId() + "] is not sale;");
            checkProductBranchSaleFlag(prodProductBranch, false, msgFlag, productBranchCallBack);
            return calSaleVO;
        }

        // 获取主规格对应的有效商品列表
        Logging.info("获取该规格下的所有商品");
        List<SuppGoods> suppGoodsList = productBranchCallBack.querySuppGoodsList(productBranchId);
        if (CollectionUtils.isEmpty(suppGoodsList)) {
            calSaleVO.setNosaleMsg("productBranch get suppGoodsList size is null or error");
            Logging.info("productBranch get suppGoodsList size is null or error");
            checkProductBranchSaleFlag(prodProductBranch, false, msgFlag, productBranchCallBack);
            return calSaleVO;
        }

        // 判断产品规格下商品列表是否可售
        Logging.info("判断该规格下是否存在可售的商品 sourceFlag:"+sourceFlag+",msgFlag:"+msgFlag);
        ProdProductBranchCalSaleVO prodBranchCalSaleVO = checkSuppGoodsList
                (suppGoodsList, sourceFlag, msgFlag, productBranchCallBack.getSuppGoodsCallBack());
        int goodsSaleFlagCount = prodBranchCalSaleVO.getGoodsSaleCount();
        if (goodsSaleFlagCount > 0) {
            calSaleVO.setNosaleMsg("存在可售的商品。");
            Logging.info("存在可售的商品。");
            checkProductBranchSaleFlag(prodProductBranch, true, msgFlag, productBranchCallBack);
            calSaleVO.setSaleFlag(true);
        } else {
            calSaleVO.setNosaleMsg("不存在可售的商品。");
            Logging.info("不存在可售的商品。");
            checkProductBranchSaleFlag(prodProductBranch, false, msgFlag, productBranchCallBack);
            calSaleVO.setNosaleMsg(prodBranchCalSaleVO.getNosaleMsg());
        }

        return calSaleVO;
    }

    /**
     * 校验规格下的产品是否可售。
     */
    private ProdProductBranchCalSaleVO checkSuppGoodsList(List<SuppGoods> suppGoodsList, boolean sourceFlag,
                                                          boolean msgFlag, SuppGoodsCallBack suppGoodsCallBack) throws Exception {
        int goodsSaleFlagCount = 0;
        StringBuilder suppGoodsNoSaleMsg = new StringBuilder();
        for (SuppGoods suppGoods : suppGoodsList) {
            // 表示从消息来得
            if (sourceFlag && Constant.Y_FLAG.equalsIgnoreCase(suppGoods.getOnlineFlag())) {
                goodsSaleFlagCount++;
                break;
            }
            ComCalSaleVO suppGoodsCalSaleVO = calcuSingleSuppGoodsSale.calculate(suppGoods, msgFlag, suppGoodsCallBack);
            if (suppGoodsCalSaleVO == null) {
                continue;
            }

            if (suppGoodsCalSaleVO.isSaleFlag()) {
                goodsSaleFlagCount++;
            } else {
                suppGoodsNoSaleMsg.append(suppGoodsCalSaleVO.getNosaleMsg());
            }
        }

        ProdProductBranchCalSaleVO productBranchCalSaleVO = new ProdProductBranchCalSaleVO();
        productBranchCalSaleVO.setGoodsSaleCount(goodsSaleFlagCount);
        productBranchCalSaleVO.setNosaleMsg(suppGoodsNoSaleMsg.toString());

        return productBranchCalSaleVO;
    }

    /**
     * 更新产品规格可售状态
     */
    private void checkProductBranchSaleFlag(ProdProductBranch productBranch, boolean saleFlag,
                                            boolean msgFlag, ProductBranchCallBack productBranchCallBack) throws Exception {
        if (saleFlag) {
            if ("N".equalsIgnoreCase(productBranch.getSaleFlag()) || StringUtils.isEmpty(productBranch.getSaleFlag())) {
                productBranchCallBack.updateProductBranchSellFlag(productBranch.getProductBranchId(), "Y", msgFlag);
            }
        }else if ("Y".equalsIgnoreCase(productBranch.getSaleFlag())) {
            productBranchCallBack.updateProductBranchSellFlag(productBranch.getProductBranchId(), "N", msgFlag);
        }
    }

}
