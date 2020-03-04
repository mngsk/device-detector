package io.github.mngsk.devicedetector.device;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceFixture {

	@JsonProperty("user_agent")
	private String userAgent;
	private Device device;

	public String getUserAgent() {
		return this.userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public Device getDevice() {
		return this.device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

}
