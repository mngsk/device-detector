package io.github.mngsk.devicedetector.client.bot;

import io.github.mngsk.devicedetector.client.ClientRegex;

public class BotRegex extends ClientRegex {

  private String category;
  private String url;
  private BotProducer producer;

  public String getCategory() {
    return this.category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public BotProducer getProducer() {
    return this.producer;
  }

  public void setProducer(BotProducer producer) {
    this.producer = producer;
  }
}
