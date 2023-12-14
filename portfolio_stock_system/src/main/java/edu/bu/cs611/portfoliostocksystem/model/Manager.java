package edu.bu.cs611.portfoliostocksystem.model;

public class Manager extends User  {

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

}
