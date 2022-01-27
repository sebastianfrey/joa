package com.github.sebastianfrey.joa.resources.params;

import java.util.List;
import java.util.stream.Stream;
import com.github.sebastianfrey.joa.models.Datetime;

/**
 * Simple Datetime converter leveraging JAX-RS String constructor instantiation.
 *
 * @sfrey
 */
public class DatetimeParam {

  public final static String SEPARATOR = "/";

  List<String> values;

  public DatetimeParam(String value) {
    values = parse(value);
  }

  public List<String> parse(String input) {
    if (input == null) {
      return null;
    }

    return Stream.of(input.trim().split(SEPARATOR)).toList();
  }

  public Boolean validate() {
    if (values == null || values.isEmpty()) {
      return true;
    }

    return get().validate();
  }

  public Datetime get() {
    int i = 0;
    String lower = values.size() > i ? values.get(i++) : null;
    String upper = values.size() > i ? values.get(i++) : lower;

    return new Datetime(lower, upper);

  }
}
