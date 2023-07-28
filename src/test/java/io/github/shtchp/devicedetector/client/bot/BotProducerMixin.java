package io.github.shtchp.devicedetector.client.bot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BotProducerMixin {

  @JsonCreator
  public BotProducerMixin(@JsonProperty("name") String name, @JsonProperty("url") String url) {}
}
