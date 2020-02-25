package io.github.mngsk.devicedetector.device;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PortableMediaPlayerDeviceParser extends AbstractDeviceParser {

	public PortableMediaPlayerDeviceParser()
			throws JsonParseException, JsonMappingException, IOException {
		super("regexes/device/portable_media_player.yml");
	}

	public PortableMediaPlayerDeviceParser(ObjectMapper objectMapper)
			throws JsonParseException, JsonMappingException, IOException {
		super("regexes/device/portable_media_player.yml", objectMapper);
	}

}
