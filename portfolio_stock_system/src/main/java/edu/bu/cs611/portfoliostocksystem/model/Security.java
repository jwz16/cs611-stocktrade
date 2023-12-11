package edu.bu.cs611.portfoliostocksystem.model;

public class Security implements BaseModel{
  
  public enum Type { STOCK, OPTION }
  
  private Integer id;
  private String symbol;
  private String securityName;
  private Type securityType;
  private Double price;
  private Boolean tradable;

  public Security(String symbol, String securityName, String securityType, Double price, Boolean tradable) {
    this(-1, symbol, securityName, Type.valueOf(securityType), price, tradable);
  }

  public Security(String symbol, String securityName, Type securityType, Double price, Boolean tradable) {
    this(-1, symbol, securityName, securityType, price, tradable);
  }

  public Security(Integer id, String symbol, String securityName, String securityType, Double price, Boolean tradable) {
    this(id, symbol, securityName, Type.valueOf(securityType), price, tradable);
  }

  public Security(Integer id, String symbol, String securityName, Type securityType, Double price, Boolean tradable) {
    this.id = id;
    this.symbol = symbol;
    this.securityName = securityName;
    this.securityType = securityType;
    this.price = price;
    this.tradable = tradable;
  }

  public Security(Security sec) {
    this(
      -1,
      sec.getSymbol(),
      sec.getSecurityName(),
      sec.getSecurityType(),
      sec.getPrice(),
      sec.getTradable()
    );
  }

  /* Getter and Setters */
  @Override
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getSymbol() {
    return symbol;
  }
  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }
  public String getSecurityName() {
    return securityName;
  }
  public void setSecurityName(String security_name) {
    this.securityName = security_name;
  }
  public Type getSecurityType() {
    return securityType;
  }
  public void setSecurityType(Type security_type) {
    this.securityType = security_type;
  }
  public Double getPrice() {
    return price;
  }
  public void setPrice(Double price) {
    this.price = price;
  }
  public Boolean getTradable() {
    return tradable;
  }
  public void setTradable(Boolean tradable) {
    this.tradable = tradable;
  }

}
