package com.github.sebastianfrey.joa.services;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;

/**
 * Defines the base interface for JOA UploadService implementations.
 *
 * @author sfrey
 */
public interface UploadService {
  /**
   * Add a new OGC API Service by using the provided files.
   *
   * @param body Geo files for populating the service.
   *
   * @throws Exception
   */
  public void addService(FormDataBodyPart body) throws Exception;

  /**
   * Update a given OGC API Service by using the provided files.
   *
   * @param serviceId The ID of the service.
   * @param body Geo files for populating the service.
   *
   * @throws Exception
   */
  public void updateService(String serviceId, FormDataBodyPart body) throws Exception;

  /**
   * Delete a given OGC API Service by its id.
   *
   * @param serviceId The ID of the service.
   *
   * @throws Exception
   */
  public void deleteService(String serviceId) throws Exception;
}
