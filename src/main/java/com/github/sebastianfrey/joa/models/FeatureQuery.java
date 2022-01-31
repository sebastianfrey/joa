package com.github.sebastianfrey.joa.models;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedMap;

/**
 * The FeatureQuery model represents the OGC API parameters list for an items query.
 */
public abstract class FeatureQuery {

  public static List<String> RESERVED_QUERY_PARAMS = List.of("f", "datetime", "bbox", "limit", "offset");

  /**
   * returns the raw query string from an items query.
   *
   * @return The raw query string
   */
  public abstract String getQueryString();

  /**
   * The optional bbox parameter, which allows to query by a spatial extent.
   *
   * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/parameters/bbox.yaml"
   *
   * @return The spatial extent.
   */
  public abstract Bbox getBbox();

  /**
   * The optional datetime parameter, which allows to query by a temporal extent.
   *
   * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/parameters/datetime.yaml"
   *
   * @return The temporal extent.
   */
  public abstract Datetime getDatetime();

  /**
   * The optional offset parameter to skip a specific number of items.
   *
   * @return The number of items to skip.
   */
  public abstract Long getOffset();

  /**
   * The optional limit prameter to limit the number of items to return.
   *
   * @see "http://schemas.opengis.net/ogcapi/features/part1/1.0/openapi/parameters/limit.yaml"
   *
   * @return The number of items to return.
   */
  public abstract Integer getLimit();

  /**
   * returns the list of all remainig query parameters from an items query.
   *
   * @return Map of query parameters
   */
  public abstract MultivaluedMap<String, String> getQueryParameters();

  /**
   * verifies that the request does not conatin unknown query parameters.
   *
   * @see "https://docs.opengeospatial.org/is/17-069r3/17-069r3.html#query_parameters"
   *
   * @param columns The list of columns to validate against.
   *
   * @throws BadRequestException when the request contains unknown query parameters.
   */
  public void validateQueryParameters(List<String> columns) {
    final Set<String> allowedQueryParams =
        Stream.concat(RESERVED_QUERY_PARAMS.stream(), columns.stream()).collect(Collectors.toSet());

    String unknownQueryParameters = getQueryParameters().keySet().stream().filter((queryParameter) -> {
      return !allowedQueryParams.contains(queryParameter);
    }).collect(Collectors.joining(", "));

    if (unknownQueryParameters != null && unknownQueryParameters.length() > 0) {
      throw new BadRequestException("Unknown query parameters detected: " + unknownQueryParameters);
    }
  }
}
