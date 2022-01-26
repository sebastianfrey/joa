package com.github.sebastianfrey.joa.resources.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ParamException.QueryParamException;
import org.slf4j.LoggerFactory;
import io.dropwizard.jersey.errors.ErrorMessage;
import io.dropwizard.jersey.errors.LoggingExceptionMapper;

public class QueryParamExceptionHandler extends LoggingExceptionMapper<QueryParamException> {

  public QueryParamExceptionHandler() {
    super(LoggerFactory.getLogger(QueryParamExceptionHandler.class));
  }

  @Override
  public Response toResponse(QueryParamException exception) {
    String message = "invalid value for parameter '" + exception.getParameterName() + "'";

    return Response.status(400)
        .type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(400, message))
        .build();
  }

}
