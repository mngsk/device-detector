package io.github.mngsk.devicedetector.client.browser;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BrowserFixture {

  @JsonProperty("user_agent")
  private String userAgent;

  private Browser client;

  public String getUserAgent() {
    return this.userAgent;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  public Browser getClient() {
    return this.client;
  }

  public void setClient(Browser client) {
    this.client = client;
  }
}
