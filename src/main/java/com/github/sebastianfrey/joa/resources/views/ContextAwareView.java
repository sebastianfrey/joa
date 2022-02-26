package com.github.sebastianfrey.joa.resources.views;

import java.nio.charset.Charset;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import io.dropwizard.views.View;

public class ContextAwareView extends View {
  private Context context;

  public static class Context {
    private final ContainerRequestContext containerRequestContext;
    private final HttpServletRequest request;

    public Context(ContainerRequestContext containerRequestContext, HttpServletRequest request) {
      this.containerRequestContext = containerRequestContext;
      this.request = request;
    }

    public String toAbsoluteUrl(String path) {
      return containerRequestContext.getUriInfo().getBaseUriBuilder().path(path).build().toString();
    }

    public Locale getLocale() {
      return request.getLocale();
    }
  }

  public ContextAwareView(String view) {
    super(view, Charset.forName("UTF-8"));
  }

  public void setRequestContext(ContainerRequestContext containerRequestContext, HttpServletRequest request) {
    context = new Context(containerRequestContext, request);
  }

  public Context getContext() {
    return context;
  }
}
