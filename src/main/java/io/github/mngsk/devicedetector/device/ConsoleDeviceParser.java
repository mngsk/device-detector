package io.github.mngsk.devicedetector.device;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConsoleDeviceParser extends AbstractDeviceParser {

	public ConsoleDeviceParser()
			throws JsonParseException, JsonMappingException, IOException {
		super("regexes/device/consoles.yml");
	}

	public ConsoleDeviceParser(ObjectMapper objectMapper)
			throws JsonParseException, JsonMappingException, IOException {
		super("regexes/device/consoles.yml", objectMapper);
	}

}
