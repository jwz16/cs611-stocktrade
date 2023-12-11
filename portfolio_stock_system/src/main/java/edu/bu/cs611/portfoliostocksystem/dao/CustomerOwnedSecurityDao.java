package edu.bu.cs611.portfoliostocksystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;
import edu.bu.cs611.portfoliostocksystem.model.CustomerOwnedSecurity;

public class CustomerOwnedSecurityDao extends Dao<CustomerOwnedSecurity> {

  private static Logger logger = LogManager.getLogger();
  private static String TABLE_NAME = "customer_owned_securities";

  public CustomerOwnedSecurityDao() {
    super();
  }

  public CustomerOwnedSecurityDao(DatabaseClient dbClient) {
    super(dbClient);
  }

  @Override
  public boolean add(CustomerOwnedSecurity cxOwnedSec) {
    var sql = String.format(
      "INSERT INTO %s VALUES(NULL, %d, %d, %d, %f, datetime('now'))",
      TABLE_NAME,
      cxOwnedSec.getSecurityId(),
      cxOwnedSec.getCxTradingAcctId(),
      cxOwnedSec.getQuantity(),
      cxOwnedSec.getBoughtPrice(),
      cxOwnedSec.getBoughtAt()
    );

    var affectedRows = dbClient.executeUpdate(sql, stmt -> {
      var genKeys = stmt.executeQuery("SELECT last_insert_rowid()");
      if (genKeys.next()) {
        cxOwnedSec.setId(genKeys.getInt(1));
      }
    });

    if (affectedRows == 1) {
      sql = String.format("SELECT bought_at FROM %s WHERE id='%s'", TABLE_NAME, cxOwnedSec.getId());
      dbClient.executeQuery(sql, rs -> {
        if (rs.next()) {
          cxOwnedSec.setBoughtAt(rs.getTimestamp("bought_at"));
        }
      });

      logger.info("Added a new customer owned security, id: " + cxOwnedSec.getId());

      return true;
    }

    return false;
  }

  @Override
  public boolean update(CustomerOwnedSecurity cxOwnedSec) {
    var sql = String.format(
      "UPDATE %s SET security_id=%d, customer_trading_account_id=%d, quantity=%d, bought_price=%f WHERE id=%d",
      TABLE_NAME,
      cxOwnedSec.getSecurityId(),
      cxOwnedSec.getCxTradingAcctId(),
      cxOwnedSec.getQuantity(),
      cxOwnedSec.getBoughtPrice(),
      cxOwnedSec.getId()
    );

    logger.info("Updated the customer owned security, id: " + cxOwnedSec.getId());

    return dbClient.executeUpdate(sql) == 1;
  }

  @Override
  protected CustomerOwnedSecurity fromResultSet(ResultSet rs) throws SQLException {
    return new CustomerOwnedSecurity(
      rs.getInt("id"),
      rs.getInt("security_id"),
      rs.getInt("customer_trading_account_id"),
      rs.getLong("quantity"),
      rs.getDouble("bought_price"),
      rs.getTimestamp("bought_at")
    );
  }

  @Override
  public String tableName() {
    return TABLE_NAME;
  }
  
}
