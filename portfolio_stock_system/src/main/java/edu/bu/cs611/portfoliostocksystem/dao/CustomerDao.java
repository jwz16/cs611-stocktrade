package edu.bu.cs611.portfoliostocksystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;
import edu.bu.cs611.portfoliostocksystem.model.Customer;

public class CustomerDao extends Dao<Customer> {

  private static Logger logger = LogManager.getLogger();
  private static String TABLE_NAME = "customers";

  public CustomerDao() {
    super();
  }

  public CustomerDao(DatabaseClient databaseClient) {
    super(databaseClient);
  }

  @Override
  public boolean add(Customer cx) {
    var sql = String.format(
      "INSERT INTO %s VALUES(NULL, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
      TABLE_NAME,
      cx.getFirstName(),
      cx.getLastName(),
      cx.getUsername(),
      cx.getEmail(),
      cx.getDateOfBirth(),
      cx.getSsn(),
      cx.getStatus(),
      cx.getPasswordHash()
    );

    var affectedRows = dbClient.executeUpdate(sql, stmt -> {
      var genKeys = stmt.executeQuery("SELECT last_insert_rowid()");
      if (genKeys.next()) {
        cx.setId(genKeys.getInt(1));
      }
    });

    logger.info("Added a new customer: " + cx.getUsername());

    return affectedRows == 1;
  }

  @Override
  public boolean update(Customer cx) {
    var sql = String.format(
      "UPDATE %s SET first_name='%s', last_name='%s', username='%s', email='%s', date_of_birth='%s', ssn='%s', customer_status='%s', password_hash='%s' WHERE id=%d",
      TABLE_NAME,
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

    logger.info("Updated the customer " + cx.getUsername());

    return dbClient.executeUpdate(sql) == 1;
  }

  @Override
  protected Customer fromResultSet(ResultSet rs) throws SQLException {
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
  public String tableName() {
    return TABLE_NAME;
  }
  
}
