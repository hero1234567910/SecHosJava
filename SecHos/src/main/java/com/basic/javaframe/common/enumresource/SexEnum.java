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
	;

	@Override
	public int getCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}
//    MEN("1","男"),
//    WOMEN("2","女"),
//    UNKNOWN("0","保密");
//    private final String code;
//    private final String value;
//    private SexEnum(String code, String value) {
//        this.code = code;
//        this.value = value;
//    }
//    @Override
//    public String getCode() { return code;}
//    @Override
//    public String getValue() { return value; }
}
