package com.mouchina.entity.pojo.pay;

/**
 * 
 * ClassName: TableNameHelper 
 *
 * @Description 数据表枚举类
 *
 * @author 刘刚
 *
 * @date 2017年3月22日 下午2:26:20
 *
 */
public enum TableNameHelper {

	TB_ADVERT("tb_advert","广告信息"),
	TB_ORDER("tb_order","订单信息"),
	TB_RED_ENVELOPE("tb_red_envelope","用户红包信息"),
	TB_USER_ASSETS("tb_user_assets","用户账户信息"),
	TB_USER_INCOME_SUM("tb_user_income_sum","用户邀请好友领取红包收益统计信息"),
	TB_USER_INCOME_AWARD("tb_user_income_award","用户活动奖励信息"),
	TB_WITHDRAWAL_HISTORY_ORDER("tb_withdrawal_history_order","提现历史信息"),
	TB_AGENT_INCOME_DETAIL("tb_agent_income_detail","代理商收益明细信息"),
	TB_AGENT_ADVERTISE_INCOME("tb_agent_advertise_income","代理商发广告收益信息");
	
	private String marker;
	
	private String display;

	private TableNameHelper(String marker, String display) {
		this.marker = marker;
		this.display = display;
	}

	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
	
}
