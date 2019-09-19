package com.basic.javaframe.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



 /**
 * <p>Title: SechosDrug</p>
 * <p>Description:</p>
 * @author 
 */
public class SechosDrug implements Serializable {
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
	/****/
	private Integer cflx;
	/****/
	private String cfmxxh;
	/****/
	private String cfxh;
	/****/
	private String dxmmc;
	/****/
	private BigDecimal je;
	/****/
	private String jldw;
	/****/
	private Integer jlzt;
	/****/
	private String jxmc;
	/****/
	private String jzlsh;
	/****/
	private String kfksdm;
	/****/
	private String kfksmc;
	/****/
	private String kfsj;
	/****/
	private String kfysdm;
	/****/
	private String kfysmc;
	/****/
	private String lcxmmc;
	/****/
	private String pcmc;
	/****/
	private Integer pfyzt;
	/****/
	private Integer sfzt;
	/****/
	private Integer shzt;
	/****/
	private BigDecimal xmdj;
	/****/
	private String xmdm;
	/****/
	private String xmmc;
	/****/
	private String xmsl;
	/****/
	private String yfmc;
	/****/
	private String ypbz;
	/****/
	private String ypdw;
	/****/
	private String ypgg;
	/****/
	private String ypjl;
	/****/
	private String yyts;
	/****/
	private String zyjf;
	
	private String patientRowGuid;
	
	public String getPatientRowGuid() {
		return patientRowGuid;
	}
	public void setPatientRowGuid(String patientRowGuid) {
		this.patientRowGuid = patientRowGuid;
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
	 * 设置：
	 */
	public void setCflx(Integer cflx) {
		this.cflx = cflx;
	}
	/**
	 * 获取：
	 */
	public Integer getCflx() {
		return cflx;
	}
	/**
	 * 设置：
	 */
	public void setCfmxxh(String cfmxxh) {
		this.cfmxxh = cfmxxh;
	}
	/**
	 * 获取：
	 */
	public String getCfmxxh() {
		return cfmxxh;
	}
	/**
	 * 设置：
	 */
	public void setCfxh(String cfxh) {
		this.cfxh = cfxh;
	}
	/**
	 * 获取：
	 */
	public String getCfxh() {
		return cfxh;
	}
	/**
	 * 设置：
	 */
	public void setDxmmc(String dxmmc) {
		this.dxmmc = dxmmc;
	}
	/**
	 * 获取：
	 */
	public String getDxmmc() {
		return dxmmc;
	}
	/**
	 * 设置：
	 */
	public void setJe(BigDecimal je) {
		this.je = je;
	}
	/**
	 * 获取：
	 */
	public BigDecimal getJe() {
		return je;
	}
	/**
	 * 设置：
	 */
	public void setJldw(String jldw) {
		this.jldw = jldw;
	}
	/**
	 * 获取：
	 */
	public String getJldw() {
		return jldw;
	}
	/**
	 * 设置：
	 */
	public void setJlzt(Integer jlzt) {
		this.jlzt = jlzt;
	}
	/**
	 * 获取：
	 */
	public Integer getJlzt() {
		return jlzt;
	}
	/**
	 * 设置：
	 */
	public void setJxmc(String jxmc) {
		this.jxmc = jxmc;
	}
	/**
	 * 获取：
	 */
	public String getJxmc() {
		return jxmc;
	}
	/**
	 * 设置：
	 */
	public void setJzlsh(String jzlsh) {
		this.jzlsh = jzlsh;
	}
	/**
	 * 获取：
	 */
	public String getJzlsh() {
		return jzlsh;
	}
	/**
	 * 设置：
	 */
	public void setKfksdm(String kfksdm) {
		this.kfksdm = kfksdm;
	}
	/**
	 * 获取：
	 */
	public String getKfksdm() {
		return kfksdm;
	}
	/**
	 * 设置：
	 */
	public void setKfksmc(String kfksmc) {
		this.kfksmc = kfksmc;
	}
	/**
	 * 获取：
	 */
	public String getKfksmc() {
		return kfksmc;
	}
	/**
	 * 设置：
	 */
	public void setKfsj(String kfsj) {
		this.kfsj = kfsj;
	}
	/**
	 * 获取：
	 */
	public String getKfsj() {
		return kfsj;
	}
	/**
	 * 设置：
	 */
	public void setKfysdm(String kfysdm) {
		this.kfysdm = kfysdm;
	}
	/**
	 * 获取：
	 */
	public String getKfysdm() {
		return kfysdm;
	}
	/**
	 * 设置：
	 */
	public void setKfysmc(String kfysmc) {
		this.kfysmc = kfysmc;
	}
	/**
	 * 获取：
	 */
	public String getKfysmc() {
		return kfysmc;
	}
	/**
	 * 设置：
	 */
	public void setLcxmmc(String lcxmmc) {
		this.lcxmmc = lcxmmc;
	}
	/**
	 * 获取：
	 */
	public String getLcxmmc() {
		return lcxmmc;
	}
	/**
	 * 设置：
	 */
	public void setPcmc(String pcmc) {
		this.pcmc = pcmc;
	}
	/**
	 * 获取：
	 */
	public String getPcmc() {
		return pcmc;
	}
	/**
	 * 设置：
	 */
	public void setPfyzt(Integer pfyzt) {
		this.pfyzt = pfyzt;
	}
	/**
	 * 获取：
	 */
	public Integer getPfyzt() {
		return pfyzt;
	}
	/**
	 * 设置：
	 */
	public void setSfzt(Integer sfzt) {
		this.sfzt = sfzt;
	}
	/**
	 * 获取：
	 */
	public Integer getSfzt() {
		return sfzt;
	}
	/**
	 * 设置：
	 */
	public void setShzt(Integer shzt) {
		this.shzt = shzt;
	}
	/**
	 * 获取：
	 */
	public Integer getShzt() {
		return shzt;
	}
	/**
	 * 设置：
	 */
	public void setXmdj(BigDecimal xmdj) {
		this.xmdj = xmdj;
	}
	/**
	 * 获取：
	 */
	public BigDecimal getXmdj() {
		return xmdj;
	}
	/**
	 * 设置：
	 */
	public void setXmdm(String xmdm) {
		this.xmdm = xmdm;
	}
	/**
	 * 获取：
	 */
	public String getXmdm() {
		return xmdm;
	}
	/**
	 * 设置：
	 */
	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}
	/**
	 * 获取：
	 */
	public String getXmmc() {
		return xmmc;
	}
	/**
	 * 设置：
	 */
	public void setXmsl(String xmsl) {
		this.xmsl = xmsl;
	}
	/**
	 * 获取：
	 */
	public String getXmsl() {
		return xmsl;
	}
	/**
	 * 设置：
	 */
	public void setYfmc(String yfmc) {
		this.yfmc = yfmc;
	}
	/**
	 * 获取：
	 */
	public String getYfmc() {
		return yfmc;
	}
	/**
	 * 设置：
	 */
	public void setYpbz(String ypbz) {
		this.ypbz = ypbz;
	}
	/**
	 * 获取：
	 */
	public String getYpbz() {
		return ypbz;
	}
	/**
	 * 设置：
	 */
	public void setYpdw(String ypdw) {
		this.ypdw = ypdw;
	}
	/**
	 * 获取：
	 */
	public String getYpdw() {
		return ypdw;
	}
	/**
	 * 设置：
	 */
	public void setYpgg(String ypgg) {
		this.ypgg = ypgg;
	}
	/**
	 * 获取：
	 */
	public String getYpgg() {
		return ypgg;
	}
	/**
	 * 设置：
	 */
	public void setYpjl(String ypjl) {
		this.ypjl = ypjl;
	}
	/**
	 * 获取：
	 */
	public String getYpjl() {
		return ypjl;
	}
	/**
	 * 设置：
	 */
	public void setYyts(String yyts) {
		this.yyts = yyts;
	}
	/**
	 * 获取：
	 */
	public String getYyts() {
		return yyts;
	}
	/**
	 * 设置：
	 */
	public void setZyjf(String zyjf) {
		this.zyjf = zyjf;
	}
	/**
	 * 获取：
	 */
	public String getZyjf() {
		return zyjf;
	}
}
