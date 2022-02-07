package com.github.sebastianfrey.joa.models;

import java.util.Set;
import javax.ws.rs.core.Link;
import com.fasterxml.jackson.annotation.JsonValue;
import com.github.sebastianfrey.joa.models.schema.JSONSchemaBuilder;
import com.github.sebastianfrey.joa.models.schema.type.ObjectType;

public class Queryables extends Linkable {
  ObjectType schema = JSONSchemaBuilder.objectType();
  String serviceId;
  String collectionId;

  public ObjectType getSchema() {
    return schema;
  }

  public void setSchema(ObjectType schema) {
    this.schema = schema;
  }

  public Queryables schema(ObjectType schema) {
    this.schema = schema;
    return this;
  }

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public Queryables serviceId(String serviceId) {
    setServiceId(serviceId);
    return this;
  }

  public String getCollectionId() {
    return collectionId;
  }

  public void setCollectionId(String collectionId) {
    this.collectionId = collectionId;
  }

  public Queryables collectionId(String collectionId) {
    setCollectionId(collectionId);
    return this;
  }

  public Set<String> getColumns() {
    return schema.getProperties().keySet();
  }

  @JsonValue
  public ObjectType toJson() {
    for (Link link : getLinks()) {
      if (link.getRel().equals("self")) {
        schema.id(link.getUri().toString());
        break;
      }
    }

    return schema;
  }
}
