package com.github.sebastianfrey.joa.resources.views;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import com.github.sebastianfrey.joa.i18n.Messages;

public class LocalizedView extends ContextAwareView {
  private final static String BUNDLE_NAME = "com.github.sebastianfrey.joa.i18n.ViewResources";

  private Messages messages;

  public LocalizedView(String view) {
    super(view);
  }

  @Override
  public void setRequestContext(ContainerRequestContext containerRequestContext, HttpServletRequest request) {
    super.setRequestContext(containerRequestContext, request);

    this.messages = new Messages(BUNDLE_NAME, getContext().getLocale());
  }

  public Messages getMessages() {
    return messages;
  }
}
