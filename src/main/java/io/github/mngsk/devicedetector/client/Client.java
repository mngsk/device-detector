package io.github.mngsk.devicedetector.client;

import java.util.Optional;

public class Client {

	private final String type;
	private final String name;
	private final String version;

	public Client(String type, String name, String version) {
		this.type = type;
		this.name = name;
		this.version = version;
	}

	public String getType() {
		return this.type;
	}

	public Optional<String> getName() {
		return Optional.ofNullable(this.name);
	}

	public Optional<String> getVersion() {
		return Optional.ofNullable(this.version);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result
				+ ((this.type == null) ? 0 : this.type.hashCode());
		result = prime * result
				+ ((this.version == null) ? 0 : this.version.hashCode());
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
		Client other = (Client) obj;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
			return false;
		if (this.type == null) {
			if (other.type != null)
				return false;
		} else if (!this.type.equals(other.type))
			return false;
		if (this.version == null) {
			if (other.version != null)
				return false;
		} else if (!this.version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if (this.name == null) {
			return this.type;
		}

		StringBuilder builder = new StringBuilder();
		builder.append(this.name);
		if (this.version != null) {
			builder.append(" ").append(this.version);
		}
		return builder.toString();
	}

}
