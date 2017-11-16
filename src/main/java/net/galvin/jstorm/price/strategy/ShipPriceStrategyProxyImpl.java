package net.galvin.jstorm.price.strategy;

import net.galvin.jstorm.price.pojo.ComCalSaleVO;
import net.galvin.jstorm.price.pojo.ProdProduct;
import net.galvin.jstorm.price.pojo.ProdProductBranch;
import net.galvin.jstorm.price.pojo.SuppGoods;
import net.galvin.jstorm.price.strategy.callback.PackingProductCallBack;
import net.galvin.jstorm.price.strategy.callback.ProdProductCallBack;
import net.galvin.jstorm.price.strategy.callback.ProductBranchCallBack;
import net.galvin.jstorm.price.strategy.callback.SuppGoodsCallBack;
import net.galvin.jstorm.utils.Logging;
import java.util.List;

public class ShipPriceStrategyProxyImpl extends AbstractPriceStrategyEngine {

    /**
     * 商品维度触发可售计算
     */
    @Override
    protected void calcuSuppGoods(long suppGoodsId) throws Exception {
//        Logging.info("商品可售计算开始: suppGoodsId="+suppGoodsId);
//
//        //当前商品可售计算
//        Logging.info("当前商品可售计算 start");
//        SuppGoods suppGoods = shipSuppGoodsProxy.getSuppGoods(suppGoodsId);
//        ComCalSaleVO comCalSaleVO = this.calcuSingleSuppGoodsSale.calculate(suppGoods, true, shipSuppGoodsCallBack);
//        PriceStrategyUtil.sale(comCalSaleVO.isSaleFlag());
//        Logging.info("当前商品可售计算 end");
//
//        //当前商品对应的规格可售计算
//        Logging.info("当前商品对应的规格可售计算 start");
//        Long productBranchId = suppGoods.getProductBranchId();
//        ProdProductBranch prodProductBranch = shipProdProductBranchProxy.getProductBranch(productBranchId);
//        this.calcuSingleProdBranchSale.calculate(prodProductBranch, true, true, shipProductBranchCallBack);
//        Logging.info("当前商品对应的规格可售计算 end");
//
//        //当前商品对应的产品可售计算
//        Logging.info("当前商品对应的产品可售计算 start");
//        Long productId = suppGoods.getProductId();
//        ProdProduct prodProduct = shipProdProductProxy.getProdProduct(productId);
//        this.calcuSingleProdSale.calculate(prodProduct, true, true, shipProdProductCallBack);
//        Logging.info("当前商品对应的产品可售计算 end");
//
//        Logging.info("商品可售计算结束: suppGoodsId="+suppGoodsId);
    }

    /**
     * 产品规格维度触发可售计算
     */
    @Override
    protected void calcuProdProductBranch(long productBranchId) throws Exception {
//        Logging.info("产品规格可售计算开始: productBranchId="+productBranchId);
//
//        //当前规格可售计算
//        Logging.info("当前规格可售计算 start");
//        ProdProductBranch prodProductBranch = shipProdProductBranchProxy.getProductBranch(productBranchId);
//        ComCalSaleVO comCalSaleVO = this.calcuSingleProdBranchSale.calculate(prodProductBranch, true, false, shipProductBranchCallBack);
//        Logging.info("当前规格可售计算 end");
//
//        //规格对应的产品可售计算
//        Logging.info("当前规格对应的产品可售计算 start");
//        Long productId = prodProductBranch.getProductId();
//        ProdProduct prodProduct = shipProdProductProxy.getProdProduct(productId);
//        this.calcuSingleProdSale.calculate(prodProduct, true, false, shipProdProductCallBack);
//        Logging.info("当前规格对应的产品可售计算 end");
//
//        Logging.info("产品规格可售计算结束: productBranchId="+productBranchId);
    }

    /**
     * 产品维度触发可售计算
     */
    @Override
    protected void calcuProdProduct(long productId) throws Exception {
//        Logging.info("产品可售计算开始: productId="+productId);
//        ProdProduct prodProduct = shipProdProductProxy.getProdProduct(productId);
//        ComCalSaleVO comCalSaleVO = this.calcuSingleProdSale.calculate(prodProduct, true, false, shipProdProductCallBack);
//        Logging.info("产品可售计算结束: productId="+productId);
    }

    //商品回调
    private SuppGoodsCallBack shipSuppGoodsCallBack = new SuppGoodsCallBack() {

        @Override
        public boolean checkSuppGoodsTimePrice(long suppGoodsId, long categoryId) {
            boolean isSale = false;
            return isSale;
        }

        @Override
        public void updateSuppGoodsSellFlag(long suppGoodsId, String saleFlag, boolean msgFlag) throws Exception {

        }

        @Override
        public boolean check(SuppGoods suppGoods) {
            return true;
        }

    };

    //产品规格回调
    private ProductBranchCallBack shipProductBranchCallBack = new ProductBranchCallBack() {

        @Override
        public List<SuppGoods> querySuppGoodsList(Long prodBranchId) throws Exception {
            return null;
        }

        @Override
        public SuppGoodsCallBack getSuppGoodsCallBack() {
            return shipSuppGoodsCallBack;
        }

        @Override
        public void updateProductBranchSellFlag(long productBranchId, String saleFlag, boolean msgFlag) throws Exception {

        }

        @Override
        public boolean check(ProdProductBranch prodProductBranch) {
            return true;
        }

    };

    //单产品回调
    private ProdProductCallBack shipProdProductCallBack = new ProdProductCallBack() {

        @Override
        public List<ProdProductBranch> queryProdProductBranchList(long productId) throws Exception {
            return null;
        }

        @Override
        public ProductBranchCallBack getProductBranchCallBack() {
            return shipProductBranchCallBack;
        }

        @Override
        public void updateProductSaleFlag(Long productId, String saleFlag, boolean msgFlag) throws Exception {

        }

        @Override
        public boolean check(ProdProduct prodProduct) {
            return true;
        }

    };

    //打包产品回调
    private PackingProductCallBack packingProductCallBack = new PackingProductCallBack() {

        @Override
        public void updateProductSaleFlag(Long productId, String saleFlag, boolean msgFlag) throws Exception {

        }

        @Override
        public boolean checkSupplierPackProd(ProdProduct prodProduct, boolean msgFlag) throws Exception {
            boolean isSale= false;
            return isSale;
        }

        @Override
        public boolean checkLvmamaPackProd(ProdProduct prodProduct, boolean msgFlag) throws Exception {
            boolean isSale= false;
            return isSale;
        }

        @Override
        public boolean check(ProdProduct prodProduct) {
            return true;
        }

    };

}
