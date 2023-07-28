package io.github.shtchp.devicedetector.client.browser;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = BrowserMixin.Builder.class)
public class BrowserMixin {

  public static class Builder {

    private String name;
    private String version;
    private String engine;
    private String engineVersion;

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

    public Browser build() {
      return new Browser(
          this.name, this.version, new Engine(this.engine, this.engineVersion), null);
    }
  }
}
