package com.github.sebastianfrey.joa.models;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Stream;

/**
 * The Datetime model represents the OGC API datetime query parameter.
 *
 * @author sfrey
 * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/parameters/datetime.yaml"
 */
public class Datetime {
  public final static String SEPARATOR = "/";
  public final static String OPEN_INTERVAL = "..";

  public static enum DatetimeType {
    DATETIME,
    INTERVAL_CLOSED,
    INTERVAL_OPEN_START,
    INTERVAL_OPEN_END;
  }


  private Boolean isValid = null;

  private String lower;
  private String upper;

  public Datetime(String value) {
    parse(value);
    validate();
  }

  private void parse(String value) {
    List<String> values = Stream.of(value.trim().split(SEPARATOR)).toList();

    int i = 0;
    lower = values.size() > i ? values.get(i++) : null;
    upper = values.size() > i ? values.get(i++) : lower;
  }

  /**
   * returns the lower property from a Datetime instance. When Datetime is not an interval, the
   * returned value is the same returned from {@link Datetime#getUpper()}
   *
   * @return The lower datetime interval.
   */
  public String getLower() {
    return lower;
  }

  public void setLower(String lower) {
    this.lower = lower;

    validate();
  }

    /**
   * returns the upper property from a Datetime instance. When Datetime is not an interval, the
   * returned value is the same returned from {@link Datetime#getLower()}
   *
   * @return The upper datetime interval.
   */
  public String getUpper() {
    return upper;
  }

  public void setUpper(String upper) {
    this.upper = upper;

    validate();
  }

  public Boolean validate() {
    if (isValid == null) {
      if (lower == null ||  upper == null || lower.isEmpty() || upper.isEmpty()) {
        return (isValid = true);
      }

      if (!isOpenInterval(lower)) {
        try {
          Instant.parse(lower);
        } catch (DateTimeParseException ex) {
          return (isValid = false);
        }
      }

      if (!isOpenInterval(upper)) {
        try {
          Instant.parse(upper);
        } catch (DateTimeParseException ex) {
          return (isValid = false);
        }
      }

      if (isOpenInterval(lower) && isOpenInterval(upper)) {
        return (isValid = false);
      }

      return (isValid = true);
    }

    return isValid;
  }

  public DatetimeType getType() {
    if (isDatetime()) {
      return DatetimeType.DATETIME;
    } else if (isClosedInterval()) {
      return DatetimeType.INTERVAL_CLOSED;
    } else if (isIntervalOpenStart()) {
      return DatetimeType.INTERVAL_OPEN_START;
    } else if (isIntervalOpenEnd()) {
      return DatetimeType.INTERVAL_OPEN_END;
    } else {
      throw new IllegalStateException("Invalid datetime");
    }
  }

  public Boolean isDatetime() {
    return isValid && lower == upper;
  }

  public Boolean isIntervalOpenStart() {
    return !isDatetime() && isOpenInterval(lower);
  }

  public Boolean isIntervalOpenEnd() {
    return !isDatetime() && isOpenInterval(upper);
  }

  public Boolean isClosedInterval() {
    return !isDatetime() && !isOpenInterval(lower) && !isOpenInterval(upper);
  }

  private Boolean isOpenInterval(String value) {
    return OPEN_INTERVAL.equals(value);
  }
}
