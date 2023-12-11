package edu.bu.cs611.portfoliostocksystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.bu.cs611.portfoliostocksystem.Util;
import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;
import edu.bu.cs611.portfoliostocksystem.database.DatabaseClientTest;
import edu.bu.cs611.portfoliostocksystem.model.Manager;

public class ManagerDaoTest {

  private static ManagerDao mgrDao;
  private static Manager testManager;

  @BeforeAll
  static void setup() {
    DatabaseClientTest.createTestDatabase();

    mgrDao = new ManagerDao(new DatabaseClient(true));

    testManager = new Manager(
      "John", "Anderson",
      "john001",
      "john001@gmail.com",
      "01/01/1990",
      Util.hashPassword("123456789")
    );
  }

  @AfterAll
  static void teardown() {
    DatabaseClientTest.deleteTestDatabase();
  }

  @Test
  void testAdd() {
    assertTrue(mgrDao.add(testManager));

    assertNotNull(mgrDao.getById(testManager.getId()));
    assertTrue(mgrDao.delete(testManager));
  }

  @Test
  void testDelete() {
    mgrDao.add(testManager);

    assertTrue(mgrDao.delete(testManager));
    assertNull(mgrDao.getById(testManager.getId()));
  }

  @Test
  void testGetAll() {
    var testManager1 = new Manager(testManager);
    testManager1.setUsername("john001");
    testManager1.setEmail("john001@gmail.com");
    mgrDao.add(testManager1);

    var testManager2 = new Manager(testManager);
    testManager2.setUsername("john002");
    testManager2.setEmail("john002@gmail.com");
    mgrDao.add(testManager2);

    assertEquals(2, mgrDao.getAll().size());

    assertTrue(mgrDao.delete(testManager1));
    assertTrue(mgrDao.delete(testManager2));
  }

  @Test
  void testGetById() {
    mgrDao.add(testManager);
    assertNotNull(mgrDao.getById(testManager.getId()));
    mgrDao.delete(testManager);
  }

  @Test
  void testUpdate() {
    testManager.setFirstName("John");
    mgrDao.add(testManager);

    testManager.setFirstName("Michael");
    mgrDao.update(testManager);

    assertEquals("Michael", mgrDao.getById(testManager.getId()).getFirstName());
    mgrDao.delete(testManager);
  }

  @Test
  void testSelectWhere() {
    var testManager1 = new Manager(testManager);
    testManager1.setUsername("john001");
    testManager1.setEmail("john001@gmail.com");
    mgrDao.add(testManager1);

    var testManager2 = new Manager(testManager);
    testManager2.setUsername("john002");
    testManager2.setEmail("john002@gmail.com");
    mgrDao.add(testManager2);

    assertEquals(1, mgrDao.selectWhere("username='john002'").size());

    mgrDao.delete(testManager1);
    mgrDao.delete(testManager2);
  }

}
