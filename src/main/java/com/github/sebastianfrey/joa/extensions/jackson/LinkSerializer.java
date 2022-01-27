package com.github.sebastianfrey.joa.extensions.jackson;

import java.io.IOException;
import javax.ws.rs.core.Link;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Jackson serializer for JAX-RS Links.
 *
 * @author sfrey
 */
public class LinkSerializer extends StdSerializer<Link> {

  public LinkSerializer() {
    this(null);
  }

  public LinkSerializer(Class<Link> t) {
    super(t);
  }


  @Override
  public void serialize(Link link, JsonGenerator jgen, SerializerProvider provider) throws IOException {
    jgen.writeStartObject();
    if (link.getRel() != null) {
      jgen.writeStringField("rel", link.getRel());
    }
    if (link.getType() != null) {
      jgen.writeStringField("type", link.getType());
    }
    if (link.getTitle() != null) {
      jgen.writeStringField("title", link.getTitle());
    }
    if (link.getUri() != null) {
      jgen.writeStringField("href", link.getUri().toString().replace("%3F", "?"));
    }
    jgen.writeEndObject();
  }
}
