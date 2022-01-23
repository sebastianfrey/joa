package com.github.joa.core;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Link;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.joa.util.LinkUtils;
import mil.nga.sf.geojson.Feature;

@JsonPropertyOrder({"type", "numberReturned", "numberMatched", "timeStamp", "features", "links"})
public class Items extends Linkable {
  @JsonIgnore
  private String serviceId = "";
  @JsonIgnore
  private String collectionId = "";
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

  private List<Feature> features = new ArrayList<>();
  private Long total;

  public String getType() {
    return "FeatureCollection";
  }

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public String getCollectionId() {
    return collectionId;
  }

  public void setCollectionId(String collectionId) {
    this.collectionId = collectionId;
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

  public Long getOffset() {
    return offset;
  }

  public void setOffset(Long offset) {
    this.offset = offset;

    if (total != null) {
      updatePageCount();
    }

    updatePageIndicators();
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;

    this.updatePageIndicators();
  }

  @JsonIgnore
  public boolean isNextPageAvailable() {
    return isNextPageAvailable;
  }

  @JsonIgnore
  public boolean isPrevPageAvailable() {
    return isPrevPageAvailable;
  }

  @JsonIgnore
  public boolean isFirstPageAvailable() {
    return isFirstPageAvailable;
  }

  @JsonIgnore
  public boolean isLastPageAvailable() {
    return isLastPageAvailable;
  }

  public List<Feature> getFeatures() {
    return features;
  }

  public void setFeatures(List<Feature> features) {
    this.features = features;
  }

  public void addFeature(Feature feature) {
    this.features.add(feature);
  }

  public Long getNumberMatched() {
    return total;
  }

  public void setNumberMatched(Long total) {
    this.total = total;

    if (offset != null) {
      updatePageCount();
    }

    this.updatePageIndicators();
  }

  public String getTimeStamp() {
    return Instant.now().truncatedTo(ChronoUnit.SECONDS).toString();
  }

  public Integer getNumberReturned() {
    return features.size();
  }

  @Override
  public List<Link> getLinks() {
    List<Link> links = super.getLinks();

    for (Link link : links) {
      int index = links.indexOf(link);
      Link newLink = LinkUtils.replaceQuery(link, (uriBuilder) -> {
        uriBuilder.replaceQuery(queryString);

        switch (link.getRel()) {
          case "next":
            uriBuilder.replaceQueryParam("offset", offset + limit);
            break;

          case "prev":
            uriBuilder.removeQueryParam("offset");
            if (offset - limit > 0) {
              uriBuilder.replaceQueryParam("offset", offset - limit);
            }
            break;

          case "first":
            uriBuilder.removeQueryParam("offset");
            break;

          case "last":
            uriBuilder.replaceQueryParam("offset", limit * pages);
            break;
        }
      });
      links.set(index, newLink);
    }

    return links;
  }

  private void updatePageCount() {
    pages = Math.floorDiv(total, limit);
  }

  private void updatePageIndicators() {
    if (total == null || offset == null || limit == null) {
      return;
    }

    isNextPageAvailable = (offset + limit) <= total;
    isPrevPageAvailable = (offset - limit) >= 0;
    isLastPageAvailable = (offset + limit) <= (limit * pages);
    isFirstPageAvailable = (offset - limit) >= 0;
  }
}
