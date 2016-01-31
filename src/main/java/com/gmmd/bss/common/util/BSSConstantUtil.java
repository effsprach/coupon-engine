/**
 *
 * Copyright (c) 2013 GMM GRAMMY(DIGITAL DOMAIN). All Rights Reserved.
 */
package com.gmmd.bss.common.util;


public class BSSConstantUtil {

	public static final String COMMON_STATUS_ACTIVE = "active";
	public static final String COMMON_STATUS_INACTIVE = "inactive";
	public static final String CREATE_UPDATE_BY = "admin";

	// Database constants
	// CA_SERVICE_TYPE
	public enum CAServiceType {
		CONVERT_TICKET("convertTicket"), FORWARD_TICKET("forwardTicket"),UPDATE_ASSET_INVISIBLE("updateAssetInvisible");

		private final String value;

		private CAServiceType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	};

	// CA_SERVICE_STATUS
	public enum CAServiceStatus {
		ACTIVE("active"), CANCEL("cancel"), EXPIRE("expire"), COMPLETE("complete");

		private final String value;

		private CAServiceStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	};

	// CA_TOKEN (TYPE)
	public enum CATokenType {
		FORGOT("forgot"), RESENDACTIVATE("resendactivate"), FORWARDTICKET("forwardticket");

		private final String cATokenValue;

		private CATokenType(String cATokenValue) {
			this.cATokenValue = cATokenValue;
		}

		public String getCaTokenValue() {
			return cATokenValue;
		}

	};

	// CA_TOKEN (STATUS)
	public enum CATokenStatus {
		ACTIVE("active"), SUSPEND("suspend"), CANCEL("cancel"), EXPIRE("expire"), USED("used");

		private final String value;

		private CATokenStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	};

	// COM_CA_CRM
	public enum CMCACrm {
		FACEBOOK("facebook"), TWITTER("twitter");

		private final String cmCaCrmValue;

		private CMCACrm(String cmCaCrmValue) {
			this.cmCaCrmValue = cmCaCrmValue;
		}

		public String getCmCaCrmValue() {
			return cmCaCrmValue;
		}

	};

	// COM_CA_CLASS
	public enum CAClass {
		NORMAL(1, "normal");

		private final int caClassId;
		private final String caClassValue;

		private CAClass(int caClassId, String caClassValue) {
			this.caClassId = caClassId;
			this.caClassValue = caClassValue;
		}

		public int getCaClassId() {
			return caClassId;
		}

		public String getCaClassValue() {
			return caClassValue;
		}

	};

	// COM_ROLE
	public enum CMRole {
		MOBILE(1, "Mobile Operator"), SP(2, "Service Provisioning"), ONLINE_SERVICE(3, "GMM Online Service");

		private final int caRoleId;
		private final String caRoleValue;

		private CMRole(int caRoleId, String caRoleValue) {
			this.caRoleId = caRoleId;
			this.caRoleValue = caRoleValue;
		}

		public int getCaRoleId() {
			return caRoleId;
		}

		public String getCaRoleValue() {
			return caRoleValue;
		}

	};

	// COM_CA_SUBTYPE
	public enum CASubType {
		PERSON(100, "person"), FAMILY(101, "family"), SMALL_SHOP(102, "small shop");

		private final int caSubTypeId;
		private final String caSubTypeValue;

		private CASubType(int caSubTypeId, String caSubTypeValue) {
			this.caSubTypeId = caSubTypeId;
			this.caSubTypeValue = caSubTypeValue;
		}

		public int getCaSubTypeId() {
			return caSubTypeId;
		}

		public String getCaSubTypeValue() {
			return caSubTypeValue;
		}

	};

	// COM_CA_TYPE
	public enum CAType {
		INDIVIDUAL(100, " individual");

		private final int caTypeId;
		private final String caTypeValue;

		private CAType(int caTypeId, String caTypeValue) {
			this.caTypeId = caTypeId;
			this.caTypeValue = caTypeValue;
		}

		public int getCaTypeId() {
			return caTypeId;
		}

		public String getCaTypeValue() {
			return caTypeValue;
		}
	};

	// user login type
	public enum UserLoginType {
		Default("default"), FACEBOOK("facebook");

		private final String value;

		private UserLoginType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	};

	// COM_ASSET
	public enum AssetStatus {
		ACTIVE("active"), SUSPEND("suspend"), CANCEL("cancel"), EXPIRE("expire"), FORWARDING("forwarding"), FORWARDED("forwarded"), USED("used");

		private final String value;

		private AssetStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	};

	// adapter ticket parameter name
	public enum adapterTagNameTicket {
		updateTickets("updateTickets"), validateTickets("validateTickets"), validateTickets_userToken("userToken"), ticketses("ticketses"), ticketses_ticketsId("ticketsId"), tickets("tickets"), tickets_exId("extId"), tickets_ticketsId("ticketsId"), tickets_ticketsTypeId("ticketsTypeId"), tickets_status("status"), tickets_remark("remark");

		private final String value;

		private adapterTagNameTicket(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	};

	// GNR_TICKET_FEE_TYPE
	public enum GNRTicketCardTypeFEE {
		TICKET_TYPE_ELECTONIC_FEE("E"), TICKET_TYPE_PHYSICAL_FEE("P"), TICKET_TYPE_CONVERT_FEE("C");

		private final String value;

		private GNRTicketCardTypeFEE(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	};

	// COM_ASSET name
	public enum AssetAttrName {
		SALELOT_ID("saleLotId"), TICKET_ID("ticketId"), TICKET_TYPE("showsOfTicketType"), ASSET_QUANTITY("QUANTITY"), COUPON_REF_CODE("COUPON_REF_CODE"), ATTACH_ASSET_ID("ATTACH_ASSET_ID"), SHOWS_START_DATE("showsStartDated"), FORWARDED_ASSET_ID("FORWARDED_ASSET_ID");

		private final String value;

		private AssetAttrName(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	};

	// COM_ASSET ticket type
	public enum AssetAttr_TicketType {
		TICKET_TYPE_ELECTONIC("100"), TICKET_TYPE_PHYSICAL_PLASTIC("200"), TICKET_TYPE_PHYSICAL_PAPER("201"), TICKET_TYPE_PHYSICAL_NONESPECT("900");

		private final String value;

		private AssetAttr_TicketType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	};

	// promotion discountType
	public enum PromotionDiscountTypeFormat {
		AMOUNT("amount"), PERCENT("percent");

		private final String value;

		private PromotionDiscountTypeFormat(String discountTypeFormat) {
			this.value = discountTypeFormat;
		}

		public String getValue() {
			return value;
		}

	};

	// Ref page
	public enum refPageId {
		ORDER_SUMMARY(1), ORDER_HISTORY(2);

		private final int value;

		private refPageId(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	};

	// promotions type
	public enum PromotionsType {
		DISCOUNT_AND_PREMIUMS(1, "discount && premiums"), DISCOUNT(2, "discount"), PREMIUMS(3, "premiums");

		private PromotionsType(Integer typeId, String typeValue) {
			this.typeId = typeId;
			this.typeValue = typeValue;
		}

		private final Integer typeId;
		private final String typeValue;

		public Integer getTypeId() {
			return typeId;
		}

		public String getTypeValue() {
			return typeValue;
		}

	}

	// [COM_MAS_PROD_TYPE] COM_ASSET.ASSET_TYPE, COM_PRODUCT.PRODUCT_TYPE
	public enum ProductType {
		BOOKING(1, "Booking"), DOWNLOAD(2, "Download"), PRODUCT_CONSUMERS(3, "Product consumers"), TICKET(4, "Ticket"), COUPON(5, "Coupon"), DISCOUNT(6, "Discount"), PREMIUMS(7, "Premiums"), VOUCHER(8, "Voucher");

		private ProductType(Integer typeId, String typeValue) {
			this.typeId = typeId;
			this.typeValue = typeValue;
		}

		private final Integer typeId;
		private final String typeValue;

		public Integer getTypeId() {
			return typeId;
		}

		public String getTypeValue() {
			return typeValue;
		}

	};

	// [COM_MAS_PROD_SUBTYPE] COM_ASSET.ASSET_SUBTYPE,
	// COM_PRODUCT.PRODUCT_SUBTYPE
	public enum ProductSubType {

		E_BOOKLET(200, "e-booklet"), E_COUPON(201, "e-coupon"), MERCHANT(300, "Merchant"), INTERNAL(301, "Internal"), PAPER_TICKET(400, "Paper-Ticket"), E_TICKET(401, "e-Ticket"), PAPER_VOUCHER(800, "Paper-Voucher"), E_VOUCHER(801, "e-Voucher");

		private ProductSubType(int subTypeId, String subTypeValue) {
			this.subTypeId = subTypeId;
			this.subTypeValue = subTypeValue;
		}

		private final int subTypeId;
		private final String subTypeValue;

		public int getSubTypeId() {
			return subTypeId;
		}

		public String getSubTypeValue() {
			return subTypeValue;
		}
	};

	public enum AssetAttrStatus {
		ACTIVE("active"), CANCEL("cancel");

		private String assetAttrStatusValue;

		private AssetAttrStatus(String assetAttrStatusValue) {
			this.assetAttrStatusValue = assetAttrStatusValue;
		}

		public String getAssetAttrStatusValue() {
			return assetAttrStatusValue;
		}

		public void setAssetAttrStatusValue(String assetAttrStatusValue) {
			this.assetAttrStatusValue = assetAttrStatusValue;
		}

	};

	// premium attr name
	public enum PremiumAttrName {
		COUPON_PASS_CODE("couponPassCode"), COUPON_CAMPAIGN_ID("couponCampaignId");

		private String value;

		private PremiumAttrName(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	};

	// premium attr status
	public enum PremiumAttrStatus {
		ACTIVE("active"), CANCEL("cancel");

		private String value;

		private PremiumAttrStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	};

	// COM_CA
	public enum CustomerAccountStatus {
		PROSPECT("prospect"), ACTIVE("active"), INACTIVE("inactive"), CLOSED("closed");
		// Remove status LEAD("lead"), POTENTIAL("potential"), 23-07-2013

		private final String value;

		private CustomerAccountStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	// COM_ACTIVITY
	public enum ActivityStatus {
		OPEN("open"), PROCESSING("processing"), COMPLETE("complete"), ERROR("error");

		private final String value;

		private ActivityStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	// COM masActivityDefId
	public enum MasActivityDefId {
		NEW_CUSTOMER_EMAIL_VALIDATE(100), CUSTOMER_FORGOT_PASSWORD(101), EMAIL_VALIDATE_SUCCESS(102), EMAIL_FARWARD_TICKET(103), MOBILE_FARWARD_TICKET(104), REMIND_ONLINE_PAYMENT_EXPIRE(105), REMIND_OFFLINE_PAYMENT_EXPIRE(106), ORDER_SUMMARY(107);

		private final Integer value;

		private MasActivityDefId(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	};

	// COM_PAYMENT
	public enum NotifyExternalStatus {
		OPEN("open"), PROCESSING("processing"), COMPLETE("complete"), ERROR("error");

		private final String value;

		private NotifyExternalStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	// COM_PAYMENT
	public enum RecurringSubscriptionStatus {
		OPEN("open"), PROCESSING("processing"), COMPLETE("complete"), ERROR("error");

		private final String value;

		private RecurringSubscriptionStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	// COM_PAYMENT
	public enum PaymentStatus {

		CANCEL("cancel"), CANCELFAIL("cancel_fail"), CHANGE("change"), CHANGEFAIL("change_fail"), EXPIRE("expire"), EXPIRED("expired"), EXPIRED_FAIL("expired_fail"), FAIL("fail"), OPEN("open"), SUCCESS("success");

		private final String value;

		private PaymentStatus(String value) {

			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	// COM_PAYMENT_ATTR
	public enum PaymentAttrStatus {
		ACTIVE("active");

		private final String value;

		private PaymentAttrStatus(String value) {

			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	// delivery attribute status
	public enum DeliveryAttrStatus {
		ACTIVE("active"), INACTIVE("inactive");
		private final String value;

		private DeliveryAttrStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	// prePromotionByRedeem status
	public enum PrePromotionByRedeemStatus {
		ACTIVE("active"), INACTIVE("inactive");
		private final String value;

		private PrePromotionByRedeemStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	// CANCEL REASON FOR GMMD PGW
	public enum CancelPaymentGMMDPGWReason {
		CANCEL_BY_PAYER(101, "cancel by payer"), CANCEL_BY_MERCHANT(102, "cancel by merchant"), CANCEL_BY_OPERATION_TEAM(103, "cancel by operation team");

		private final Integer id;
		private final String name;

		private CancelPaymentGMMDPGWReason(int id, String name) {
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

	// COM_PAYMENT_ATTR
	public enum PaymentAttr {

		PAYMENT_ATTR_NAME_VOUCHER_CODE("Voucher_Code"), PAYMENT_ATTR_NAME_VOUCHER_PIN("Voucher_PIN");

		private final String AttrName;

		public String getAttrName() {
			return AttrName;
		}

		private PaymentAttr(String attrName) {
			AttrName = attrName;
		}
	}

	// COM_INVOICE
	public enum InvoiceStatus {
		OPEN("open"), COMPLETE("complete");

		private final String value;

		private InvoiceStatus(String value) {

			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	// ORDER_DELIVERY
	public enum OrderDeliveyStatus {
		OPEN("open"), COMPLETE("complete");

		private final String value;

		private OrderDeliveyStatus(String value) {

			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	// ORDER_DELIVERY_ADDRESS
	public enum OrderDeliveyAddress {

		ACTIVE("active"), INACTIVE("inactive");
		private final String value;

		private OrderDeliveyAddress(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	public enum OrderDeliveyName {
		DF_EMF(1, "Delivery DF EMS");

		private final Integer id;
		private final String value;

		private OrderDeliveyName(Integer id, String value) {
			this.id = id;
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public Integer getId() {
			return id;
		}
	};

	// OFM_WORK
	public enum WorkStatus {
		OPEN("open"), DECOMPOSE("decompose"), PROVISION("provision"), ERROR("error"), SUCCESS("success"), ORDER_RESP("response");

		private final String value;

		private WorkStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	public enum WorkComStatus {
		OPEN("open"), SUCCESS("success"), ERROR("error");

		private final String value;

		private WorkComStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	// OFM_TASK
	public enum TaskStatus {
		OPEN("open"), ERROR("error"), SUCCESS("success");

		private final String value;

		private TaskStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	//
	public enum TaskForceFlag {
		FORCE(true), NOT_FORCE(false);
		private final Boolean value;

		TaskForceFlag(Boolean value) {
			this.value = value;
		}

		public Boolean getValue() {
			return value;
		}
	};

	// OFM_CMD
	public enum CmdStatus {
		OPEN("open"), ERROR("error"), SUCCESS("success");
		private final String value;

		private CmdStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	// COM_CONTACT
	public enum ContactType {
		OWNER("owner"), GENERAL("general"), BILLING("billing"), DELIVERY("delivery");
		private final String value;

		private ContactType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	// COM_ORDER_ITEM status
	public enum OrderItemStatus {
		OPEN("open"), CANCEL("cancel"), CANCELFAIL("cancel_fail"), COMPLETE("complete"), EXPIRED("expired"), EXPIRED_FAIL("expired_fail"), FAIL("fail"), UPDATE_FAIL("update_fail");

		private final String value;

		private OrderItemStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	public enum Action {
		NO_ACTION("-"), ADD("add"), DELETE("delete"), UPDATE("update"), MODIFY("modify");
		private final String value;

		Action(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	// COM_ORDER fail reason
	public enum OrderFailReason {
		UPDATE_PRODUCT_TO_PROVIDER_FAIL(8001, "UPDATE PRODUCT TO PROVIDER FAIL");

		private final String msg;
		private final Integer code;

		private OrderFailReason(Integer code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public Integer getCode() {
			return code;
		}

		public String getMsg() {
			return msg;
		}
	};

	// COM_ORDER status
	public enum OrderStatus {
		OPEN("open"), COMPLETE("complete"), SUBMITTED("submitted"), FAIL("fail"), WAITING_PAYMENT("waiting payment"), CANCEL("cancel"), EXPIRING("expiring"), EXPIRED("expired"), EXPIRED_FAIL("expired_fail"), CANCELFAIL("cancel_fail"), UPDATE_FAIL("update_fail");

		private final String value;

		private OrderStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	//
	public enum ResponseStatus {
		WAITING_RESPONSE(1, "waiting response"), COMPLETE(2, "complete"), FAIL(3, "fail");
		private final Integer responseStatusId;
		private final String responseStatusValue;

		private ResponseStatus(Integer responseStatusId, String responseStatusValue) {
			this.responseStatusId = responseStatusId;
			this.responseStatusValue = responseStatusValue;
		}

		public Integer getResponseStatusId() {
			return responseStatusId;
		}

		public String getResponseStatusValue() {
			return responseStatusValue;
		}
	};

	// [COM_MAS_ORDER_TYPE] COM_ORDER_ITEM, OFM_LOOKUP_TASK_CMD
	public enum OrderType {
		NEW_REGISTER(1, "New Register"), CHANGE_MAIN_PACKAGE(2, "Change Main Package"), ADD_PACKAGE(3, "Add Package"), CANCEL_SERVICE(4, "Cancel Service"), SUSPEND_SERVICE(5, "Suspend Service"), UNSUSPEND_SERVICE(6, "Unsuspend Service"), TERMINATE_SERVICE(7, "Terminate Service"), NEW_REGIS_REVERSE(8, "New Register Reverse"), RENEW(9, "Renew"), ADD_MAIN_PACKAGE(10, "Add Main Package"), ADD_MAIN_PACKAGE_REVERSE(11, "Add Main Package Reverse"), CHANGE_ASSET_SUBSCRIPTION(12, "Change Asset Subscription"), CANCEL_SERVICE_REVERSE(13, "Cancel Service Reverse"), CREATE_ORDER(14, "CREATE ORDER");
		private final Integer orderTypeId;
		private final String typeName;

		OrderType(Integer orderTypeId, String typeName) {
			this.orderTypeId = orderTypeId;
			this.typeName = typeName;
		}

		public Integer getOrderTypeId() {
			return orderTypeId;
		}

		public String getTypeName() {
			return typeName;
		}
	}

	//
	public enum ApiUrl {
		OFM_KEY_URL("ofmURL"), SERVICE_PROVISIONING_KEY_URL("serviceProvisioningURL"), SMS_GATEWAY("smsGateWayURL"), COM_KEY_URL("comURL"), OFM_SP_RES_KEY_URL("ofmSpResURL"), OFM_SP_RES_KEY_PORT("ofmSpResPort");

		private final String key;

		ApiUrl(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}

	};

	public enum PaymentMethod {
		MOBILE_OPER_BUNDLE(1, "mobile oper bundle"), MOBILE_OPER_WALLET(2, "mobile oper wallet"), CASH(3, "cash"), BILL_PAYMENT(4, "bill payment"), PAY_CODE(5, "pay code"), ONLINE_DIRECT_DEBIT(6, "online direct debit"), CREDIT_CARD(7, "credit card"), INSTALLMENT(8, "installment"), VOUCHER(9, "voucher");

		private final Integer paymentMethodId;
		private final String paymentMethodValue;

		private PaymentMethod(Integer paymentMethodId, String paymentMethodValue) {
			this.paymentMethodId = paymentMethodId;
			this.paymentMethodValue = paymentMethodValue;
		}

		public Integer getPaymentMethodId() {
			return paymentMethodId;
		}

		public String getPaymentMethodValue() {
			return paymentMethodValue;
		}

	};

	public enum Priority {
		LOW("1", "low"), MEDIUM("5", "medium"), HIGH("10", "high");

		private final Short priorityId;
		private final String priorityValue;

		private Priority(String priorityId, String priorityValue) {
			this.priorityId = new Short(priorityId);
			this.priorityValue = priorityValue;
		}

		public Short getPriorityId() {
			return priorityId;
		}

		public String getPriorityValue() {
			return priorityValue;
		}
	};

	public enum Title {
		TITLE("-");

		private final String titleValue;

		private Title(String titleValue) {
			this.titleValue = titleValue;
		}

		public String getTitleValue() {
			return titleValue;
		}

	};

	public enum AppId {
		COM("COM"), OFM("OFM"), AMI("AMI"), EAI("EAI"), GMMTICKETE("GMMTICKETE"), GMMTICKETF("GMMTICKETF"), GMMTICKETW("GMMTICKETW"), GMMTICKETM("GMMTICKETM"), TGMMScanFN("TGMMScanFN"), TGMMSanIOS("TGMMScanIOS"), TGMMScanANDROID("TGMMScanANDROID");

		private final String appIdValue;

		private AppId(String appIdValue) {
			this.appIdValue = appIdValue;
		}

		public String getAppIdValue() {
			return appIdValue;
		}

	};

	// COM_NOTIFICATION.STATUS
	public enum NotificationStatus {
		CREATED("created"), ERROR_COMPLETE("error-complete"), ERROR_SEND("error-send"), NEW("new"), PROCESS_COMPLETE("process-complete"), PROCESS_SEND("process-send"), SENT("sent");

		private final String value;

		private NotificationStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	// COM_MAS_NOFT_TYPE.NOFT_CHANNEL
	public enum NotfChannel {

		EMAIL("EMAIL"), FAX("FAX"), MAIL("MAIL"), SMS("SMS");

		private final String value;

		private NotfChannel(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	}

	// COM_Address
	public enum AddressStatus {

		ACTIVE("active");

		private final String value;

		private AddressStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	}

	// COM_MAS_GENERIC
	public enum BaType {
		PAYPERUSE("PayPerUse"), POSTPAID("Postpaid"), PREPAID("Prepaid ");

		private final String value;

		private BaType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	// COM_MAS_GENERIC
	public enum Category {
		CHANNEL("CHANNEL");

		private final String value;

		private Category(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public enum operatorType {
		AIS("ais"), AIS3G("ais3g"), DTACT("dtact "), DATCT3G("dtact3g"), TRUEMOVE("truemove"), TREUMOVEH("truemoveh "), TRUEMOVE3G("truemove3g");

		private final String value;

		private operatorType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	// OFM_MAS_PROVISION_SYSTEM
	public enum SystemType {
		KK(1, "KK"), GMMZCCB(2, "GMMZCCB"), EAI(3, "EAI");
		private final Integer systemId;
		private final String systemName;

		SystemType(Integer systemId, String systemName) {
			this.systemId = systemId;
			this.systemName = systemName;
		}

		public Integer getSystemId() {
			return systemId;
		}

		public String getSystemName() {
			return systemName;
		}
	}

	public enum FixSalesChannel {
		WSS("wss");

		private final String value;

		private FixSalesChannel(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public enum PaymentChannel {
		BAY_PAYCODE(500), BBL_PAYCODE(501), KTB_PAYCODE(503), CTS_PAYCODE(507), BIGC_PAYCODE(508), MPAY_GMMZ_PAYCODE(509), CTS_GMMZ_PAYCODE(510), VOUCHER_GMMZ(901), MPAY_PAYCODE(512);
		private final Integer value;

		private PaymentChannel(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	public enum BuChannel {
		KKBOX(100), GMM(101), GMMZ(102), GMMZDTV(103);
		private final Integer value;

		private BuChannel(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}

	}

	// COM_PRODUCT
	public enum ProductStatus {
		ACTIVE("Active");
		private String value;

		private ProductStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	// COM_ORDER_Promotions_Premium_Item
	public enum OrderPromotionsPremiumItemStatus {
		OPEN("open"), COMPLETE("complete"), EXPIRED("expired"), EXPIRED_FAIL("expired_fail"), CANCEL("cancel"), CANCELFAIL("cancel_fail"), FAIL("fail");

		private final String value;

		private OrderPromotionsPremiumItemStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	// shelf type
	public enum shelfType {
		SHELF_CAMPAIGN(100), SHELF_CAMPAIGN_PROMOTE(101);

		private final Integer value;

		private shelfType(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}

	}

	// --------------------
	// App plateform
	public enum appPlateform {
		IOS("ios"), ANDROID("android");

		private final String value;

		private appPlateform(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	}
	
	public enum productStockType {
		SEPARATE(100), PROTOTYPE(101);

		private final Integer value;

		private productStockType(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}

	}
	
	public enum productAttrType {
		INHERITED(100), NOT_INHERITED(101);
		
		private final Integer value;
		
		private productAttrType(Integer value) {
			this.value = value;
		}
		
		public Integer getValue() {
			return value;
		}
		
	}

}
