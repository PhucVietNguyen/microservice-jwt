package com.example.common.core.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtil {

  private static ZoneId defaultZoneId = ZoneId.of("UTC");

  public static String ddmmyyyyFormatString = "dd/MM/yyyy";

  public static String convertInstantToString(Instant instant, String pattern, ZoneId zoneId) {
    if (instant == null || pattern == null || zoneId == null) return null;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern).withZone(zoneId);
    return dateTimeFormatter.format(instant);
  }

  public static String convertInstantToddmmyyyyString(Instant instant) {
    return convertInstantToString(instant, ddmmyyyyFormatString, defaultZoneId);
  }

  public static void main(String[] args) {
    System.out.println(convertInstantToddmmyyyyString(Instant.now()));
    System.out.println(
        convertInstantToString(Instant.now(), "dd/MM/yyyy", ZoneId.of("Asia/Hong_Kong")));
  }
}
