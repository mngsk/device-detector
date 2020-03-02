package io.github.mngsk.devicedetector.device;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = DeviceMixin.Builder.class)
public class DeviceMixin {

	public static class Builder {

		private String type;
		private String brand;
		private String model;

		public Builder withType(String type) {
			this.type = type == null ? null : type.trim();
			switch (this.type) {
			case "4":
				this.type = "console";
				break;
			case "6":
				this.type = "car browser";
				break;
			case "8":
				this.type = "camera";
				break;
			default:
				// no-op
			}
			return this;
		}

		public Builder withBrand(String brand) {
			this.brand = brand == null ? null : brand.trim();
			switch (this.brand) {
			case "SA":
				this.brand = "Samsung";
				break;
			case "NN":
				this.brand = "Nikon";
				break;
			case "TA":
				this.brand = "Tesla";
				break;
			case "AR":
				this.brand = "Archos";
				break;
			case "MS":
				this.brand = "Microsoft";
				break;
			case "NI":
				this.brand = "Nintendo";
				break;
			case "OU":
				this.brand = "OUYA";
				break;
			case "SO":
				this.brand = "Sony";
				break;
			default:
				// no-op
			}
			return this;
		}

		public Builder withModel(String model) {
			this.model = model == null ? null : model.trim();
			return this;
		}

		public Device build() {
			return new Device(this.type, this.brand, this.model);
		}

	}

}
