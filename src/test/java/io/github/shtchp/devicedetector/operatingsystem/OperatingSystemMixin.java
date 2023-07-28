package io.github.shtchp.devicedetector.operatingsystem;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = OperatingSystemMixin.Builder.class)
public class OperatingSystemMixin {

  public static class Builder {

    private String name;
    private String family;
    private String platform;
    private String version;

    public Builder withName(String name) {
      this.name = name == null ? null : name.trim();
      return this;
    }

    public Builder withFamily(String family) {
      this.family = family == null ? null : family.trim();
      return this;
    }

    public Builder withPlatform(String platform) {
      // the test fixture files use null values and empty strings
      // interchangeably
      this.platform = platform == null ? null : platform.trim().isEmpty() ? null : platform.trim();
      return this;
    }

    public Builder withVersion(String version) {
      // the test fixture files contain null values, while the main
      // fixture files have empty strings
      this.version = version == null ? "" : version.trim();
      return this;
    }

    public OperatingSystem build() {
      return new OperatingSystem(this.name, this.family, this.platform, this.version);
    }
  }
}
