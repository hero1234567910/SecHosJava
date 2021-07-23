package com.basic.javaframe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


/**
* <p>Title: SecHos_Healthy</p>
* <p>Description:</p>
* @author
*/
public class SecHos_Healthy implements Serializable {
   private static final long serialVersionUID = 1L;

   /****/
   private Integer rowId;
   /****/
   private String rowGuid;
   /****/
   private Date createTime;
   /****/
   private Integer delFlag;
   /****/
   private Integer sortSq;

   private String name;

   private Integer age;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
   private Date date;

   private String height;

   private String sex;

   private String weight;

   private String bloodPressure;

   private String heartRate;

   private String fastingBloodGlucose;

   private String postprandialBloodGlucose;

   private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public String getRowGuid() {
        return rowGuid;
    }

    public void setRowGuid(String rowGuid) {
        this.rowGuid = rowGuid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getSortSq() {
        return sortSq;
    }

    public void setSortSq(Integer sortSq) {
        this.sortSq = sortSq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getFastingBloodGlucose() {
        return fastingBloodGlucose;
    }

    public void setFastingBloodGlucose(String fastingBloodGlucose) {
        this.fastingBloodGlucose = fastingBloodGlucose;
    }

    public String getPostprandialBloodGlucose() {
        return postprandialBloodGlucose;
    }

    public void setPostprandialBloodGlucose(String postprandialBloodGlucose) {
        this.postprandialBloodGlucose = postprandialBloodGlucose;
    }
}
