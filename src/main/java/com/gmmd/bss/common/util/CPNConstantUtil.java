package com.gmmd.bss.common.util;

public class CPNConstantUtil {
	
	public static final String CREATE_UPDATE_BY = "admin";
	
	
	public static class GenericLovName {
		final public static String GENERATE_COUPON_CODE_NUMBER = "GENERATE_COUPON_CODE_NUMBER";
		final public static String CPN_SIMPLE_GENERATE = "CPN_SIMPLE_GENERATE";
	}
	
	
	public static class CampaignConditionParamName {
		//CREATE_CAMPAIGE
		final public static String MAX_NUMBER_COUPON_PER_CAMPAIGN = "MAX_NUMBER_COUPON_PER_CAMPAIGN";
		//CREATE_COUPON
		final public static String LIMIT_NUMBER_USAGE_PER_COUPON = "LIMIT_NUMBER_USAGE_PER_COUPON";
		final public static String DEFAULT_CPN_USAGE_SDATE = "DEFAULT_CPN_USAGE_SDATE";
		final public static String DEFAULT_CPN_USAGE_EDATE = "DEFAULT_CPN_USAGE_EDATE";
	}
	
	public enum CouponStatus {
		READY_TO_USE("ready to use"), RESERVE("reserve"), USED("used"), HOLD("hold"), EXPIRED("expired");

		private final String value;

		private CouponStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	};
	
	public enum CouponUsageStatus {
		USED("used"), RESERVE("reserve"), READY_TO_USE("ready to use");

		private final String value;

		private CouponUsageStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	};
	
	public enum CouponType {
		
		CPN_ONE_USE_ONCE(100 ,"Coupon Code Can Only Use One Time")
		, CPN_ONE_USE_MANY(101, "Coupon Code Can Use Many Time");

		private final Integer id;
		private final String name;
		
		private CouponType(Integer id, String name) {
			this.id = id;
			this.name = name;
		}
		public Integer getId() {
			return id;
		}
		public String getName() {
			return name;
		}

	};
	
	public enum AppId {
		ECOM("ECOM", "100");

		private final String name;
		private final String code;
		
		private AppId(String name, String code) {
			this.name = name;
			this.code = code;
		}
		
		public String getName() {
			return name;
		}
		public String getCode() {
			return code;
		}
	};


}
