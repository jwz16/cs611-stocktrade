package edu.bu.cs611.portfoliostocksystem;

import org.apache.commons.codec.digest.DigestUtils;

public class Util {
  public static String hashPassword(String passwd) {
    return DigestUtils.md5Hex(passwd);
  }
}
