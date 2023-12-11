package edu.bu.cs611.portfoliostocksystem.model;

public class CustomerTradingAccount implements BaseModel {
  
  public enum Status {
    PENDING,
    ACTIVE,
    DENIED
  }

  private Integer id;
  private Integer customerId;
  private String accountName;
  private Long accountNumber;
  private Double totalValue;
  private Double cash;
  private Double investmentValue;
  private Double unrealizedGains;
  private Double realizedGains;
  private Status accountStatus;

  public CustomerTradingAccount(Integer customerId, String accountName, Long accountNumber, Double totalValue, Double cash,
      Double investmentValue, Double unrealizedGains, Double realizedGains, Status accountStatus) {
    this(-1, customerId, accountName, accountNumber, totalValue, cash, investmentValue, unrealizedGains, realizedGains, accountStatus);
  }

  public CustomerTradingAccount(Integer id, Integer customerId, String accountName, Long accountNumber, Double totalValue, Double cash,
      Double investmentValue, Double unrealizedGains, Double realizedGains, Status accountStatus) {
    this.id = id;
    this.customerId = customerId;
    this.accountName = accountName;
    this.accountNumber = accountNumber;
    this.totalValue = totalValue;
    this.cash = cash;
    this.investmentValue = investmentValue;
    this.unrealizedGains = unrealizedGains;
    this.realizedGains = realizedGains;
    this.accountStatus = accountStatus;
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
  public Double getTotalValue() {
    return totalValue;
  }
  public void setTotalValue(Double totalValue) {
    this.totalValue = totalValue;
  }
  public Double getCash() {
    return cash;
  }
  public void setCash(Double cash) {
    this.cash = cash;
  }
  public Double getInvestmentValue() {
    return investmentValue;
  }
  public void setInvestmentValue(Double investmentValue) {
    this.investmentValue = investmentValue;
  }
  public Double getUnrealizedGains() {
    return unrealizedGains;
  }
  public void setUnrealizedGains(Double unrealizedGains) {
    this.unrealizedGains = unrealizedGains;
  }
  public Double getRealizedGains() {
    return realizedGains;
  }
  public void setRealizedGains(Double realizedGains) {
    this.realizedGains = realizedGains;
  }
  public Status getAccountStatus() {
    return accountStatus;
  }
  public void setAccountStatus(Status accountStatus) {
    this.accountStatus = accountStatus;
  }

}
