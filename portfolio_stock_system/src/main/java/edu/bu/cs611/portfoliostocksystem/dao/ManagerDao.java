package edu.bu.cs611.portfoliostocksystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;
import edu.bu.cs611.portfoliostocksystem.model.Manager;

public class ManagerDao implements Dao<Manager> {
  private static Logger logger = LogManager.getLogger();

  protected DatabaseClient dbClient;

  public ManagerDao() {
    dbClient = new DatabaseClient();
  }

  public ManagerDao(DatabaseClient dbClient) {
    this.dbClient = dbClient;
  }

  @Override
  public List<Manager> getAll() {
    List<Manager> managers = new ArrayList<>();

    var sql = "SELECT * from managers";
    dbClient.executeQuery(sql, rs -> {
      try {
        while(rs.next()) {
          managers.add(resultSetToManager(rs));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });

    return managers;
  }

  @Override
  public boolean add(Manager mgr) {
    var sql = String.format(
      "INSERT INTO managers VALUES(NULL, '%s', '%s', '%s', '%s', '%s', '%s')",
      mgr.getFirstName(),
      mgr.getLastName(),
      mgr.getUsername(),
      mgr.getEmail(),
      mgr.getDateOfBirth(),
      mgr.getPasswordHash()
    );

    if (dbClient.executeUpdate(sql) == 1) {
      sql = String.format("SELECT id FROM managers WHERE email='%s'", mgr.getEmail());
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
      "UPDATE managers SET first_name='%s', last_name='%s', username='%s', email='%s', date_of_birth='%s', password_hash='%s' WHERE id=%d",
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

  @Override
  public boolean deleteById(Integer id) {
    var sql = String.format("DELETE FROM managers WHERE id=%d", id);

    return dbClient.executeUpdate(sql) == 1;
  }

  @Override
  public Manager getById(Integer id) {
    List<Manager> managers = new ArrayList<>();

    var sql = String.format("SELECT * FROM managers WHERE id=%d", id);
    dbClient.executeQuery(sql, rs -> {
      try {
        if (!rs.next())
          return;
        managers.add(resultSetToManager(rs));
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });

    if (managers.size() != 1) {
      logger.error("getById should return exactly one result but got " + managers.size() + " results!");
      return null;
    }

    return managers.get(0);
  }

  private Manager resultSetToManager(ResultSet rs) throws SQLException {
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
  public DatabaseClient getDatabaseClient() {
    return dbClient;
  }
}
