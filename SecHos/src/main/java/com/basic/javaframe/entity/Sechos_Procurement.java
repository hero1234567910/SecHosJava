package com.basic.javaframe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * <p>Title: Sechos_Procurement</p>
 * <p>Description:</p>
 *
 * @author
 */
public class Sechos_Procurement implements Serializable {
    private static final long serialVersionUID = 1L;

    /****/
    private Integer rowId;
    /****/
    private String rowGuid;
    /****/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /****/
    private Integer delFlag;
    /****/
    private Integer sortSq;
    /**
     * 采购单号
     **/
    private String purchaseOrderNum;
    /**
     * 下单日期
     **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date purchaseDate;

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public Date getInboundDate() {
        return inboundDate;
    }

    /**
     * 入库日期
     **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date inboundDate;
    /**
     * 采购状态
     **/
    private Integer purchaseStatus;
    /**
     * 采购备注
     **/
    private String purchaseNote;
    /**
     * 采购总价
     **/
    private BigDecimal purchasePrice;

    /**采购人Guid**/
    private String personGuid;

    public String getPersonGuid() {
        return personGuid;
    }

    public void setPersonGuid(String personGuid) {
        this.personGuid = personGuid;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setInboundDate(Date inboundDate) {
        this.inboundDate = inboundDate;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
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
     * 设置：采购单号
     */
    public void setPurchaseOrderNum(String purchaseOrderNum) {
        this.purchaseOrderNum = purchaseOrderNum;
    }

    /**
     * 获取：采购单号
     */
    public String getPurchaseOrderNum() {
        return purchaseOrderNum;
    }

    /**
     * 设置：采购状态
     */
    public void setPurchaseStatus(Integer purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    /**
     * 获取：采购状态
     */
    public Integer getPurchaseStatus() {
        return purchaseStatus;
    }

    /**
     * 设置：采购备注
     */
    public void setPurchaseNote(String purchaseNote) {
        this.purchaseNote = purchaseNote;
    }

    /**
     * 获取：采购备注
     */
    public String getPurchaseNote() {
        return purchaseNote;
    }

    @Override
    public String toString() {
        return "Sechos_Procurement{" +
                "rowId=" + rowId +
                ", rowGuid='" + rowGuid + '\'' +
                ", createTime=" + createTime +
                ", delFlag=" + delFlag +
                ", sortSq=" + sortSq +
                ", purchaseOrderNum='" + purchaseOrderNum + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", inboundDate=" + inboundDate +
                ", purchaseStatus=" + purchaseStatus +
                ", purchaseNote='" + purchaseNote + '\'' +
                ", purchasePrice=" + purchasePrice +
                '}';
    }
}
