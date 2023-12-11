package edu.bu.cs611.portfoliostocksystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;
import edu.bu.cs611.portfoliostocksystem.model.CustomerPersonalAccount;

public class CustomerPersonalAccountDao extends Dao<CustomerPersonalAccount> {

  private static Logger logger = LogManager.getLogger();
  private static String TABLE_NAME = "customer_personal_accounts";

  public CustomerPersonalAccountDao() {
    super();
  }

  public CustomerPersonalAccountDao(DatabaseClient dbClient) {
    super(dbClient);
  }

  @Override
  public boolean add(CustomerPersonalAccount cxPersAcct) {
    var sql = String.format(
      "INSERT INTO %s VALUES(NULL, '%s', %d, %d)",
      TABLE_NAME,
      cxPersAcct.getAccountName(),
      cxPersAcct.getAccountNumber(),
      cxPersAcct.getRoutingNumber()
    );

    var affectedRows = dbClient.executeUpdate(sql, stmt -> {
      var genKeys = stmt.executeQuery("SELECT last_insert_rowid()");
      if (genKeys.next()) {
        cxPersAcct.setId(genKeys.getInt(1));
      }
    });

    logger.info("Added a new customer personal account " + cxPersAcct.getAccountName());

    return affectedRows == 1;
  }

  @Override
  public boolean update(CustomerPersonalAccount cxPersAcct) {
    var sql = String.format(
      "UPDATE %s SET account_name='%s', account_number=%d, routing_number=%d WHERE id=%d",
      TABLE_NAME,
      cxPersAcct.getAccountName(),
      cxPersAcct.getAccountNumber(),
      cxPersAcct.getRoutingNumber(),
      cxPersAcct.getId()
    );

    logger.info("Updated the customer personal account " + cxPersAcct.getAccountName());

    return dbClient.executeUpdate(sql) == 1;
  }

  @Override
  protected CustomerPersonalAccount fromResultSet(ResultSet rs) throws SQLException {
    return new CustomerPersonalAccount(
      rs.getString("account_name"),
      rs.getLong("account_number"),
      rs.getLong("routing_number")
    );
  }

  @Override
  public String tableName() {
    return TABLE_NAME;
  }
  
}
