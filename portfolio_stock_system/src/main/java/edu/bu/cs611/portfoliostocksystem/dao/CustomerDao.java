package edu.bu.cs611.portfoliostocksystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;
import edu.bu.cs611.portfoliostocksystem.model.Customer;

public class CustomerDao implements Dao<Customer> {

  private static Logger logger = LogManager.getLogger();

  protected DatabaseClient dbClient;

  public CustomerDao() {
    dbClient = new DatabaseClient();
  }

  public CustomerDao(DatabaseClient dbClient) {
    this.dbClient = dbClient;
  }

  @Override
  public List<Customer> getAll() {
    List<Customer> customers = new ArrayList<>();
    
    var sql = "SELECT * from customers";
    dbClient.executeQuery(sql, rs -> {
      try {
        customers.add(resultSetToCustomer(rs));
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });

    return customers;
  }

  @Override
  public boolean add(Customer cx) {
    var sql = String.format(
      "INSERT INTO customers VALUES(NULL, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
      cx.getFirstName(),
      cx.getLastName(),
      cx.getUsername(),
      cx.getEmail(),
      cx.getDateOfBirth(),
      cx.getSsn(),
      cx.getStatus(),
      cx.getPasswordHash()
    );

    if (dbClient.executeUpdate(sql) == 1) {
      sql = String.format("SELECT id FROM customers WHERE email='%s'", cx.getEmail());
      dbClient.executeQuery(sql, rs -> {
        try {
          rs.next();
          cx.setId(rs.getInt("id"));
        } catch (SQLException e) {
          e.printStackTrace();
        }
      });

      return true;
    }

    return false;
  }

  @Override
  public boolean update(Customer cx) {
    var sql = String.format(
      "UPDATE customers SET first_name='%s', last_name='%s', username='%s', email='%s', date_of_birth='%s', ssn='%s', customer_status='%s', password_hash='%s' WHERE id=%d",
      cx.getFirstName(),
      cx.getLastName(),
      cx.getUsername(),
      cx.getEmail(),
      cx.getDateOfBirth(),
      cx.getSsn(),
      cx.getStatus(),
      cx.getPasswordHash(),
      cx.getId()
    );

    return dbClient.executeUpdate(sql) == 1;
  }

  @Override
  public boolean delete(Customer cx) {
    var sql = String.format("DELETE FROM customers WHERE id=%d", cx.getId());

    return dbClient.executeUpdate(sql) == 1;
  }

  @Override
  public Customer getById(Integer id) {
    List<Customer> customers = new ArrayList<>();

    var sql = String.format("SELECT * FROM customers WHERE id=%d", id);
    dbClient.executeQuery(sql, rs -> {
      try {
        rs.next();
        customers.add(resultSetToCustomer(rs));
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });

    if (customers.size() != 1) {
      logger.error("getById should return exactly one result but got " + customers.size() + " results!");
      return null;
    }
    
    return customers.get(0);
  }

  private Customer resultSetToCustomer(ResultSet rs) throws SQLException {
    return new Customer(
      rs.getInt("id"),
      rs.getString("first_name"),
      rs.getString("last_name"),
      rs.getString("username"),
      rs.getString("email"),
      rs.getString("date_of_birth"),
      rs.getString("ssn"),
      rs.getString("customer_status"),
      rs.getString("password_hash")
    );
  }

  @Override
  public DatabaseClient getDatabaseClient() {
    return dbClient;
  }
  
}
