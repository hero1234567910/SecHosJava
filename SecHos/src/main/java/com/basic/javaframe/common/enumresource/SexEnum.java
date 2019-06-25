package com.basic.javaframe.common.enumresource;

import com.basic.javaframe.common.utils.EnumMessage;

/**
    *  性别枚举
  * @ClassName: SexEnum 
  * @Description: 性别枚举
  * @author hero
  * @date 2018年11月8日 上午10:24:55 
  *
  */
public enum SexEnum implements EnumMessage {
	MALE(0,"男"),
	FEMALE(1,"女");

	private final int code;
    private final String value;
    private SexEnum(int code, String value) {
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
