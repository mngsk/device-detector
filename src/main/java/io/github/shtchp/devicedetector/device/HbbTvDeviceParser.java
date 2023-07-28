package io.github.shtchp.devicedetector.device;

import java.util.Optional;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HbbTvDeviceParser extends AbstractDeviceParser {

  private Pattern hbbtvPattern =
      Pattern.compile(
          "(?:^|[^A-Z0-9\\-_]|[^A-Z0-9\\-]_|sprd-)(?:HbbTV/([1-9]{1}(?:\\.[0-9]{1}){1,2}))",
          Pattern.CASE_INSENSITIVE);

  public HbbTvDeviceParser() {
    super("regexes/device/televisions.yml");
  }

  public HbbTvDeviceParser(ObjectMapper objectMapper) {
    super("regexes/device/televisions.yml", objectMapper);
  }

  @Override
  public Optional<Device> parse(String userAgent) {
    if (!this.hbbtvPattern.matcher(userAgent).find()) {
      return Optional.empty();
    }

    return Optional.of(super.parse(userAgent).orElse(new Device("tv", null, null)));
  }
}
