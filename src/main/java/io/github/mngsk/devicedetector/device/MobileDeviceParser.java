package io.github.mngsk.devicedetector.device;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MobileDeviceParser extends AbstractDeviceParser {

	public MobileDeviceParser()
			throws JsonParseException, JsonMappingException, IOException {
		super("regexes/device/mobiles.yml");
	}

	public MobileDeviceParser(ObjectMapper objectMapper)
			throws JsonParseException, JsonMappingException, IOException {
		super("regexes/device/mobiles.yml", objectMapper);
	}

}
