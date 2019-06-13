package com.basic.javaframe.common.enumresource;

import com.basic.javaframe.common.utils.EnumMessage;

/**
 * 是否接单枚举
* <p>Title: ReceiveOrderEnum</p>  
* <p>Description: </p>  
* @author hero
 */
public enum ReceiveOrderEnum implements EnumMessage{
	AlreadyClose(0,"已关闭"),
	AlreadyOpen(1,"已开启");
	
	private final int code;
    private final String value;
    private ReceiveOrderEnum(int code, String value) {
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
