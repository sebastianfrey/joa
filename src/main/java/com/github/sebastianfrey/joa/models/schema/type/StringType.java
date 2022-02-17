package com.github.sebastianfrey.joa.models.schema.type;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = StringType.class)
public class StringType extends GenericType<StringType> {
  private Long minLength;
  private Long maxLength;
  private String pattern;
  private String format;

  @Override
  public String getType() {
    return "string";
  }

  public Long getMinLength() {
    return minLength;
  }

  public void setMinLength(Integer minLength) {
    setMinLength(Long.valueOf(minLength));
  }

  public void setMinLength(Long minLength) {
    this.minLength = minLength;
  }

  public StringType minLength(Integer minLength) {
    setMinLength(minLength);
    return this;
  }

  public Long getMaxLength() {
    return maxLength;
  }

  public void setMaxLength(Integer maxLength) {
    setMaxLength(Long.valueOf(maxLength));
  }

  public void setMaxLength(Long maxLength) {
    this.maxLength = maxLength;
  }

  public StringType maxLength(Integer maxLength) {
    setMaxLength(maxLength);
    return this;
  }

  public StringType maxLength(Long maxLength) {
    setMaxLength(maxLength);
    return this;
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public StringType pattern(String pattern) {
    setPattern(pattern);
    return this;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public StringType format(String format) {
    setFormat(format);
    return this;
  }
}
