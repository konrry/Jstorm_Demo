package net.galvin.jstorm.strategy;

import net.galvin.jstorm.utils.Utils;

import java.util.Date;

public class ComCalData {

    private String id;
    private long productId;
    private long objectId;
    private OBJECT_TYPE objectType;
    private Date createTime;
    private Date overTime;

    public ComCalData(long productId, long objectId, OBJECT_TYPE objectType){
        this.id = Utils.UID.ID();
        this.productId = productId;
        this.objectId = objectId;
        this.objectType = objectType;
        this.createTime = new Date();
    }


    public enum OBJECT_TYPE {
        PROD_PRODUCT, PRODUCT_BRANCH, SUPP_GOODS;
    }

    public String getId() {
        return id;
    }

    public long getProductId() {
        return productId;
    }

    public long getObjectId() {
        return objectId;
    }

    public OBJECT_TYPE getObjectType() {
        return objectType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }


    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("{ ");
        stringBuffer.append("id:"+id);
        stringBuffer.append(", productId:" + productId);
        stringBuffer.append(", objectId:" + objectId);
        stringBuffer.append(", objectType:" + objectType.name());
        stringBuffer.append(", createTime:" + createTime == null ? "NULL" : Utils.format(createTime));
        stringBuffer.append(", overTime:" + overTime == null ? "NULL" : Utils.format(overTime));
        stringBuffer.append(" }");
        return stringBuffer.toString();
    }

}
