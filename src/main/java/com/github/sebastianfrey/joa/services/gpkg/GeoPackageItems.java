package com.github.sebastianfrey.joa.services.gpkg;

import java.util.List;
import javax.ws.rs.core.Link;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.sebastianfrey.joa.models.Items;
import com.github.sebastianfrey.joa.models.Linkable;
import com.github.sebastianfrey.joa.models.MediaType;
import com.github.sebastianfrey.joa.utils.LinkUtils;

/**
 * GeoPackage specific implementation of the Items model.
 *
 * @author sfrey
 */
public class GeoPackageItems extends Items<GeoPackageItems> {
  @JsonIgnore
  private String queryString = "";
  @JsonIgnore
  private Long offset;
  @JsonIgnore
  private Integer limit;
  @JsonIgnore
  private Long pages;

  private boolean isNextPageAvailable;
  private boolean isPrevPageAvailable;
  private boolean isFirstPageAvailable;
  private boolean isLastPageAvailable;

  @Override
  public GeoPackageItems collectionId(String collectionId) {
    super.collectionId(collectionId);
    return this;
  }

  @Override
  public GeoPackageItems serviceId(String serviceId) {
    super.serviceId(serviceId);
    return this;
  }

  public String getQueryString() {
    return queryString;
  }

  public void setQueryString(String query) {
    if (query == null) {
      query = "";
    }

    this.queryString = query;
  }

  public GeoPackageItems queryString(String query) {
    setQueryString(query);
    return this;
  }

  public Long getOffset() {
    return offset;
  }

  public void setOffset(Long offset) {
    this.offset = offset;

    updatePageIndicators();
  }

  public GeoPackageItems offset(Long offset) {
    setOffset(offset);
    return this;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;

    updatePageCount();
    updatePageIndicators();
  }

  public GeoPackageItems limit(Integer limit) {
    setLimit(limit);
    return this;
  }

  @Override
  public void setNumberMatched(Long total) {
    super.setNumberMatched(total);

    updatePageCount();
    updatePageIndicators();
  }

  @Override
  public boolean isNextPageAvailable() {
    return isNextPageAvailable;
  }

  @Override
  public boolean isPrevPageAvailable() {
    return isPrevPageAvailable;
  }

  @Override
  public boolean isFirstPageAvailable() {
    return isFirstPageAvailable;
  }

  @Override
  public boolean isLastPageAvailable() {
    return isLastPageAvailable;
  }

  @Override
  public List<Link> getLinks() {
    List<Link> links = super.getLinks();

    for (Link link : links) {
      int index = links.indexOf(link);
      Link newLink = LinkUtils.transformUri(link, (uriBuilder) -> {

        uriBuilder.replaceQuery(queryString);

        // since query string is replaced, we must set the f parameter accordingly
        switch (link.getType()) {
          case MediaType.TEXT_HTML:
            uriBuilder.removeQueryParam("f");
            break;
          case MediaType.APPLICATION_GEO_JSON:
          case MediaType.APPLICATION_JSON:
            uriBuilder.replaceQueryParam("f", "json");
            break;
        }

        switch (link.getRel()) {
          case Linkable.NEXT:
            uriBuilder.replaceQueryParam("offset", offset + limit);
            break;

          case Linkable.PREV:
            uriBuilder.removeQueryParam("offset");
            if (offset - limit > 0) {
              uriBuilder.replaceQueryParam("offset", offset - limit);
            }
            break;

          case Linkable.FIRST:
            uriBuilder.removeQueryParam("offset");
            break;

          case Linkable.LAST:
            Long total = getNumberMatched();
            Long last = limit * pages;

            Boolean hasMore = ((total - last) % limit) > 0;

            if (!hasMore) {
              last = last - limit;
            }

            uriBuilder.replaceQueryParam("offset", last);
            break;

        }
      });
      links.set(index, newLink);
    }

    return links;
  }

  private void updatePageCount() {
    Long total = getNumberMatched();

    if (total == null || limit == null) {
      return;
    }

    pages = Math.floorDiv(total, limit);
  }

  private void updatePageIndicators() {
    Long total = getNumberMatched();

    if (total == null || offset == null || limit == null) {
      return;
    }

    isNextPageAvailable = (offset + limit) < total;
    isPrevPageAvailable = (offset - limit) >= 0;
    isLastPageAvailable = (offset + limit) < (limit * pages);
    isFirstPageAvailable = (offset - limit) >= 0;
  }
}
