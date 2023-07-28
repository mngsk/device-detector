package io.github.shtchp.devicedetector.util;

import java.util.Optional;
import java.util.regex.Matcher;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractParser<T> {

  public abstract Optional<T> parse(String userAgent);

  protected String buildByMatch(String item, Matcher matcher) {
    if (item == null) {
      return null;
    }

    if (!item.contains("$")) {
      return item.trim();
    }

    for (int i = 1; i <= 3; i++) {
      String replacement = "";

      if (item.indexOf("$" + i) > -1) {
        try {
          String group = matcher.group(i);
          replacement = Optional.ofNullable(group).orElse("");
        } catch (IndexOutOfBoundsException e) {
          replacement = "";
        }
      }

      item = item.replace("$" + i, replacement);
    }
    return item.trim();
  }

  protected String buildVersion(String version, Matcher matcher) {
    version = buildByMatch(version, matcher);
    if (version == null) {
      return null;
    }

    version = version.replace("_", ".");
    return StringUtils.strip(version, " .");
  }
}
