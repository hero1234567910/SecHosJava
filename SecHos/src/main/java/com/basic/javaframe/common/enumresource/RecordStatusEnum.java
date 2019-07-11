package com.basic.javaframe.common.enumresource;

import com.basic.javaframe.common.utils.EnumMessage;

public enum RecordStatusEnum implements EnumMessage {
	READYHANDLE(1,"待处理"),
	ALREADYHANDLE(2,"已处理");
	
	
	private final int code;
    private final String value;
    private RecordStatusEnum(int code, String value) {
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
