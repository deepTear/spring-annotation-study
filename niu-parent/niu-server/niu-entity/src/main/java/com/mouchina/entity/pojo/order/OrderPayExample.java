package com.mouchina.entity.pojo.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderPayExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_order_pay
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_order_pay
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_order_pay
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_pay
     *
     * @mbggenerated
     */
    public OrderPayExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_pay
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_pay
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_pay
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_pay
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_pay
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_pay
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_pay
     *
     * @mbggenerated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_pay
     *
     * @mbggenerated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_pay
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_order_pay
     *
     * @mbggenerated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table tb_order_pay
     *
     * @mbggenerated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPayPriceIsNull() {
            addCriterion("pay_price is null");
            return (Criteria) this;
        }

        public Criteria andPayPriceIsNotNull() {
            addCriterion("pay_price is not null");
            return (Criteria) this;
        }

        public Criteria andPayPriceEqualTo(Integer value) {
            addCriterion("pay_price =", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceNotEqualTo(Integer value) {
            addCriterion("pay_price <>", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceGreaterThan(Integer value) {
            addCriterion("pay_price >", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_price >=", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceLessThan(Integer value) {
            addCriterion("pay_price <", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceLessThanOrEqualTo(Integer value) {
            addCriterion("pay_price <=", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceIn(List<Integer> values) {
            addCriterion("pay_price in", values, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceNotIn(List<Integer> values) {
            addCriterion("pay_price not in", values, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceBetween(Integer value1, Integer value2) {
            addCriterion("pay_price between", value1, value2, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_price not between", value1, value2, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayNoIsNull() {
            addCriterion("pay_no is null");
            return (Criteria) this;
        }

        public Criteria andPayNoIsNotNull() {
            addCriterion("pay_no is not null");
            return (Criteria) this;
        }

        public Criteria andPayNoEqualTo(String value) {
            addCriterion("pay_no =", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoNotEqualTo(String value) {
            addCriterion("pay_no <>", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoGreaterThan(String value) {
            addCriterion("pay_no >", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoGreaterThanOrEqualTo(String value) {
            addCriterion("pay_no >=", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoLessThan(String value) {
            addCriterion("pay_no <", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoLessThanOrEqualTo(String value) {
            addCriterion("pay_no <=", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoLike(String value) {
            addCriterion("pay_no like", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoNotLike(String value) {
            addCriterion("pay_no not like", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoIn(List<String> values) {
            addCriterion("pay_no in", values, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoNotIn(List<String> values) {
            addCriterion("pay_no not in", values, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoBetween(String value1, String value2) {
            addCriterion("pay_no between", value1, value2, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoNotBetween(String value1, String value2) {
            addCriterion("pay_no not between", value1, value2, "payNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andPayUserIdIsNull() {
            addCriterion("pay_user_id is null");
            return (Criteria) this;
        }

        public Criteria andPayUserIdIsNotNull() {
            addCriterion("pay_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andPayUserIdEqualTo(Long value) {
            addCriterion("pay_user_id =", value, "payUserId");
            return (Criteria) this;
        }

        public Criteria andPayUserIdNotEqualTo(Long value) {
            addCriterion("pay_user_id <>", value, "payUserId");
            return (Criteria) this;
        }

        public Criteria andPayUserIdGreaterThan(Long value) {
            addCriterion("pay_user_id >", value, "payUserId");
            return (Criteria) this;
        }

        public Criteria andPayUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("pay_user_id >=", value, "payUserId");
            return (Criteria) this;
        }

        public Criteria andPayUserIdLessThan(Long value) {
            addCriterion("pay_user_id <", value, "payUserId");
            return (Criteria) this;
        }

        public Criteria andPayUserIdLessThanOrEqualTo(Long value) {
            addCriterion("pay_user_id <=", value, "payUserId");
            return (Criteria) this;
        }

        public Criteria andPayUserIdIn(List<Long> values) {
            addCriterion("pay_user_id in", values, "payUserId");
            return (Criteria) this;
        }

        public Criteria andPayUserIdNotIn(List<Long> values) {
            addCriterion("pay_user_id not in", values, "payUserId");
            return (Criteria) this;
        }

        public Criteria andPayUserIdBetween(Long value1, Long value2) {
            addCriterion("pay_user_id between", value1, value2, "payUserId");
            return (Criteria) this;
        }

        public Criteria andPayUserIdNotBetween(Long value1, Long value2) {
            addCriterion("pay_user_id not between", value1, value2, "payUserId");
            return (Criteria) this;
        }

        public Criteria andPayChannelIsNull() {
            addCriterion("pay_channel is null");
            return (Criteria) this;
        }

        public Criteria andPayChannelIsNotNull() {
            addCriterion("pay_channel is not null");
            return (Criteria) this;
        }

        public Criteria andPayChannelEqualTo(Byte value) {
            addCriterion("pay_channel =", value, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelNotEqualTo(Byte value) {
            addCriterion("pay_channel <>", value, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelGreaterThan(Byte value) {
            addCriterion("pay_channel >", value, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelGreaterThanOrEqualTo(Byte value) {
            addCriterion("pay_channel >=", value, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelLessThan(Byte value) {
            addCriterion("pay_channel <", value, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelLessThanOrEqualTo(Byte value) {
            addCriterion("pay_channel <=", value, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelIn(List<Byte> values) {
            addCriterion("pay_channel in", values, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelNotIn(List<Byte> values) {
            addCriterion("pay_channel not in", values, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelBetween(Byte value1, Byte value2) {
            addCriterion("pay_channel between", value1, value2, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelNotBetween(Byte value1, Byte value2) {
            addCriterion("pay_channel not between", value1, value2, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayTerminalTypeIsNull() {
            addCriterion("pay_terminal_type is null");
            return (Criteria) this;
        }

        public Criteria andPayTerminalTypeIsNotNull() {
            addCriterion("pay_terminal_type is not null");
            return (Criteria) this;
        }

        public Criteria andPayTerminalTypeEqualTo(Byte value) {
            addCriterion("pay_terminal_type =", value, "payTerminalType");
            return (Criteria) this;
        }

        public Criteria andPayTerminalTypeNotEqualTo(Byte value) {
            addCriterion("pay_terminal_type <>", value, "payTerminalType");
            return (Criteria) this;
        }

        public Criteria andPayTerminalTypeGreaterThan(Byte value) {
            addCriterion("pay_terminal_type >", value, "payTerminalType");
            return (Criteria) this;
        }

        public Criteria andPayTerminalTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("pay_terminal_type >=", value, "payTerminalType");
            return (Criteria) this;
        }

        public Criteria andPayTerminalTypeLessThan(Byte value) {
            addCriterion("pay_terminal_type <", value, "payTerminalType");
            return (Criteria) this;
        }

        public Criteria andPayTerminalTypeLessThanOrEqualTo(Byte value) {
            addCriterion("pay_terminal_type <=", value, "payTerminalType");
            return (Criteria) this;
        }

        public Criteria andPayTerminalTypeIn(List<Byte> values) {
            addCriterion("pay_terminal_type in", values, "payTerminalType");
            return (Criteria) this;
        }

        public Criteria andPayTerminalTypeNotIn(List<Byte> values) {
            addCriterion("pay_terminal_type not in", values, "payTerminalType");
            return (Criteria) this;
        }

        public Criteria andPayTerminalTypeBetween(Byte value1, Byte value2) {
            addCriterion("pay_terminal_type between", value1, value2, "payTerminalType");
            return (Criteria) this;
        }

        public Criteria andPayTerminalTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("pay_terminal_type not between", value1, value2, "payTerminalType");
            return (Criteria) this;
        }

        public Criteria andPayAppAccountIsNull() {
            addCriterion("pay_app_account is null");
            return (Criteria) this;
        }

        public Criteria andPayAppAccountIsNotNull() {
            addCriterion("pay_app_account is not null");
            return (Criteria) this;
        }

        public Criteria andPayAppAccountEqualTo(String value) {
            addCriterion("pay_app_account =", value, "payAppAccount");
            return (Criteria) this;
        }

        public Criteria andPayAppAccountNotEqualTo(String value) {
            addCriterion("pay_app_account <>", value, "payAppAccount");
            return (Criteria) this;
        }

        public Criteria andPayAppAccountGreaterThan(String value) {
            addCriterion("pay_app_account >", value, "payAppAccount");
            return (Criteria) this;
        }

        public Criteria andPayAppAccountGreaterThanOrEqualTo(String value) {
            addCriterion("pay_app_account >=", value, "payAppAccount");
            return (Criteria) this;
        }

        public Criteria andPayAppAccountLessThan(String value) {
            addCriterion("pay_app_account <", value, "payAppAccount");
            return (Criteria) this;
        }

        public Criteria andPayAppAccountLessThanOrEqualTo(String value) {
            addCriterion("pay_app_account <=", value, "payAppAccount");
            return (Criteria) this;
        }

        public Criteria andPayAppAccountLike(String value) {
            addCriterion("pay_app_account like", value, "payAppAccount");
            return (Criteria) this;
        }

        public Criteria andPayAppAccountNotLike(String value) {
            addCriterion("pay_app_account not like", value, "payAppAccount");
            return (Criteria) this;
        }

        public Criteria andPayAppAccountIn(List<String> values) {
            addCriterion("pay_app_account in", values, "payAppAccount");
            return (Criteria) this;
        }

        public Criteria andPayAppAccountNotIn(List<String> values) {
            addCriterion("pay_app_account not in", values, "payAppAccount");
            return (Criteria) this;
        }

        public Criteria andPayAppAccountBetween(String value1, String value2) {
            addCriterion("pay_app_account between", value1, value2, "payAppAccount");
            return (Criteria) this;
        }

        public Criteria andPayAppAccountNotBetween(String value1, String value2) {
            addCriterion("pay_app_account not between", value1, value2, "payAppAccount");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andFinshiTimeIsNull() {
            addCriterion("finshi_time is null");
            return (Criteria) this;
        }

        public Criteria andFinshiTimeIsNotNull() {
            addCriterion("finshi_time is not null");
            return (Criteria) this;
        }

        public Criteria andFinshiTimeEqualTo(Date value) {
            addCriterion("finshi_time =", value, "finshiTime");
            return (Criteria) this;
        }

        public Criteria andFinshiTimeNotEqualTo(Date value) {
            addCriterion("finshi_time <>", value, "finshiTime");
            return (Criteria) this;
        }

        public Criteria andFinshiTimeGreaterThan(Date value) {
            addCriterion("finshi_time >", value, "finshiTime");
            return (Criteria) this;
        }

        public Criteria andFinshiTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("finshi_time >=", value, "finshiTime");
            return (Criteria) this;
        }

        public Criteria andFinshiTimeLessThan(Date value) {
            addCriterion("finshi_time <", value, "finshiTime");
            return (Criteria) this;
        }

        public Criteria andFinshiTimeLessThanOrEqualTo(Date value) {
            addCriterion("finshi_time <=", value, "finshiTime");
            return (Criteria) this;
        }

        public Criteria andFinshiTimeIn(List<Date> values) {
            addCriterion("finshi_time in", values, "finshiTime");
            return (Criteria) this;
        }

        public Criteria andFinshiTimeNotIn(List<Date> values) {
            addCriterion("finshi_time not in", values, "finshiTime");
            return (Criteria) this;
        }

        public Criteria andFinshiTimeBetween(Date value1, Date value2) {
            addCriterion("finshi_time between", value1, value2, "finshiTime");
            return (Criteria) this;
        }

        public Criteria andFinshiTimeNotBetween(Date value1, Date value2) {
            addCriterion("finshi_time not between", value1, value2, "finshiTime");
            return (Criteria) this;
        }

        public Criteria andPayStateIsNull() {
            addCriterion("pay_state is null");
            return (Criteria) this;
        }

        public Criteria andPayStateIsNotNull() {
            addCriterion("pay_state is not null");
            return (Criteria) this;
        }

        public Criteria andPayStateEqualTo(Byte value) {
            addCriterion("pay_state =", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateNotEqualTo(Byte value) {
            addCriterion("pay_state <>", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateGreaterThan(Byte value) {
            addCriterion("pay_state >", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateGreaterThanOrEqualTo(Byte value) {
            addCriterion("pay_state >=", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateLessThan(Byte value) {
            addCriterion("pay_state <", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateLessThanOrEqualTo(Byte value) {
            addCriterion("pay_state <=", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateIn(List<Byte> values) {
            addCriterion("pay_state in", values, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateNotIn(List<Byte> values) {
            addCriterion("pay_state not in", values, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateBetween(Byte value1, Byte value2) {
            addCriterion("pay_state between", value1, value2, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateNotBetween(Byte value1, Byte value2) {
            addCriterion("pay_state not between", value1, value2, "payState");
            return (Criteria) this;
        }

        public Criteria andThirdPartyPayNoIsNull() {
            addCriterion("third_party_pay_no is null");
            return (Criteria) this;
        }

        public Criteria andThirdPartyPayNoIsNotNull() {
            addCriterion("third_party_pay_no is not null");
            return (Criteria) this;
        }

        public Criteria andThirdPartyPayNoEqualTo(String value) {
            addCriterion("third_party_pay_no =", value, "thirdPartyPayNo");
            return (Criteria) this;
        }

        public Criteria andThirdPartyPayNoNotEqualTo(String value) {
            addCriterion("third_party_pay_no <>", value, "thirdPartyPayNo");
            return (Criteria) this;
        }

        public Criteria andThirdPartyPayNoGreaterThan(String value) {
            addCriterion("third_party_pay_no >", value, "thirdPartyPayNo");
            return (Criteria) this;
        }

        public Criteria andThirdPartyPayNoGreaterThanOrEqualTo(String value) {
            addCriterion("third_party_pay_no >=", value, "thirdPartyPayNo");
            return (Criteria) this;
        }

        public Criteria andThirdPartyPayNoLessThan(String value) {
            addCriterion("third_party_pay_no <", value, "thirdPartyPayNo");
            return (Criteria) this;
        }

        public Criteria andThirdPartyPayNoLessThanOrEqualTo(String value) {
            addCriterion("third_party_pay_no <=", value, "thirdPartyPayNo");
            return (Criteria) this;
        }

        public Criteria andThirdPartyPayNoLike(String value) {
            addCriterion("third_party_pay_no like", value, "thirdPartyPayNo");
            return (Criteria) this;
        }

        public Criteria andThirdPartyPayNoNotLike(String value) {
            addCriterion("third_party_pay_no not like", value, "thirdPartyPayNo");
            return (Criteria) this;
        }

        public Criteria andThirdPartyPayNoIn(List<String> values) {
            addCriterion("third_party_pay_no in", values, "thirdPartyPayNo");
            return (Criteria) this;
        }

        public Criteria andThirdPartyPayNoNotIn(List<String> values) {
            addCriterion("third_party_pay_no not in", values, "thirdPartyPayNo");
            return (Criteria) this;
        }

        public Criteria andThirdPartyPayNoBetween(String value1, String value2) {
            addCriterion("third_party_pay_no between", value1, value2, "thirdPartyPayNo");
            return (Criteria) this;
        }

        public Criteria andThirdPartyPayNoNotBetween(String value1, String value2) {
            addCriterion("third_party_pay_no not between", value1, value2, "thirdPartyPayNo");
            return (Criteria) this;
        }

        public Criteria andPayNoLikeInsensitive(String value) {
            addCriterion("upper(pay_no) like", value.toUpperCase(), "payNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLikeInsensitive(String value) {
            addCriterion("upper(order_no) like", value.toUpperCase(), "orderNo");
            return (Criteria) this;
        }

        public Criteria andPayAppAccountLikeInsensitive(String value) {
            addCriterion("upper(pay_app_account) like", value.toUpperCase(), "payAppAccount");
            return (Criteria) this;
        }

        public Criteria andThirdPartyPayNoLikeInsensitive(String value) {
            addCriterion("upper(third_party_pay_no) like", value.toUpperCase(), "thirdPartyPayNo");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table tb_order_pay
     *
     * @mbggenerated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table tb_order_pay
     *
     * @mbggenerated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}