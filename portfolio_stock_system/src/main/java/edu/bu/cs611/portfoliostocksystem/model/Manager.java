package edu.bu.cs611.portfoliostocksystem.model;

public class Manager implements BaseModel  {
  
  private Integer id;
  private String firstName;
  private String lastName;
  private String username;
  private String email;
  private String dateOfBirth;
  private String passwordHash;

  public Manager(String firstName, String lastName, String username, String email, String dateOfBirth, String passwordHash) {
    this(-1, firstName, lastName, username, email, dateOfBirth, passwordHash);
  }

  public Manager(Integer id, String firstName, String lastName, String username, String email, String dateOfBirth, String passwordHash) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.email = email;
    this.dateOfBirth = dateOfBirth;
    this.passwordHash = passwordHash;
  }

  public Manager(Manager mgr) {
    this(
      mgr.getFirstName(),
      mgr.getLastName(),
      mgr.getUsername(),
      mgr.getEmail(),
      mgr.getDateOfBirth(),
      mgr.getPasswordHash()
    );
  }

  /* Getters and Setters */
  @Override
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
  public String getPasswordHash() {
    return passwordHash;
  }
  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

}
