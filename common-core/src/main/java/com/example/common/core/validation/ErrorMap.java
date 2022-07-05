package com.example.common.core.validation;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public class ErrorMap {
  private final Map<String, String> errors = new HashMap<>();

  public boolean hasError() {
    return errors.size() > 0;
  }

  public void put(String field, String description) {
    errors.put(field, description);
  }

  public void putIfAbsent(String field, String description) {
    errors.putIfAbsent(field, description);
  }

  public String get(String field) {
    return errors.get(field);
  }

  public void remove(String field) {
    errors.remove(field);
  }

  public int size() {
    return errors.size();
  }

  public Map<String, String> toMap() {
    return ImmutableMap.copyOf(errors);
  }
}
