package edu.bu.cs611.portfoliostocksystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;
import edu.bu.cs611.portfoliostocksystem.model.CustomerMessage;

public class CustomerMessageDao extends Dao<CustomerMessage> {
  
  private static Logger logger = LogManager.getLogger();
  private static String TABLE_NAME = "customer_messages";

  public CustomerMessageDao() {
    super();
  }

  public CustomerMessageDao(DatabaseClient databaseClient) {
    super(databaseClient);
  }
  
  @Override
  public boolean add(CustomerMessage cxMsg) {
    var sql = String.format(
      "INSERT INTO %s VALUES(NULL, %d, '%s', datetime('now'))",
      TABLE_NAME,
      cxMsg.getCustomerId(),
      cxMsg.getMessage()
    );

    var affectedRows = dbClient.executeUpdate(sql, stmt -> {
      try {
        var genKeys = stmt.executeQuery("SELECT last_insert_rowid()");
        if (genKeys.next()) {
          cxMsg.setId(genKeys.getInt(1));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });

    if (affectedRows == 1) {
      sql = String.format("SELECT sent_at FROM %s WHERE id='%s'", TABLE_NAME, cxMsg.getId());
      dbClient.executeQuery(sql, rs -> {
        try {
          if (rs.next()) {
            cxMsg.setSentAt(rs.getTimestamp("sent_at"));
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      });

      return true;
    }

    return false;
  }

  @Override
  public boolean update(CustomerMessage cxMsg) {
    var sql = String.format(
      "UPDATE %s SET customer_id=%d, message='%s' WHERE id=%d",
      TABLE_NAME,
      cxMsg.getCustomerId(),
      cxMsg.getMessage(),
      cxMsg.getId()
    );

    return dbClient.executeUpdate(sql) == 1;
  }

  @Override
  public boolean delete(CustomerMessage cxMsg) {
    return deleteById(cxMsg.getId());
  }

  @Override
  protected CustomerMessage fromResultSet(ResultSet rs) throws SQLException {
    return new CustomerMessage(
      rs.getInt("id"),
      rs.getInt("customer_id"),
      rs.getString("message"),
      rs.getTimestamp("sent_at")
    );
  }

  @Override
  public String tableName() {
    return TABLE_NAME;
  }

}
