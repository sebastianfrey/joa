package com.github.sebastianfrey.joa.resources.views;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;

public class LocalizedView extends ContextAwareView {

  public static class Messages {
    private final String name;
    private final Locale locale;

    public Messages(String template, Locale locale) {
      this.name = getBundleName(template);
      this.locale = getBundleLocale(locale);
    }

    public String get(String key, Object ...replacments) {
      ResourceBundle bundle = fetchBundle();

      String message = bundle.getString(key);

      if (replacments.length > 0) {
        message = MessageFormat.format(message, replacments);
      }

      return message;
    }

    private ResourceBundle fetchBundle() {
      return ResourceBundle.getBundle(name, locale);
    }

    private String getBundleName(String template) {
      return template.replace(".ftl", "").substring(1).replace("/", ".");
    }

    private Locale getBundleLocale(Locale locale) {
      if (locale == null) {
        return Locale.ENGLISH;
      }

      return locale;
    }
  }

  private Messages messages;

  public LocalizedView(String view) {
    super(view);
  }

  @Override
  public void setRequestContext(ContainerRequestContext containerRequestContext, HttpServletRequest request) {
    super.setRequestContext(containerRequestContext, request);

    this.messages = new Messages(getTemplateName(), getContext().getLocale());
  }

  public Messages getMessages() {
    return messages;
  }
}
