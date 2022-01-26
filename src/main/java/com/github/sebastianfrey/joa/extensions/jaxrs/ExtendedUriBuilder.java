package com.github.sebastianfrey.joa.extensions.jaxrs;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.Map;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriBuilderException;

public class ExtendedUriBuilder {
  private UriBuilder uriBuilder;

  public ExtendedUriBuilder(UriBuilder uriBuilder) {
    this.uriBuilder = uriBuilder;
  }

  public UriBuilder clone() {
    return uriBuilder.clone();
  }

  public boolean equals(Object arg0) {
    return uriBuilder.equals(arg0);
  }

  public int hashCode() {
    return uriBuilder.hashCode();
  }

  public UriBuilder uri(URI uri) {
    return uriBuilder.uri(uri);
  }

  public UriBuilder uri(String uriTemplate) {
    return uriBuilder.uri(uriTemplate);
  }

  public UriBuilder scheme(String scheme) {
    return uriBuilder.scheme(scheme);
  }

  public UriBuilder schemeSpecificPart(String ssp) {
    return uriBuilder.schemeSpecificPart(ssp);
  }

  public UriBuilder userInfo(String ui) {
    return uriBuilder.userInfo(ui);
  }

  public UriBuilder host(String host) {
    return uriBuilder.host(host);
  }

  public UriBuilder port(int port) {
    return uriBuilder.port(port);
  }

  public UriBuilder replacePath(String path) {
    return uriBuilder.replacePath(path);
  }

  public UriBuilder path(String path) {
    return uriBuilder.path(path);
  }

  public UriBuilder path(Class<?> resource) {
    return uriBuilder.path(resource);
  }

  public UriBuilder path(Class<?> resource, String method) {
    return uriBuilder.path(resource, method);
  }

  public UriBuilder path(Method method) {
    return uriBuilder.path(method);
  }

  public UriBuilder segment(String... segments) {
    return uriBuilder.segment(segments);
  }

  public UriBuilder replaceMatrix(String matrix) {
    return uriBuilder.replaceMatrix(matrix);
  }

  public UriBuilder matrixParam(String name, Object... values) {
    return uriBuilder.matrixParam(name, values);
  }

  public UriBuilder replaceMatrixParam(String name, Object... values) {
    return uriBuilder.replaceMatrixParam(name, values);
  }

  public UriBuilder replaceQuery(String query) {
    return uriBuilder.replaceQuery(query);
  }

  public UriBuilder queryParam(String name, Object... values) {
    return uriBuilder.queryParam(name, values);
  }

  public UriBuilder replaceQueryParam(String name, Object... values) {
    return uriBuilder.replaceQueryParam(name, values);
  }

  public UriBuilder removeQueryParam(String name) {
    return this.replaceQueryParam(name, (Object[]) null);
  }

  public UriBuilder fragment(String fragment) {
    return uriBuilder.fragment(fragment);
  }

  public UriBuilder resolveTemplate(String name, Object value) {
    return uriBuilder.resolveTemplate(name, value);
  }

  public UriBuilder resolveTemplate(String name, Object value, boolean encodeSlashInPath) {
    return uriBuilder.resolveTemplate(name, value, encodeSlashInPath);
  }

  public UriBuilder resolveTemplateFromEncoded(String name, Object value) {
    return uriBuilder.resolveTemplateFromEncoded(name, value);
  }

  public UriBuilder resolveTemplates(Map<String, Object> templateValues) {
    return uriBuilder.resolveTemplates(templateValues);
  }

  public UriBuilder resolveTemplates(Map<String, Object> templateValues, boolean encodeSlashInPath)
      throws IllegalArgumentException {
    return uriBuilder.resolveTemplates(templateValues, encodeSlashInPath);
  }

  public UriBuilder resolveTemplatesFromEncoded(Map<String, Object> templateValues) {
    return uriBuilder.resolveTemplatesFromEncoded(templateValues);
  }

  public URI buildFromMap(Map<String, ?> values) {
    return uriBuilder.buildFromMap(values);
  }

  public URI buildFromMap(Map<String, ?> values, boolean encodeSlashInPath)
      throws IllegalArgumentException, UriBuilderException {
    return uriBuilder.buildFromMap(values, encodeSlashInPath);
  }

  public URI buildFromEncodedMap(Map<String, ?> values)
      throws IllegalArgumentException, UriBuilderException {
    return uriBuilder.buildFromEncodedMap(values);
  }

  public URI build(Object... values) throws IllegalArgumentException, UriBuilderException {
    return uriBuilder.build(values);
  }

  public URI build(Object[] values, boolean encodeSlashInPath)
      throws IllegalArgumentException, UriBuilderException {
    return uriBuilder.build(values, encodeSlashInPath);
  }

  public URI buildFromEncoded(Object... values)
      throws IllegalArgumentException, UriBuilderException {
    return uriBuilder.buildFromEncoded(values);
  }

  public String toString() {
    return uriBuilder.toString();
  }

  public String toTemplate() {
    return uriBuilder.toTemplate();
  }


}
