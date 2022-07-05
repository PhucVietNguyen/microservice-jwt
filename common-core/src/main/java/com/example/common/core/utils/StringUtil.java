package com.example.common.core.utils;

public class StringUtil {

  public static boolean containsOnlyAlphabetAndSpace(String str) {
    return str.matches("[A-Za-z\\s]+");
  }

  public static boolean isEmptyString(String str) {
    return str == null || str.trim().equals("");
  }
}
