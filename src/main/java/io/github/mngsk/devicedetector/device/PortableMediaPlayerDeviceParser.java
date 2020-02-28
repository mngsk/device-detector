package io.github.mngsk.devicedetector.device;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PortableMediaPlayerDeviceParser extends AbstractDeviceParser {

	public PortableMediaPlayerDeviceParser() {
		super("regexes/device/portable_media_player.yml");
	}

	public PortableMediaPlayerDeviceParser(ObjectMapper objectMapper) {
		super("regexes/device/portable_media_player.yml", objectMapper);
	}

}
