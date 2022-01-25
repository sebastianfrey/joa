package com.github.sebastianfrey.joa.services;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;

public interface UploadService {
  public void addService(FormDataBodyPart body) throws Exception;
  public void updateService(String serviceId, FormDataBodyPart body) throws Exception;
  public void deleteService(String serviceId) throws Exception;
}
