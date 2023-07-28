package io.github.shtchp.devicedetector.operatingsystem;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OperatingSystemFixture {

  @JsonProperty("user_agent")
  private String userAgent;

  @JsonProperty("os")
  private OperatingSystem operatingSystem;

  public String getUserAgent() {
    return this.userAgent;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  public OperatingSystem getOperatingSystem() {
    return this.operatingSystem;
  }

  public void setOperatingSystem(OperatingSystem operatingSystem) {
    this.operatingSystem = operatingSystem;
  }
}
