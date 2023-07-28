package io.github.shtchp.devicedetector.client.browser;

import io.github.shtchp.devicedetector.client.ClientRegex;

public class BrowserRegex extends ClientRegex {

  private BrowserEngine engine;

  public BrowserEngine getEngine() {
    return this.engine;
  }

  public void setEngine(BrowserEngine engine) {
    this.engine = engine;
  }
}
