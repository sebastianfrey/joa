package com.github.sebastianfrey.joa.extensions.jackson;

import java.io.IOException;
import javax.ws.rs.core.Link;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class LinkDeserializer extends JsonDeserializer<Link> {

  @Override
  public Link deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonNode node = jp.readValueAsTree();

    String href = node.get("href").asText();

    Link.Builder linkBuilder = Link.fromUri(href);

    if (node.has(Link.TYPE)) {
      linkBuilder.type(node.get(Link.TYPE).asText());
    }

    if (node.has(Link.REL)) {
      linkBuilder.rel(node.get(Link.REL).asText());
    }

    if (node.has(Link.TITLE)) {
      linkBuilder.title(node.get(Link.TITLE).asText());
    }

    return linkBuilder.build();
  }
}
