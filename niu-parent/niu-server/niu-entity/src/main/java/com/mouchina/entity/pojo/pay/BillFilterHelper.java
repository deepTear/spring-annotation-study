package com.mouchina.entity.pojo.pay;

/**
 * ClassName: BillScreeHelper 
 *
 * @Description 账单筛选枚举类
 * @author haolupeng
 * @date 2017年3月15日 下午1:53:18
 *
 */

public enum BillFilterHelper {
	
	//-----------------------收益
	INCOME_HELP_EARN(1,"帮赚收益"),
	INCOME_RED_ENVELOPE(2,"福袋收益"),
	INCOME_TASK_REWARD(3,"任务奖励"),
	INCOME_OFFICIAL_AVTIVE(4,"运营奖励"),
	INCOME_USER_AVTIVE(5,"活动奖励"),
	INCOME_FACING_EACH(6,"面对面收款"),
	INCOME_USER_RECHARGE(7,"用户充值"),
	INCOME_RELAY_RED(8,"接力福袋"),
	INCOME_NEW_USER(9,"新用户福袋"),
	INCOME_HOME_PAGE(10,"首页福袋"),
	INCOME_SHARE_YOUR(11,"分享收益"),
	INCOME_INVITACTION(12,"邀请收益"),
	INCOME_AGENT_MONTHLY_BALANCE(13,"月结收益"),
	
	INCOME_AGENT_BALANCE(14,"代理收益"),
	INCOME_AGENT_AREA_BALANCE(23,"区域收益"),
	INCOME_AGENT_RECOMMEND_BALANCE(24,"推荐收益"),
	
	//----------------------消费
	PAY_RELEASE_RED(15,"发布福袋"),
	PAY_RELEASE_RELAY(16,"开启福袋"),
	PAY_MALL_SHAP(17,"商城购买"),
	PAY_CASH_WITTH(18,"提现"),
	PAY_FACE_TOO(19,"面对面付款"),
	PAY_CHARITABLE_DONATION(20,"慈善捐款"),
	PAY_CASH_WITTH_FEE(22,"提现手续费"),
	INCOME_REFUND(21,"福袋退款"),
	INCOME_CHUANG_AVTIVE(25,"创客邀请收益"),
	INCOME_CHUANG_TASK(26,"创客任务收益"),
	PAY_CHUANG_WITH_CASHBALANCE(27,"用零钱晋升创客"),
	PAY_HEHUOREN_WITH_CASHBALANCE(28,"合伙人领取抽奖红包"),
	CHANGE_CONSUMER_MONEY(29,"购买消费金"),
	INIT_REGISTER_CONSUMER_MONEY(30,"新用户注册系统赠送"),
	USER_PAY_MENT(31,"用户优惠买单"),//优惠买单，用户付款
	BUSINESS_INCOME(32,"商户收款"),//优惠买单，商户收款
	CHANGE_ADVERT_COIN(33,"兑换广告币"),//
	//---------------------------

	SYSTEM_DEDUCTION(50, "系统扣除");
	
//	PAY_HOME_PAGE(20,"首页推广");
	private Integer marker;
	
	private String display;

	private BillFilterHelper(Integer marker, String display) {
		this.marker = marker;
		this.display = display;
	}

	public Integer getMarker() {
		return marker;
	}

	public void setMarker(Integer marker) {
		this.marker = marker;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
}
