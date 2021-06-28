package io.github.mngsk.devicedetector.device;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CarDeviceParser extends AbstractDeviceParser {

  public CarDeviceParser() {
    super("regexes/device/car_browsers.yml");
  }

  public CarDeviceParser(ObjectMapper objectMapper) {
    super("regexes/device/car_browsers.yml", objectMapper);
  }
}
