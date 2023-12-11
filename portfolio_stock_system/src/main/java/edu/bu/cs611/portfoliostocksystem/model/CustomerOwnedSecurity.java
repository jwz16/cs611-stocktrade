package edu.bu.cs611.portfoliostocksystem.model;

import java.sql.Timestamp;

public class CustomerOwnedSecurity implements BaseModel {

  private Integer id;
  private Integer securityId;
  private Integer cxTradingAcctId;
  private Long quantity;
  private Double boughtPrice;
  private Timestamp boughtAt;

  public CustomerOwnedSecurity(Integer securityId, Integer cxTradingAcctId, Long quantity, Double boughtPrice, Timestamp boughtAt) {
    this(-1, securityId, cxTradingAcctId, quantity, boughtPrice, boughtAt);
  }

  public CustomerOwnedSecurity(Integer id, Integer securityId, Integer cxTradingAcctId, Long quantity,
      Double boughtPrice, Timestamp boughtAt) {
    this.id = id;
    this.securityId = securityId;
    this.cxTradingAcctId = cxTradingAcctId;
    this.quantity = quantity;
    this.boughtPrice = boughtPrice;
    this.boughtAt = boughtAt;
  }

  /* Getters and Setters */
  @Override
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public Integer getSecurityId() {
    return securityId;
  }
  public void setSecurityId(Integer securityId) {
    this.securityId = securityId;
  }
  public Integer getCxTradingAcctId() {
    return cxTradingAcctId;
  }
  public void setCxTradingAcctId(Integer cxTradingAcctId) {
    this.cxTradingAcctId = cxTradingAcctId;
  }
  public Long getQuantity() {
    return quantity;
  }
  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }
  public Double getBoughtPrice() {
    return boughtPrice;
  }
  public void setBoughtPrice(Double boughtPrice) {
    this.boughtPrice = boughtPrice;
  }
  public Timestamp getBoughtAt() {
    return boughtAt;
  }
  public void setBoughtAt(Timestamp boughtAt) {
    this.boughtAt = boughtAt;
  }

}
