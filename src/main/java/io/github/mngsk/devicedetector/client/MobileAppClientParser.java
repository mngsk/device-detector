package io.github.mngsk.devicedetector.client;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MobileAppClientParser extends AbstractClientParser<ClientRegex> {

  public MobileAppClientParser() {
    super("mobile app", "regexes/client/mobile_apps.yml");
  }

  public MobileAppClientParser(ObjectMapper objectMapper) {
    super("mobile app", "regexes/client/mobile_apps.yml", objectMapper);
  }
}
