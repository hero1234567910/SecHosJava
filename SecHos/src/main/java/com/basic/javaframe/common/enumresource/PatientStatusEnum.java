package com.basic.javaframe.common.enumresource;

import com.basic.javaframe.common.utils.EnumMessage;

public enum PatientStatusEnum implements EnumMessage{
	OUTPATIENT(1,"门诊"),
	HOSPATIENT(2,"住院");
	
	private final int code;
    private final String value;
    private PatientStatusEnum(int code, String value) {
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
