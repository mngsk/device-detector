package io.github.mngsk.devicedetector.client.bot;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BotFixture {

  @JsonProperty("user_agent")
  private String userAgent;

  private Bot bot;

  public String getUserAgent() {
    return this.userAgent;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  public Bot getBot() {
    return this.bot;
  }

  public void setBot(Bot bot) {
    this.bot = bot;
  }
}
