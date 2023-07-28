package io.github.shtchp.devicedetector.client.browser;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class EngineRegex {

  private String regex;
  private String name;

  @JsonIgnore private Pattern pattern;

  public String getRegex() {
    return this.regex;
  }

  public void setRegex(String regex) {
    this.regex = regex;
    this.pattern =
        Pattern.compile(
            "(?:^|[^A-Z0-9\\-_]|[^A-Z0-9\\-]_|sprd-)(?:" + regex + ")", Pattern.CASE_INSENSITIVE);
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Pattern getPattern() {
    return this.pattern;
  }
}
