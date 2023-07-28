package io.github.shtchp.devicedetector.device;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CameraDeviceParser extends AbstractDeviceParser {

  public CameraDeviceParser() {
    super("regexes/device/cameras.yml");
  }

  public CameraDeviceParser(ObjectMapper objectMapper) {
    super("regexes/device/cameras.yml", objectMapper);
  }
}
