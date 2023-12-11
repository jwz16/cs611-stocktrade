package edu.bu.cs611.portfoliostocksystem.model;

import java.sql.Timestamp;

public class CustomerMessage {
  
  private Integer id;
  private Integer customerId;
  private String message;
  private Timestamp sentAt;

  public CustomerMessage(Integer customerId, String message, Timestamp sentAt) {
    this(-1, customerId, message, sentAt);
  }

  public CustomerMessage(Integer id, Integer customerId, String message, Timestamp sentAt) {
    this.id = id;
    this.customerId = customerId;
    this.message = message;
    this.sentAt = sentAt;
  }

  public CustomerMessage(CustomerMessage cxMsg) {
    this(cxMsg.customerId, cxMsg.message, cxMsg.sentAt);
  }

  /* Gettters and Setters */
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public Integer getCustomerId() {
    return customerId;
  }
  public void setCustomerId(Integer customerId) {
    this.customerId = customerId;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  public Timestamp getSentAt() {
    return sentAt;
  }
  public void setSentAt(Timestamp sentAt) {
    this.sentAt = sentAt;
  }

}
