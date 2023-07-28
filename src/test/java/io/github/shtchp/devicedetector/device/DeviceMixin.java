package io.github.shtchp.devicedetector.device;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = DeviceMixin.Builder.class)
public class DeviceMixin {

  public static class Builder {

    private String type;
    private String brand;
    private String model;

    public Builder withType(String type) {
      if (type == null || type.trim().isEmpty()) {
        return this;
      }

      this.type = type.trim();

      return this;
    }

    public Builder withBrand(String brand) {
      if (brand == null || brand.trim().isEmpty()) {
        return this;
      }

      this.brand = brand.trim();

      return this;
    }

    public Builder withModel(String model) {
      if (model == null || model.trim().isEmpty()) {
        return this;
      }

      this.model = model.trim();

      return this;
    }

    public Device build() {
      if (this.type == null || this.type.isEmpty()) {
        return null;
      }
      return new Device(this.type, this.brand, this.model);
    }
  }
}
