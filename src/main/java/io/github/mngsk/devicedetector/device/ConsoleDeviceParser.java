package io.github.mngsk.devicedetector.device;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConsoleDeviceParser extends AbstractDeviceParser {

  public ConsoleDeviceParser() {
    super("regexes/device/consoles.yml");
  }

  public ConsoleDeviceParser(ObjectMapper objectMapper) {
    super("regexes/device/consoles.yml", objectMapper);
  }
}
