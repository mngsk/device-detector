package io.github.shtchp.devicedetector.client.bot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BotMixin {

  @JsonCreator
  public BotMixin(
      @JsonProperty("name") String name,
      @JsonProperty("category") String category,
      @JsonProperty("url") String url,
      @JsonProperty("producer") BotProducer producer) {}
}
