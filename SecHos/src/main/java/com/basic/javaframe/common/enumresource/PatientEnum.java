package com.basic.javaframe.common.enumresource;


public enum PatientEnum{
	OUTPATIENT("mz","门诊患者"),
	HOSPITALIZED("zy","住院患者");
	
	private final String code;
    private final String value;
    private PatientEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
	
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	
	public String getValue() {
		// TODO Auto-generated method stub
		return value;
	}
}
