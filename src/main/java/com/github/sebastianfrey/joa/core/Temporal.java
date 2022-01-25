package com.github.sebastianfrey.joa.core;

import java.util.ArrayList;
import java.util.List;

public class Temporal {
  private List<List<String>> interval = new ArrayList<>();

  public List<List<String>> getInterval() {
    return interval;
  }

  public void setInterval(List<List<String>> interval) {
    this.interval = interval;
  }

  public void addInterval(List<String> interval) {
    this.interval.add(interval);
  }
}
