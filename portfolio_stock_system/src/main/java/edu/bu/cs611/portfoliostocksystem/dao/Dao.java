package edu.bu.cs611.portfoliostocksystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;
import edu.bu.cs611.portfoliostocksystem.model.BaseModel;

public abstract class Dao<T extends BaseModel> {
  
  protected DatabaseClient dbClient;

  public Dao() {
    dbClient = new DatabaseClient();
  }

  public Dao(DatabaseClient dbClient) {
    this.dbClient = dbClient;
  }

  public abstract boolean add(T t);
  public abstract boolean update(T t);
  protected abstract T fromResultSet(ResultSet rs) throws SQLException;
  public abstract String tableName();

  public T getById(Integer id) {
    List<T> items = selectWhere("id=" + id);

    if (items == null || items.isEmpty())
      return null;
    
    return items.get(0);
  }

  public boolean delete(T t) {
    return deleteById(t.getId());
  }

  public boolean deleteById(Integer id) {
    var sql = String.format("DELETE FROM %s WHERE id=%d", tableName(), id);

    return dbClient.executeUpdate(sql) == 1;
  }

  public List<T> getAll() {
    List<T> items = new ArrayList<>();
    var sql = String.format("SELECT * FROM %s", tableName());

    dbClient.executeQuery(sql, rs -> {
      while (rs.next()) {
        items.add(fromResultSet(rs));
      }
    });

    return items; 
  }

  public List<T> selectWhere(String clause) {
    List<T> items = new ArrayList<>();
    var sql = String.format("SELECT * FROM %s WHERE %s", tableName(), clause);

    dbClient.executeQuery(sql, rs -> {
      while (rs.next()) {
        items.add(fromResultSet(rs));
      }
    });

    return items;
  }

}
