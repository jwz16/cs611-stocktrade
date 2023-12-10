package edu.bu.cs611.portfoliostocksystem.dao;

import java.util.List;

import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;

public interface Dao<T> {
  
  public DatabaseClient getDatabaseClient();
  public List<T> getAll();
  public boolean add(T t);
  public boolean update(T t);
  public boolean delete(T t);
  public boolean deleteById(Integer id);
  public T getById(Integer id);

}
