package com.github.sebastianfrey.joa.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {
  private final String name;
  private final Locale locale;

  public Messages(String name, Locale locale) {
    this.name = name;
    this.locale = getBundleLocale(locale);
  }

  public String get(String key, Object ...replacments) {
    ResourceBundle bundle = fetchBundle();

    String message = key;

    if (bundle.containsKey(key)) {
      message = bundle.getString(key);
    }

    if (replacments.length > 0) {
      message = MessageFormat.format(message, replacments);
    }

    return message;
  }

  private ResourceBundle fetchBundle() {
    return ResourceBundle.getBundle(name, locale);
  }

  private Locale getBundleLocale(Locale locale) {
    if (locale == null) {
      return Locale.ENGLISH;
    }

    return locale;
  }
}
