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

    var affectedRows = dbClient.executeUpdate(sql, stmt -> {
      var genKeys = stmt.executeQuery("SELECT last_insert_rowid()");
      if (genKeys.next()) {
        mgr.setId(genKeys.getInt(1));
      }
    });

    logger.info("Added a new manager: " + mgr.getUsername());

    return affectedRows == 1;
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

    logger.info("Updated the manager " + mgr.getUsername());

    return dbClient.executeUpdate(sql) == 1;
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
