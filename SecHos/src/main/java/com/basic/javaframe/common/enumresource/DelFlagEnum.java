package com.basic.javaframe.common.enumresource;

import com.basic.javaframe.common.utils.EnumMessage;

public enum DelFlagEnum implements EnumMessage{
	YDELFLAG(1,"已删除"),
	NDELFLAG(0,"未删除");
	
	private final int code;
    private final String value;
    private DelFlagEnum(int code, String value) {
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
