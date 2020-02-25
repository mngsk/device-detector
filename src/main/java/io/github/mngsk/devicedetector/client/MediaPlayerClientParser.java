package io.github.mngsk.devicedetector.client;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MediaPlayerClientParser extends AbstractClientParser<ClientRegex> {

	public MediaPlayerClientParser()
			throws JsonParseException, JsonMappingException, IOException {
		super("mediaplayer", "regexes/client/mediaplayers.yml");
	}

	public MediaPlayerClientParser(ObjectMapper objectMapper)
			throws JsonParseException, JsonMappingException, IOException {
		super("mediaplayer", "regexes/client/mediaplayers.yml", objectMapper);
	}

}
