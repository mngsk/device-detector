package io.github.shtchp.devicedetector.client;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ClientRegex {

  private String regex;
  private String name;
  private String version;

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

  public String getVersion() {
    return this.version;
  }

  public void setVersion(String version) {
    // the fixture files uses nulls and empty strings interchangeably
    this.version = version == null ? "" : version;
  }

  public Pattern getPattern() {
    return this.pattern;
  }
}
