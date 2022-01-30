package com.github.sebastianfrey.joa.configuration.gpkg;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoPackageConfiguration {
  @NotNull
  @NotEmpty
  private String workspace;

  @NotNull
  @NotEmpty
  @DefaultValue("mod_spatialite")
  private String runtime;

  @JsonProperty
  public String getWorkspace() {
    return workspace;
  }
  @JsonProperty
  public void setWorksapce(String workspace) {
    this.workspace = workspace;
  }
  @JsonProperty
  public String getRuntime() {
    return runtime;
  }

  @JsonProperty
  public void setRuntime(String runtime) {
    this.runtime = runtime;
  }
}
