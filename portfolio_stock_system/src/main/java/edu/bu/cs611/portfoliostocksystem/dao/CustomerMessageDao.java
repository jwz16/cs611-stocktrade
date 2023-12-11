package edu.bu.cs611.portfoliostocksystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;
import edu.bu.cs611.portfoliostocksystem.model.CustomerMessage;

public class CustomerMessageDao implements Dao<CustomerMessage> {
  
  private static Logger logger = LogManager.getLogger();

  protected DatabaseClient dbClient;

  public CustomerMessageDao() {
    dbClient = new DatabaseClient();
  }

  public CustomerMessageDao(DatabaseClient dbClient) {
    this.dbClient = dbClient;
  }

  @Override
  public DatabaseClient getDatabaseClient() {
    return dbClient;
  }

  @Override
  public List<CustomerMessage> getAll() {
    List<CustomerMessage> customerMessages = new ArrayList<>();
    
    var sql = "SELECT * from customer_messages";
    dbClient.executeQuery(sql, rs -> {
      try {
        while(rs.next()) {
          customerMessages.add(resultSetToCustomerMessage(rs));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });

    return customerMessages;
  }

  @Override
  public boolean add(CustomerMessage cxMsg) {
    var sql = String.format(
      "INSERT INTO customer_messages VALUES(NULL, %d, '%s', datetime('now'))",
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
      sql = String.format("SELECT sent_at FROM customer_messages WHERE id='%s'", cxMsg.getId());
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
      "UPDATE customer_messages SET customer_id=%d, message='%s' WHERE id=%d",
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
  public boolean deleteById(Integer id) {
    var sql = String.format("DELETE FROM customer_messages WHERE id=%d", id);

    return dbClient.executeUpdate(sql) == 1;
  }

  @Override
  public CustomerMessage getById(Integer id) {
    List<CustomerMessage> customerMessages = new ArrayList<>();

    var sql = String.format("SELECT * FROM customer_messages WHERE id=%d", id);
    dbClient.executeQuery(sql, rs -> {
      try {
        if (!rs.next())
          return;
        customerMessages.add(resultSetToCustomerMessage(rs));
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });

    if (customerMessages.size() != 1) {
      logger.error("getById should return exactly one result but got " + customerMessages.size() + " results!");
      return null;
    }
    
    return customerMessages.get(0);
  }

  private CustomerMessage resultSetToCustomerMessage(ResultSet rs) throws SQLException {
    return new CustomerMessage(
      rs.getInt("id"),
      rs.getInt("customer_id"),
      rs.getString("message"),
      rs.getTimestamp("sent_at")
    );
  }

}
