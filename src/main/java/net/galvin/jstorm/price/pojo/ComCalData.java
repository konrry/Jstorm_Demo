package net.galvin.jstorm.price.pojo;

import java.io.Serializable;
import java.util.Date;

public class ComCalData implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long objectId;

	private String objectType;

	private Long productId;

	private String pushFlag;

	private Integer pushCount;

	private Integer lessPushCount;

	private String pushContent;

	private Date createTime;

	private Date updateTime;

	private String dataSouce;

	private Integer dataLevel;

	private Long costTime;

	private Long calculateCostTime;

	private Long categoryId;

	private String prodPackageType;

	private Integer status;

	public ComCalData(){}

	public ComCalData(Long objectId, String objectType, Long productId, String pushFlag, Integer pushCount, String pushContent, Date createTime, Date updateTime, String dataSouce, Integer dataLevel, Long categoryId, String prodPackageType, Integer status) {
		this.objectId = objectId;
		this.objectType = objectType;
		this.productId = productId;
		this.pushFlag = pushFlag;
		this.pushCount = pushCount;
		this.pushContent = pushContent;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.dataSouce = dataSouce;
		this.dataLevel = dataLevel;
		this.categoryId = categoryId;
		this.prodPackageType = prodPackageType;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
		this.objectType = objectType == null ? null : objectType.trim();
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getPushFlag() {
		return pushFlag;
	}

	public void setPushFlag(String pushFlag) {
		this.pushFlag = pushFlag == null ? null : pushFlag.trim();
	}

	public Integer getPushCount() {
		return pushCount;
	}

	public void setPushCount(Integer pushCount) {
		this.pushCount = pushCount;
	}

	public String getPushContent() {
		return pushContent;
	}

	public void setPushContent(String pushContent) {
		this.pushContent = pushContent == null ? null : pushContent.trim();
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

	public String getDataSouce() {
		return dataSouce;
	}

	public void setDataSouce(String dataSouce) {
		this.dataSouce = dataSouce == null ? null : dataSouce.trim();
	}

	public Integer getDataLevel() {
		return dataLevel;
	}

	public void setDataLevel(Integer dataLevel) {
		this.dataLevel = dataLevel;
	}

	public Long getCostTime() {
		return costTime;
	}

	public void setCostTime(Long costTime) {
		this.costTime = costTime;
	}

	public Long getCalculateCostTime() {
		return calculateCostTime;
	}

	public void setCalculateCostTime(Long calculateCostTime) {
		this.calculateCostTime = calculateCostTime;
	}

	public Integer getLessPushCount() {
		return lessPushCount;
	}

	public void setLessPushCount(Integer lessPushCount) {
		this.lessPushCount = lessPushCount;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getProdPackageType() {
		return prodPackageType;
	}

	public void setProdPackageType(String prodPackageType) {
		this.prodPackageType = prodPackageType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}