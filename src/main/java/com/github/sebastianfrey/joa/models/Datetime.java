package com.github.sebastianfrey.joa.models;

import java.time.Instant;
import java.time.format.DateTimeParseException;

public class Datetime {

  public final static String OPEN_INTERVAL = "..";

  private String lower;
  private String upper;

  public Datetime(String value) {
    this(value, value);
  }

  public Datetime(String lower, String upper) {
    this.lower = lower;
    this.upper = upper;
  }

  public String getLower() {
    return lower;
  }

  public void setLower(String lower) {
    this.lower = lower;
  }

  public String getUpper() {
    return upper;
  }

  public void setUpper(String upper) {
    this.upper = upper;
  }

  public Boolean validate() {
    if (lower == null) {
      return false;
    }

    if (upper == null) {
      return false;
    }

    if (!isOpenInterval(lower)) {
      try {
        Instant.parse(lower);
      } catch (DateTimeParseException ex) {
        return false;
      }
    }

    if (!isOpenInterval(upper)) {
      try {
        Instant.parse(upper);
      } catch (DateTimeParseException ex) {
        return false;
      }
    }

    if (isOpenInterval(lower) && isOpenInterval(upper)) {
      return false;
    }

    return true;
  }

  public Boolean isOpenInterval(String value) {
    return OPEN_INTERVAL.equals(value);
  }

  public Boolean isSingleValue() {
    return validate() && lower == upper;
  }

  public Boolean hasClosedInterval() {
    return validate() && !isOpenInterval(lower) && !isOpenInterval(upper);
  }

  public Boolean hasOpenInterval() {
    return validate() && isOpenInterval(lower) || isOpenInterval(upper);
  }
}
