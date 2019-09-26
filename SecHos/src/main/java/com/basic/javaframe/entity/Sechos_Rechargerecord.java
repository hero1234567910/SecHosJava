package com.basic.javaframe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
* <p>Title: Sechos_Rechargerecord</p>
* <p>Description:</p>
* @author
*/
public class Sechos_Rechargerecord implements Serializable {
   private static final long serialVersionUID = 1L;

   /****/
   private Integer rowId;
   /****/
   private String rowGuid;
   /****/
   @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
   private Date createTime;
   /****/
   private Integer delFlag;
   /****/
   private Integer sortSq;
   /**商户订单号**/
   private String merchantNumber;
   /**充值金额**/
   private BigDecimal payMoney;
   /**支付时间**/
   @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
   private Date payTime;
   /**记录状态**/
   private Integer recordStatus;
   /**患者guid**/
   private String patientRowGuid;
   /**充值类型**/
   private Integer payType;
   
   private String patid;
   
   private String patientName;
   
   private String ghxh;
   
   private String sjh;
   
   private String zje;
   
   private String yfje;
   
   private String zfje;
   
   private String zfsj;
   
   private String yyxh;
   
   private String hisddh;
   
   private String yjlsh;
   
   private String blh;
   
   private String jzlsh;
   
   public String getJzlsh() {
	return jzlsh;
}
public void setJzlsh(String jzlsh) {
	this.jzlsh = jzlsh;
}
public String getBlh() {
	return blh;
}
public void setBlh(String blh) {
	this.blh = blh;
}
public String getHisddh() {
	return hisddh;
}
public void setHisddh(String hisddh) {
	this.hisddh = hisddh;
}
public String getYjlsh() {
	return yjlsh;
}
public void setYjlsh(String yjlsh) {
	this.yjlsh = yjlsh;
}
public String getYyxh() {
	return yyxh;
}
public void setYyxh(String yyxh) {
	this.yyxh = yyxh;
}
public String getZfsj() {
	return zfsj;
}
public void setZfsj(String zfsj) {
	this.zfsj = zfsj;
}
public String getGhxh() {
	return ghxh;
	}
	public void setGhxh(String ghxh) {
		this.ghxh = ghxh;
	}
	public String getSjh() {
		return sjh;
	}
	public void setSjh(String sjh) {
		this.sjh = sjh;
	}
	public String getZje() {
		return zje;
	}
	public void setZje(String zje) {
		this.zje = zje;
	}
	public String getYfje() {
		return yfje;
	}
	public void setYfje(String yfje) {
		this.yfje = yfje;
	}
	public String getZfje() {
		return zfje;
	}
	public void setZfje(String zfje) {
		this.zfje = zfje;
	}
	public String getPatid() {
		return patid;
	}
	public void setPatid(String patid) {
		this.patid = patid;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
/**
    * 设置：
    */
   public void setRowId(Integer rowId) {
       this.rowId = rowId;
   }
   /**
    * 获取：
    */
   public Integer getRowId() {
       return rowId;
   }
   /**
    * 设置：
    */
   public void setRowGuid(String rowGuid) {
       this.rowGuid = rowGuid;
   }
   /**
    * 获取：
    */
   public String getRowGuid() {
       return rowGuid;
   }
   /**
    * 设置：
    */
   public void setCreateTime(Date createTime) {
       this.createTime = createTime;
   }
   /**
    * 获取：
    */
   public Date getCreateTime() {
       return createTime;
   }
   /**
    * 设置：
    */
   public void setDelFlag(Integer delFlag) {
       this.delFlag = delFlag;
   }
   /**
    * 获取：
    */
   public Integer getDelFlag() {
       return delFlag;
   }
   /**
    * 设置：
    */
   public void setSortSq(Integer sortSq) {
       this.sortSq = sortSq;
   }
   /**
    * 获取：
    */
   public Integer getSortSq() {
       return sortSq;
   }
   /**
    * 设置：商户订单号
    */
   public void setMerchantNumber(String merchantNumber) {
       this.merchantNumber = merchantNumber;
   }
   /**
    * 获取：商户订单号
    */
   public String getMerchantNumber() {
       return merchantNumber;
   }
   /**
    * 设置：充值金额
    */
   public void setPayMoney(BigDecimal payMoney) {
       this.payMoney = payMoney;
   }
   /**
    * 获取：充值金额
    */
   public BigDecimal getPayMoney() {
       return payMoney;
   }
   /**
    * 设置：支付时间
    */
   public void setPayTime(Date payTime) {
       this.payTime = payTime;
   }
   /**
    * 获取：支付时间
    */
   public Date getPayTime() {
       return payTime;
   }
   /**
    * 设置：记录状态
    */
   public void setRecordStatus(Integer recordStatus) {
       this.recordStatus = recordStatus;
   }
   /**
    * 获取：记录状态
    */
   public Integer getRecordStatus() {
       return recordStatus;
   }
   /**
    * 设置：患者guid
    */
   public void setPatientRowGuid(String patientRowGuid) {
       this.patientRowGuid = patientRowGuid;
   }
   /**
    * 获取：患者guid
    */
   public String getPatientRowGuid() {
       return patientRowGuid;
   }
   /**
    * 设置：充值类型
    */
   public void setPayType(Integer payType) {
       this.payType = payType;
   }
   /**
    * 获取：充值类型
    */
   public Integer getPayType() {
       return payType;
   }
}
