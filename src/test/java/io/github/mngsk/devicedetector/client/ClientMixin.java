package io.github.mngsk.devicedetector.client;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.github.mngsk.devicedetector.client.browser.Browser;
import io.github.mngsk.devicedetector.client.browser.Engine;

@JsonDeserialize(builder = ClientMixin.Builder.class)
public class ClientMixin {

  public static class Builder {

    private String type;
    private String name;
    private String version;
    private String engine;
    private String engineVersion;

    public Builder withType(String type) {
      this.type = type == null ? null : type.trim();
      return this;
    }

    public Builder withName(String name) {
      this.name = name == null ? null : name.trim();
      return this;
    }

    public Builder withVersion(String version) {
      this.version = version == null ? null : version.trim();
      return this;
    }

    public Builder withEngine(String engine) {
      this.engine = engine == null ? null : engine.trim();
      return this;
    }

    public Builder withEngineVersion(String engineVersion) {
      this.engineVersion = engineVersion == null ? null : engineVersion.trim();
      return this;
    }

    public Client build() {
      if (type.equals("browser")) {
        return new Browser(
            this.name, this.version, new Engine(this.engine, this.engineVersion), null);
      } else {
        return new Client(this.type, this.name, this.version);
      }
    }
  }
}
