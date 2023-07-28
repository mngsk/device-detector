package io.github.shtchp.devicedetector.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientFixture {

  @JsonProperty("user_agent")
  private String userAgent;

  private Client client;

  public String getUserAgent() {
    return this.userAgent;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  public Client getClient() {
    return this.client;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
