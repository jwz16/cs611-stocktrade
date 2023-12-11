package edu.bu.cs611.portfoliostocksystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;
import edu.bu.cs611.portfoliostocksystem.model.Manager;

public class ManagerDao extends Dao<Manager> {
  private static Logger logger = LogManager.getLogger();
  private static String TABLE_NAME = "managers";

  public ManagerDao() {
    super();
  }

  public ManagerDao(DatabaseClient dbClient) {
    super(dbClient);
  }

  @Override
  public boolean add(Manager mgr) {
    var sql = String.format(
      "INSERT INTO %s VALUES(NULL, '%s', '%s', '%s', '%s', '%s', '%s')",
      TABLE_NAME,
      mgr.getFirstName(),
      mgr.getLastName(),
      mgr.getUsername(),
      mgr.getEmail(),
      mgr.getDateOfBirth(),
      mgr.getPasswordHash()
    );

    if (dbClient.executeUpdate(sql) == 1) {
      sql = String.format("SELECT id FROM %s WHERE email='%s'", TABLE_NAME, mgr.getEmail());
      dbClient.executeQuery(sql, rs -> {
        try {
          rs.next();
          mgr.setId(rs.getInt("id"));
        } catch (SQLException e) {
          e.printStackTrace();
        }
      });

      return true;
    }

    return false;
  }

  @Override
  public boolean update(Manager mgr) {
    var sql = String.format(
      "UPDATE %s SET first_name='%s', last_name='%s', username='%s', email='%s', date_of_birth='%s', password_hash='%s' WHERE id=%d",
      TABLE_NAME,
      mgr.getFirstName(),
      mgr.getLastName(),
      mgr.getUsername(),
      mgr.getEmail(),
      mgr.getDateOfBirth(),
      mgr.getPasswordHash(),
      mgr.getId()
    );

    return dbClient.executeUpdate(sql) == 1;
  }

  @Override
  public boolean delete(Manager mgr) {
    return deleteById(mgr.getId());
  }

  protected Manager fromResultSet(ResultSet rs) throws SQLException {
    return new Manager(
      rs.getInt("id"),
      rs.getString("first_name"),
      rs.getString("last_name"),
      rs.getString("username"),
      rs.getString("email"),
      rs.getString("date_of_birth"),
      rs.getString("password_hash")
    );
  }

  @Override
  public String tableName() {
    return TABLE_NAME;
  }

}
