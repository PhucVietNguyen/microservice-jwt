package com.example.common.core.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtil {

  public static boolean containsOnlyAlphabetAndSpace(String str) {
    return str.matches("[A-Za-z\\s]+");
  }

  public static boolean isEmptyString(String str) {
    return str == null || str.trim().equals("");
  }

  public static String removeAccent(String str) {
    String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(temp).replaceAll("");
  }
}
