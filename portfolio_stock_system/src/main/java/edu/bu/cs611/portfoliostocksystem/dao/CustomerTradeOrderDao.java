package edu.bu.cs611.portfoliostocksystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;
import edu.bu.cs611.portfoliostocksystem.model.CustomerTradeOrder;

public class CustomerTradeOrderDao extends Dao<CustomerTradeOrder> {

  private static Logger logger = LogManager.getLogger();
  private static String TABLE_NAME = "customer_trade_orders";

  public CustomerTradeOrderDao() {
    super();
  }

  public CustomerTradeOrderDao(DatabaseClient dbClient) {
    super(dbClient);
  }

  @Override
  public boolean add(CustomerTradeOrder tradeOrder) {
    var sql = String.format(
      "INSERT INTO %s VALUES(NULL, %d, %d, %d, %f, '%s', '%s', datetime('now'), NULL)",
      TABLE_NAME,
      tradeOrder.getSecurityId(),
      tradeOrder.getCxTradingAcctId(),
      tradeOrder.getTradeQuantity(),
      tradeOrder.getTradePrice(),
      tradeOrder.getTradeType(),
      tradeOrder.getTradeStatus()
    );

    var affectedRows = dbClient.executeUpdate(sql, stmt -> {
      var genKeys = stmt.executeQuery("SELECT last_insert_rowid()");
      if (genKeys.next()) {
        tradeOrder.setId(genKeys.getInt(1));
      }
    });

    if (affectedRows == 1) {
      sql = String.format("SELECT bought_at FROM %s WHERE id='%s'", TABLE_NAME, tradeOrder.getId());
      dbClient.executeQuery(sql, rs -> {
        if (rs.next()) {
          tradeOrder.setTradeStartAt(rs.getTimestamp("trade_start_at"));
        }
      });

      logger.info("Added a new customer trade order, id: " + tradeOrder.getId());

      return true;
    }

    return false;
  }

  @Override
  public boolean update(CustomerTradeOrder tradeOrder) {
    var sql = String.format(
      "UPDATE %s SET security_id=%d, customer_trading_account_id=%d, trade_quantity=%d, trade_price=%f, trade_type='%s', trade_status='%s', trade_finish_at='%s' WHERE id=%d",
      TABLE_NAME,
      tradeOrder.getSecurityId(),
      tradeOrder.getCxTradingAcctId(),
      tradeOrder.getTradeQuantity(),
      tradeOrder.getTradePrice(),
      tradeOrder.getTradeType(),
      tradeOrder.getTradeStatus(),
      tradeOrder.getTradeFinishAt(),
      tradeOrder.getId()
    );

    logger.info("Updated the customer trade order, id: " + tradeOrder.getId());

    return dbClient.executeUpdate(sql) == 1;
  }

  @Override
  protected CustomerTradeOrder fromResultSet(ResultSet rs) throws SQLException {
    return new CustomerTradeOrder(
      rs.getInt("security_id"),
      rs.getInt("customer_trading_account_id"),
      rs.getLong("trade_quantity"),
      rs.getDouble("trade_price"),
      CustomerTradeOrder.Type.valueOf(rs.getString("trade_type")),
      CustomerTradeOrder.Status.valueOf(rs.getString("trade_status")),
      rs.getTimestamp("trade_start_at"),
      rs.getTimestamp("trade_finish_at")
    );
  }

  @Override
  public String tableName() {
    return TABLE_NAME;
  }
  
}
