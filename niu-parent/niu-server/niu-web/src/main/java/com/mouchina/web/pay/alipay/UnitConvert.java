package com.mouchina.web.pay.alipay;

import java.math.BigDecimal;

public class UnitConvert {

	/**
	 * 金额为分的格式
	 */
	public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";
	
	/**
	 * 金额为元的格式
	 */
	public static final  String CURRENCY_YUAN_REGEX="^(([0-9]|([1-9][0-9]{0,9}))((.[0-9]{1,2})?))$";

	/**
	 * 将分为单位的转换为元 （除100）
	 *
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static BigDecimal fen2Yuan(String amount) {
	    if (!amount.matches(CURRENCY_FEN_REGEX)) {
	        throw new RuntimeException("金额格式错误|"+amount);
	    }
	    return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100));
	}

	/**
	 * 将分为单位的转换为元 （除100）
	 *
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static BigDecimal fen2Yuan(int amount) {
	    if (!String.valueOf(amount).matches(CURRENCY_FEN_REGEX)) {
	        throw new RuntimeException("金额格式错误|"+amount);
	    }
	    return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100));
	}

	public static BigDecimal fen2Yuan(Long amount) {
	    if (!String.valueOf(amount).matches(CURRENCY_FEN_REGEX)) {
	        throw new RuntimeException("金额格式错误|"+amount);
	    }
	    return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100));
	}
	
	 /**
     * 将分为单位的转换为元 （除100）
     *
     * @param amount
     * @return
     * @throws Exception
     */
    public static String fen2YuanStr(String amount) {
        if (!amount.matches(CURRENCY_FEN_REGEX)) {
            throw new RuntimeException("金额格式错误|"+amount);
        }
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();
    }


    /**
     * 将元为单位的参数转换为分 , 只对小数点前2位支持
     *
     * @param yuan
     * @return
     * @throws Exception
     */
    public static int yuan2FenInt(String yuan){
        BigDecimal fenBd = new BigDecimal(yuan).multiply(new BigDecimal(100));
        fenBd = fenBd.setScale(0, BigDecimal.ROUND_HALF_UP);
        return fenBd.intValue();
    }
	
	
}
