package io.github.mngsk.devicedetector.client;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FeedReaderClientParser extends AbstractClientParser<ClientRegex> {

	public FeedReaderClientParser()
			throws JsonParseException, JsonMappingException, IOException {
		super("feed reader", "regexes/client/feed_readers.yml");
	}

	public FeedReaderClientParser(ObjectMapper objectMapper)
			throws JsonParseException, JsonMappingException, IOException {
		super("feed reader", "regexes/client/feed_readers.yml", objectMapper);
	}

}
