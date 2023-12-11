package edu.bu.cs611.portfoliostocksystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;
import edu.bu.cs611.portfoliostocksystem.model.Security;

public class SecurityDao implements Dao<Security> {
  
  private static Logger logger = LogManager.getLogger();

  protected DatabaseClient dbClient;

  public SecurityDao() {
    dbClient = new DatabaseClient();
  }

  public SecurityDao(DatabaseClient dbClient) {
    this.dbClient = dbClient;
  }

  @Override
  public DatabaseClient getDatabaseClient() {
    return dbClient;
  }

  @Override
  public List<Security> getAll() {
    List<Security> securities = new ArrayList<>();

    var sql = "SELECT * from securities";
    dbClient.executeQuery(sql, rs -> {
      try {
        while(rs.next()) {
          securities.add(resultSetToSecurity(rs));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });

    return securities;
  }

  @Override
  public boolean add(Security sec) {
    var sql = String.format(
      "INSERT INTO securities VALUES(NULL, '%s', '%s', '%s', %f, %b)",
      sec.getSymbol(),
      sec.getSecurityName(),
      sec.getSecurityType(),
      sec.getPrice(),
      sec.getTradable()
    );

    if (dbClient.executeUpdate(sql) == 1) {
      sql = String.format("SELECT id FROM securities WHERE symbol='%s'", sec.getSymbol());
      dbClient.executeQuery(sql, rs -> {
        try {
          rs.next();
          sec.setId(rs.getInt("id"));
        } catch (SQLException e) {
          e.printStackTrace();
        }
      });

      return true;
    }

    return false;
  }

  @Override
  public boolean update(Security sec) {
    var sql = String.format(
      "UPDATE securities SET symbol='%s', security_name='%s', security_type='%s', price=%f, tradable=%b WHERE id=%d",
      sec.getSymbol(),
      sec.getSecurityName(),
      sec.getSecurityType(),
      sec.getPrice(),
      sec.getTradable(),
      sec.getId()
    );

    return dbClient.executeUpdate(sql) == 1;
  }

  @Override
  public boolean delete(Security sec) {
    return deleteById(sec.getId());
  }

  @Override
  public Security getById(Integer id) {
    List<Security> securities = new ArrayList<>();

    var sql = String.format("SELECT * FROM securities WHERE id=%d", id);
    dbClient.executeQuery(sql, rs -> {
      try {
        if (!rs.next())
          return;
        securities.add(resultSetToSecurity(rs));
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });

    if (securities.size() != 1) {
      logger.error("getById should return exactly one result but got " + securities.size() + " results!");
      return null;
    }

    return securities.get(0);
  }

  @Override
  public boolean deleteById(Integer id) {
    var sql = String.format("DELETE FROM securities WHERE id=%d", id);

    return dbClient.executeUpdate(sql) == 1;
  }

  private Security resultSetToSecurity(ResultSet rs) throws SQLException {
    return new Security(
      rs.getInt("id"),
      rs.getString("symbol"),
      rs.getString("security_name"),
      rs.getString("security_type"),
      rs.getDouble("price"),
      rs.getBoolean("tradable")
    );
  }

}
