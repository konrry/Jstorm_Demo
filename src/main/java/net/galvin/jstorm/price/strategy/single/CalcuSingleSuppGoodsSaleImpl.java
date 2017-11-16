package net.galvin.jstorm.price.strategy.single;

import net.galvin.jstorm.price.pojo.ComCalSaleVO;
import net.galvin.jstorm.price.pojo.SuppGoods;
import net.galvin.jstorm.price.strategy.callback.SuppGoodsCallBack;
import net.galvin.jstorm.price.strategy.service.CalcuSuppGoodsSaleService;
import net.galvin.jstorm.price.utils.Constant;
import net.galvin.jstorm.utils.Logging;

/**
 * 商品(单品)可售计算
 */
public class CalcuSingleSuppGoodsSaleImpl implements CalcuSuppGoodsSaleService {

    @Override
    public ComCalSaleVO calculate(SuppGoods suppGoods, boolean msgFlag, SuppGoodsCallBack suppGoodsCallBack) throws Exception {

        Logging.info("校验商品对象是否为空.");
        if(suppGoods == null || suppGoods.getSuppGoodsId() == null || suppGoods.getCategoryId() == null){
            Logging.info("suppGoods is null or suppGoods.getSuppGoodsId() is null or suppGoods.getCategoryId() is null.");
            throw new Exception("suppGoods is null or suppGoods.getSuppGoodsId() is null or suppGoods.getCategoryId() is null.");
        }

        if(suppGoodsCallBack == null){
            throw new Exception("suppGoodsCallBack is null.");
        }

        long suppGoodsId = suppGoods.getSuppGoodsId();
        ComCalSaleVO calSaleVO = new ComCalSaleVO();
        calSaleVO.setObjectId(suppGoodsId);
        calSaleVO.setObjectType(Constant.OBJECT_TYPE.SUPP_GOODS.name());
        calSaleVO.setSaleFlag(false);

        Logging.info("判断商品是否有效："+suppGoods.getCancelFlag());
        if (Constant.N_FLAG.equalsIgnoreCase(suppGoods.getCancelFlag())) {
            checkSuppGoodsSaleFlag(suppGoods, false, msgFlag, suppGoodsCallBack);
            calSaleVO.setNosaleMsg("suppGoods[名称=" + suppGoods.getGoodsName() + "] cancelFlag is N;");
            Logging.info("suppGoods[名称=" + suppGoods.getGoodsName() + "] cancelFlag is N;");
            return calSaleVO;
        }

        boolean saleFlag = suppGoodsCallBack.check(suppGoods);
        if(!saleFlag){
            checkSuppGoodsSaleFlag(suppGoods, false, msgFlag, suppGoodsCallBack);
            Logging.info("suppGoodsCallBack.check(suppGoods)[suppGoodsId=" + suppGoods.getSuppGoodsId() + "] is not sale;");
            calSaleVO.setNosaleMsg("suppGoodsCallBack.check(suppGoods)[suppGoodsId=" + suppGoods.getSuppGoodsId() + "] is not sale;");
            return calSaleVO;
        }

        Logging.info("根据品类判断商品是否有时间价格表并且不是禁售的");
        boolean isSale = suppGoodsCallBack.checkSuppGoodsTimePrice(suppGoodsId,suppGoods.getCategoryId());
        if (!isSale) {
            checkSuppGoodsSaleFlag(suppGoods, false, msgFlag, suppGoodsCallBack);
            Logging.info("suppGoodsTimePrice[suppGoodsId=" + suppGoods.getSuppGoodsId() + "] is null or is not sale;");
        } else {
            calSaleVO.setSaleFlag(true);
            Logging.info("未禁售，有时间价格，ID："+suppGoods.getSuppGoodsId());
            checkSuppGoodsSaleFlag(suppGoods, true, msgFlag, suppGoodsCallBack);
        }
        return calSaleVO;
    }

    /**
     * 更新商品可售状态
     */
    private void checkSuppGoodsSaleFlag(SuppGoods suppGoods, boolean saleFlag, boolean msgFlag, SuppGoodsCallBack suppGoodsCallBack) throws Exception {
        String strSaleFlag = saleFlag ? Constant.Y_FLAG : Constant.N_FLAG;
        Logging.info("准备发送消息，ID："+suppGoods.getSuppGoodsId()+",strSaleFlag："+strSaleFlag+"，msgFlag："+msgFlag);
        if(!suppGoods.getOnlineFlag().equalsIgnoreCase(strSaleFlag)){
            suppGoodsCallBack.updateSuppGoodsSellFlag(suppGoods.getSuppGoodsId(),strSaleFlag, msgFlag);
        }
    }

}
