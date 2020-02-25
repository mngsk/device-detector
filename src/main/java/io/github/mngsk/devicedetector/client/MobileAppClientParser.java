package io.github.mngsk.devicedetector.client;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MobileAppClientParser extends AbstractClientParser<ClientRegex> {

	public MobileAppClientParser()
			throws JsonParseException, JsonMappingException, IOException {
		super("mobile app", "regexes/client/mobile_apps.yml");
	}

	public MobileAppClientParser(ObjectMapper objectMapper)
			throws JsonParseException, JsonMappingException, IOException {
		super("mobile app", "regexes/client/mobile_apps.yml", objectMapper);
	}

}
