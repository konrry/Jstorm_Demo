package net.galvin.jstorm.price.pojo;

import java.io.Serializable;

public class ComCalSaleVO implements Serializable {
	
	/**
	 * 计算对象Id
	 */
	private Long objectId;
	
	/**
	 * 计算对象
	 */
	private String objectType;

	/**
	 * 不可售原因
	 */
	private String nosaleMsg;
	
	/**
	 * 可售标识
	 */
	private boolean saleFlag;
	
	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getNosaleMsg() {
		return nosaleMsg;
	}

	public void setNosaleMsg(String nosaleMsg) {
		this.nosaleMsg = nosaleMsg;
	}

	public boolean isSaleFlag() {
		return saleFlag;
	}

	public void setSaleFlag(boolean saleFlag) {
		this.saleFlag = saleFlag;
	}
}
