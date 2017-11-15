package net.galvin.jstorm.price.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/12.
 */
public class SuppGoods implements Serializable {

        private static final long serialVersionUID = 2053515765136237506L;

        private Long categoryId;
        private Long suppGoodsId;//商品组ID
        private Long contractId;
        private Long supplierId;
        private String supplierName;
        private Long productId;
        private Long productBranchId;
        private String goodsName;

        private String payTarget;//支付对象

        private Long adult;//成人数
        private Long child;

        private String cancelFlag;//是否有效
        private String onlineFlag;//是否可售（计算得出,有价格有库存可卖）-----(改自是否上架)

        private String bu;//bu

        private Long minQuantity;//最少起订量
        private Long maxQuantity;//最大起订量

        //币种
        private String currencyType;
        //价格类型
        private String priceType;

        private Date createTime;
        private Date updateTime;
        //门票票种
        private String goodsSpec;
        //门票票种显示名称
        private String goodsSpecName;
        //是否期票
        private String aperiodicFlag;
        //是否买断
        private String buyoutFlag;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getSuppGoodsId() {
        return suppGoodsId;
    }

    public void setSuppGoodsId(Long suppGoodsId) {
        this.suppGoodsId = suppGoodsId;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductBranchId() {
        return productBranchId;
    }

    public void setProductBranchId(Long productBranchId) {
        this.productBranchId = productBranchId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPayTarget() {
        return payTarget;
    }

    public void setPayTarget(String payTarget) {
        this.payTarget = payTarget;
    }

    public Long getAdult() {
        return adult;
    }

    public void setAdult(Long adult) {
        this.adult = adult;
    }

    public Long getChild() {
        return child;
    }

    public void setChild(Long child) {
        this.child = child;
    }

    public String getCancelFlag() {
        return cancelFlag;
    }

    public void setCancelFlag(String cancelFlag) {
        this.cancelFlag = cancelFlag;
    }

    public String getOnlineFlag() {
        return onlineFlag;
    }

    public void setOnlineFlag(String onlineFlag) {
        this.onlineFlag = onlineFlag;
    }

    public String getBu() {
        return bu;
    }

    public void setBu(String bu) {
        this.bu = bu;
    }

    public Long getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Long minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Long getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Long maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public String getGoodsSpecName() {
        return goodsSpecName;
    }

    public void setGoodsSpecName(String goodsSpecName) {
        this.goodsSpecName = goodsSpecName;
    }

    public String getAperiodicFlag() {
        return aperiodicFlag;
    }

    public void setAperiodicFlag(String aperiodicFlag) {
        this.aperiodicFlag = aperiodicFlag;
    }

    public String getBuyoutFlag() {
        return buyoutFlag;
    }

    public void setBuyoutFlag(String buyoutFlag) {
        this.buyoutFlag = buyoutFlag;
    }
}
