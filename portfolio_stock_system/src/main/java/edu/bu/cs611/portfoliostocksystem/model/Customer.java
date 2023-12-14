package edu.bu.cs611.portfoliostocksystem.model;

public class Customer extends User {

  public enum Status {
    PENDING,
    ACTIVE,
    DENIED
  }
  
  protected String ssn;
  protected Status status;
  
  public Customer() {}

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

}
