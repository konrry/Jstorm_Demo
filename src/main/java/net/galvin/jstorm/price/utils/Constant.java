package net.galvin.jstorm.price.utils;

public class Constant {

	public static final String Y_FLAG = "Y";
	public static final String N_FLAG = "N";

	public enum OBJECT_TYPE{
		PROD_PRODUCT("产品"),
		PROD_PRODUCT_BRANCH("产品规格"),
		SUPP_GOODS("商品"),
		SUPP_GOODS_TIME_PRICE("商品时间价格表");
		
		private String objectType;
		
		OBJECT_TYPE(String objectType){
			this.objectType = objectType;
		}
		
		 public String getCode(){
            return this.name();
        }
		 
		 public String getCnName(){
            return this.objectType;
        }
	}
}

