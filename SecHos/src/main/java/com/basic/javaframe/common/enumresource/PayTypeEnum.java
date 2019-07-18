package com.basic.javaframe.common.enumresource;

import com.basic.javaframe.common.utils.EnumMessage;

public enum PayTypeEnum implements EnumMessage{
	ADVANCEPAY(0,"预交金结算"),
	GHPAY(1,"挂号结算"),
	MZPAY(2,"门诊结算");
	
	private final int code;
    private final String value;
    private PayTypeEnum(int code, String value) {
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
