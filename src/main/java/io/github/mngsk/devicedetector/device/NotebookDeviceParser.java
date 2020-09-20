package io.github.mngsk.devicedetector.device;

import java.util.Optional;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NotebookDeviceParser extends AbstractDeviceParser {

  private Pattern notebookPattern = Pattern.compile(
      "(?:^|[^A-Z0-9\\-_]|[^A-Z0-9\\-]_|sprd-)(?:FBMD/)",
      Pattern.CASE_INSENSITIVE);

	public NotebookDeviceParser() {
		super("regexes/device/notebooks.yml");
	}

	public NotebookDeviceParser(ObjectMapper objectMapper) {
		super("regexes/device/notebooks.yml", objectMapper);
	}

  @Override
  public Optional<Device> parse(String userAgent) {
    if (!this.notebookPattern.matcher(userAgent).find()) {
      return Optional.empty();
    }

    return super.parse(userAgent);
  }

}
