package io.github.mngsk.devicedetector.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientMixin {

	@JsonCreator
	public ClientMixin(@JsonProperty("type") String type,
			@JsonProperty("name") String name,
			@JsonProperty("version") String version) {
	}

}
