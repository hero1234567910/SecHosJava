package com.basic.javaframe.common.enumresource;

import com.basic.javaframe.common.utils.EnumMessage;
/**
 * 订单状态枚举
* <p>Title: OrderStatuEnum</p>  
* <p>Description: </p>  
* @author hero
 */
public enum OrderStatuEnum implements EnumMessage{
	READYPAY(0,"待支付"),
	ALREADYPAY(1,"已支付"),
	RECEIVEDORDER(2,"已接单"),
	CANCALE(3,"已取消"),
	COMPLETED(4,"已完成"),
	ALREADYREFUND(5,"已退款");
	
	
	
	private final int code;
    private final String value;
    private OrderStatuEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
	@Override
	public int getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return value;
	}

}
