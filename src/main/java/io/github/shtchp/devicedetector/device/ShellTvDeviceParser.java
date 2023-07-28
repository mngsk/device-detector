package io.github.shtchp.devicedetector.device;

import java.util.Optional;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ShellTvDeviceParser extends AbstractDeviceParser {

  private Pattern shellTvPattern =
      Pattern.compile(
          "(?:^|[^A-Z0-9\\-_]|[^A-Z0-9\\-]_|sprd-)(?:[a-z]+[ _]Shell[ _]\\w{6})",
          Pattern.CASE_INSENSITIVE);

  public ShellTvDeviceParser() {
    super("regexes/device/shell_tv.yml");
  }

  public ShellTvDeviceParser(ObjectMapper objectMapper) {
    super("regexes/device/shell_tv.yml", objectMapper);
  }

  @Override
  public Optional<Device> parse(String userAgent) {
    if (!this.shellTvPattern.matcher(userAgent).find()) {
      return Optional.empty();
    }

    return Optional.of(super.parse(userAgent).orElse(new Device("tv", null, null)));
  }
}
