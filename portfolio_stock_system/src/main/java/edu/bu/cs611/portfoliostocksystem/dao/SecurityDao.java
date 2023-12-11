package edu.bu.cs611.portfoliostocksystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;
import edu.bu.cs611.portfoliostocksystem.model.Security;

public class SecurityDao extends Dao<Security> {
  
  private static Logger logger = LogManager.getLogger();
  private static String TABLE_NAME = "securities";

  public SecurityDao() {
    super();
  }

  public SecurityDao(DatabaseClient dbClient) {
    super(dbClient);
  }

  @Override
  public boolean add(Security sec) {
    var sql = String.format(
      "INSERT INTO %s VALUES(NULL, '%s', '%s', '%s', %f, %b)",
      TABLE_NAME,
      sec.getSymbol(),
      sec.getSecurityName(),
      sec.getSecurityType(),
      sec.getPrice(),
      sec.getTradable()
    );

    if (dbClient.executeUpdate(sql) == 1) {
      sql = String.format("SELECT id FROM %s WHERE symbol='%s'", TABLE_NAME, sec.getSymbol());
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
      "UPDATE %s SET symbol='%s', security_name='%s', security_type='%s', price=%f, tradable=%b WHERE id=%d",
      TABLE_NAME,
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

  protected Security fromResultSet(ResultSet rs) throws SQLException {
    return new Security(
      rs.getInt("id"),
      rs.getString("symbol"),
      rs.getString("security_name"),
      rs.getString("security_type"),
      rs.getDouble("price"),
      rs.getBoolean("tradable")
    );
  }

  @Override
  public String tableName() {
    return TABLE_NAME;
  }

}
