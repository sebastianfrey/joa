package com.github.sebastianfrey.joa.configuration;

import javax.annotation.Nullable;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.sebastianfrey.joa.configuration.gpkg.GeoPackageConfiguration;
import com.github.sebastianfrey.joa.services.OGCAPIService;
import com.github.sebastianfrey.joa.services.gpkg.GeoPackageService;

public class OGCAPIServiceFactory {
  @Nullable
  @Valid
  private GeoPackageConfiguration geoPackageConfiguration;

  @JsonProperty("gpkg")
  public GeoPackageConfiguration getGeoPackageConfiguration() {
    return geoPackageConfiguration;
  }

  @JsonProperty("gpkg")
  public void setGeoPackageConfiguration(GeoPackageConfiguration geoPackageConfiguration) {
    this.geoPackageConfiguration = geoPackageConfiguration;
  }

  public OGCAPIService getOGCAPIService() {
    if (geoPackageConfiguration != null) {
      String workspace = geoPackageConfiguration.getWorkspace();
      String runtime = geoPackageConfiguration.getRuntime();

      return new GeoPackageService(workspace, runtime);
    } else {
      throw new IllegalStateException("No JOA service configured. Supported backends are: GeoPackage");
    }
  }
}
