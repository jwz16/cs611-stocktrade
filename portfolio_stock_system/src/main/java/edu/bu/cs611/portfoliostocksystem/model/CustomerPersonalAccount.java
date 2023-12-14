package edu.bu.cs611.portfoliostocksystem.model;

public class CustomerPersonalAccount extends AbstractModel {

  private Integer id;
  private Integer customerId;
  private String accountName;
  private Long accountNumber;
  private Long routingNumber;

  public CustomerPersonalAccount(Integer customerId, String accountName, Long accountNumber, Long routingNumber) {
    this(-1, customerId, accountName, accountNumber, routingNumber);
  }

  public CustomerPersonalAccount(Integer id, Integer customerId, String accountName, Long accountNumber, Long routingNumber) {
    this.id = id;
    this.accountName = accountName;
    this.accountNumber = accountNumber;
    this.routingNumber = routingNumber;
  }

  /* Getters and Setters */
  @Override
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
  public String getAccountName() {
    return accountName;
  }
  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }
  public Long getAccountNumber() {
    return accountNumber;
  }
  public void setAccountNumber(Long accountNumber) {
    this.accountNumber = accountNumber;
  }
  public Long getRoutingNumber() {
    return routingNumber;
  }
  public void setRoutingNumber(Long routingNumber) {
    this.routingNumber = routingNumber;
  }
  
}
