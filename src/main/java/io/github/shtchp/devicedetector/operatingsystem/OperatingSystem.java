package io.github.shtchp.devicedetector.operatingsystem;

import java.util.Optional;

public class OperatingSystem {

  private final String name;
  private final String family;
  private final String platform;
  private final String version;

  public OperatingSystem(String name, String family, String platform, String version) {
    this.name = name;
    this.family = family;
    this.platform = platform;
    this.version = version;
  }

  public String getName() {
    return this.name;
  }

  public Optional<String> getFamily() {
    return Optional.ofNullable(this.family);
  }

  public Optional<String> getPlatform() {
    return Optional.ofNullable(this.platform);
  }

  public Optional<String> getVersion() {
    return Optional.ofNullable(this.version);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
    result = prime * result + ((this.platform == null) ? 0 : this.platform.hashCode());
    result = prime * result + ((this.version == null) ? 0 : this.version.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    OperatingSystem other = (OperatingSystem) obj;
    if (this.name == null) {
      if (other.name != null) return false;
    } else if (!this.name.equals(other.name)) return false;
    if (this.platform == null) {
      if (other.platform != null) return false;
    } else if (!this.platform.equals(other.platform)) return false;
    if (this.version == null) {
      if (other.version != null) return false;
    } else if (!this.version.equals(other.version)) return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(this.name);
    if (this.getVersion().isPresent()) {
      builder.append(" ").append(this.version);
    }
    if (this.getPlatform().isPresent()) {
      builder.append(" (").append(this.platform).append(")");
    }
    return builder.toString();
  }
}
