package io.github.mngsk.devicedetector.device;

import java.util.Optional;

public class Device {

	private final String type;
	private final String brand;
	private final String model;

	public Device(String type, String brand, String model) {
		this.type = type;
		this.brand = brand;
		this.model = model;
	}

	public String getType() {
		return this.type;
	}

	public Optional<String> getBrand() {
		return Optional.ofNullable(this.brand);
	}

	public Optional<String> getModel() {
		return Optional.ofNullable(this.model);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.brand == null) ? 0 : this.brand.hashCode());
		result = prime * result
				+ ((this.model == null) ? 0 : this.model.hashCode());
		result = prime * result
				+ ((this.type == null) ? 0 : this.type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Device other = (Device) obj;
		if (this.brand == null) {
			if (other.brand != null)
				return false;
		} else if (!this.brand.equals(other.brand))
			return false;
		if (this.model == null) {
			if (other.model != null)
				return false;
		} else if (!this.model.equals(other.model))
			return false;
		if (this.type == null) {
			if (other.type != null)
				return false;
		} else if (!this.type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if (this.brand == null) {
			return this.type;
		}

		StringBuilder builder = new StringBuilder();
		builder.append(this.brand);
		if (this.model != null) {
			builder.append(" ").append(this.model);
		}
		return builder.toString();
	}

}
