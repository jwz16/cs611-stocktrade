package edu.bu.cs611.portfoliostocksystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;
import edu.bu.cs611.portfoliostocksystem.model.CustomerTradingAccount;

public class CustomerTradingAccountDao extends Dao<CustomerTradingAccount> {

  private static Logger logger = LogManager.getLogger();
  private static String TABLE_NAME = "customer_trading_accounts";

  public CustomerTradingAccountDao() {
    super();
  }

  public CustomerTradingAccountDao(DatabaseClient dbClient) {
    super(dbClient);
  }

  @Override
  public boolean add(CustomerTradingAccount cxTradeAcct) {
    var sql = String.format(
      "INSERT INTO %s VALUES(NULL, %d, '%s', %d, %f, %f, %f, %f, %f, '%s')",
      TABLE_NAME,
      cxTradeAcct.getCustomerId(),
      cxTradeAcct.getAccountName(),
      cxTradeAcct.getAccountNumber(),
      cxTradeAcct.getTotalValue(),
      cxTradeAcct.getCash(),
      cxTradeAcct.getInvestmentValue(),
      cxTradeAcct.getUnrealizedGains(),
      cxTradeAcct.getRealizedGains(),
      cxTradeAcct.getAccountStatus()
    );

    var affectedRows = dbClient.executeUpdate(sql, stmt -> {
      var genKeys = stmt.executeQuery("SELECT last_insert_rowid()");
      if (genKeys.next()) {
        cxTradeAcct.setId(genKeys.getInt(1));
      }
    });

    logger.info("Added a new customer trading account " + cxTradeAcct.getAccountName());

    return affectedRows == 1;
  }

  @Override
  public boolean update(CustomerTradingAccount cxTradeAcct) {
    var sql = String.format(
      "UPDATE %s SET customer_id=%d, account_name='%s', account_number=%d, total_value=%f, cash=%f, investment_value=%f, unrealized_gains=%f, realized_gains=%f, account_status='%s' WHERE id=%d",
      TABLE_NAME,
      cxTradeAcct.getCustomerId(),
      cxTradeAcct.getAccountName(),
      cxTradeAcct.getAccountNumber(),
      cxTradeAcct.getTotalValue(),
      cxTradeAcct.getCash(),
      cxTradeAcct.getInvestmentValue(),
      cxTradeAcct.getUnrealizedGains(),
      cxTradeAcct.getRealizedGains(),
      cxTradeAcct.getAccountStatus(),
      cxTradeAcct.getId()
    );

    logger.info("Updated the customer trading account " + cxTradeAcct.getAccountName());

    return dbClient.executeUpdate(sql) == 1;
  }

  @Override
  protected CustomerTradingAccount fromResultSet(ResultSet rs) throws SQLException {
    return new CustomerTradingAccount(
      rs.getInt("customer_id"),
      rs.getString("account_name"),
      rs.getLong("account_number"),
      rs.getDouble("total_value"),
      rs.getDouble("cash"),
      rs.getDouble("investment_value"),
      rs.getDouble("unrealized_gains"),
      rs.getDouble("realized_gains"),
      CustomerTradingAccount.Status.valueOf(rs.getString("account_status"))
    );
  }

  @Override
  public String tableName() {
    return TABLE_NAME;
  }
  
}
