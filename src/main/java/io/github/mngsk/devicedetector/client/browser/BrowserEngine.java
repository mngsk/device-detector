package io.github.mngsk.devicedetector.client.browser;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BrowserEngine {

  @JsonProperty("default")
  private String defaultValue;

  private Map<String, String> versions;

  public String getDefaultValue() {
    return this.defaultValue;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public Map<String, String> getVersions() {
    return this.versions;
  }

  public void setVersions(Map<String, String> versions) {
    this.versions = versions;
  }
}
