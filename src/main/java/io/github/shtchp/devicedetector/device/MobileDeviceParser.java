package io.github.shtchp.devicedetector.device;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MobileDeviceParser extends AbstractDeviceParser {

  public MobileDeviceParser() {
    super("regexes/device/mobiles.yml");
  }

  public MobileDeviceParser(ObjectMapper objectMapper) {
    super("regexes/device/mobiles.yml", objectMapper);
  }
}
