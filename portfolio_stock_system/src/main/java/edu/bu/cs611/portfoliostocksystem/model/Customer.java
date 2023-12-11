package edu.bu.cs611.portfoliostocksystem.model;

public class Customer {

  public enum Status {
    PENDING,
    ACTIVE,
    DENIED
  }
  
  private Integer id;
  private String firstName;
  private String lastName;
  private String username;
  private String email;
  private String dateOfBirth;
  private String ssn;
  private Status status;
  private String passwordHash;

  public Customer(String firstName, String lastName, String username, String email, String dateOfBirth, String ssn, String status, String passwordHash) {
    this(firstName, lastName, username, email, dateOfBirth, ssn, Status.valueOf(status), passwordHash);
  }

  public Customer(String firstName, String lastName, String username, String email, String dateOfBirth, String ssn, Status status, String passwordHash) {
    this(-1, firstName, lastName, username, email, dateOfBirth, ssn, status, passwordHash);
  }

  public Customer(Integer id, String firstName, String lastName, String username, String email, String dateOfBirth, String ssn, String status, String passwordHash) {
    this(id, firstName, lastName, username, email, dateOfBirth, ssn, Status.valueOf(status), passwordHash);
  }

  public Customer(Integer id, String firstName, String lastName, String username, String email, String dateOfBirth, String ssn, Status status, String passwordHash) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.email = email;
    this.dateOfBirth = dateOfBirth;
    this.ssn = ssn;
    this.status = status;
    this.passwordHash = passwordHash;
  }

  public Customer(Customer cx) {
    this(
      cx.getFirstName(),
      cx.getLastName(),
      cx.getUsername(),
      cx.getEmail(),
      cx.getDateOfBirth(),
      cx.getSsn(),
      cx.getStatus(),
      cx.getPasswordHash()
    );
  }

  /* Getters and Setters */
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getDateOfBirth() {
    return dateOfBirth;
  }
  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }
  public String getSsn() {
    return ssn;
  }
  public void setSsn(String ssn) {
    this.ssn = ssn;
  }
  public Status getStatus() {
    return status;
  }
  public void setStatus(Status status) {
    this.status = status;
  }
  public String getPasswordHash() {
    return passwordHash;
  }
  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

}
