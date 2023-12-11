package edu.bu.cs611.portfoliostocksystem.model;

import java.sql.Timestamp;

public class CustomerTradeOrder implements BaseModel {
  
  public enum Type { BUY, SELL }
  public enum Status { PENDING, OPEN, EXECUTED, CANCELED }

  private Integer id;
  private Integer securityId;
  private Integer cxTradingAcctId;
  private Long tradeQuantity;
  private Double tradePrice;
  private Type tradeType;
  private Status tradeStatus;
  private Timestamp tradeStartAt;
  private Timestamp tradeFinishAt;

  public CustomerTradeOrder(Integer securityId, Integer cxTradingAcctId, Long tradeQuantity,
      Double tradePrice, Type tradeType, Status tradeStatus, Timestamp tradeStartAt, Timestamp tradeFinishAt) {
    this(-1, securityId, cxTradingAcctId, tradeQuantity, tradePrice, tradeType, tradeStatus, tradeStartAt, tradeFinishAt);
  }

  public CustomerTradeOrder(Integer id, Integer securityId, Integer cxTradingAcctId, Long tradeQuantity,
      Double tradePrice, Type tradeType, Status tradeStatus, Timestamp tradeStartAt, Timestamp tradeFinishAt) {
    this.id = id;
    this.securityId = securityId;
    this.cxTradingAcctId = cxTradingAcctId;
    this.tradeQuantity = tradeQuantity;
    this.tradePrice = tradePrice;
    this.tradeType = tradeType;
    this.tradeStatus = tradeStatus;
    this.tradeStartAt = tradeStartAt;
    this.tradeFinishAt = tradeFinishAt;
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
  public Long getTradeQuantity() {
    return tradeQuantity;
  }
  public void setTradeQuantity(Long tradeQuantity) {
    this.tradeQuantity = tradeQuantity;
  }
  public Double getTradePrice() {
    return tradePrice;
  }
  public void setTradePrice(Double tradePrice) {
    this.tradePrice = tradePrice;
  }
  public Type getTradeType() {
    return tradeType;
  }
  public void setTradeType(Type tradeType) {
    this.tradeType = tradeType;
  }
  public Status getTradeStatus() {
    return tradeStatus;
  }
  public void setTradeStatus(Status tradeStatus) {
    this.tradeStatus = tradeStatus;
  }
  public Timestamp getTradeStartAt() {
    return tradeStartAt;
  }
  public void setTradeStartAt(Timestamp tradeStartAt) {
    this.tradeStartAt = tradeStartAt;
  }
  public Timestamp getTradeFinishAt() {
    return tradeFinishAt;
  }
  public void setTradeFinishAt(Timestamp tradeFinishAt) {
    this.tradeFinishAt = tradeFinishAt;
  }

  

}
