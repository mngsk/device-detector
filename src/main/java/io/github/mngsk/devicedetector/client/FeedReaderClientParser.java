package io.github.mngsk.devicedetector.client;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FeedReaderClientParser extends AbstractClientParser<ClientRegex> {

	public FeedReaderClientParser() {
		super("feed reader", "regexes/client/feed_readers.yml");
	}

	public FeedReaderClientParser(ObjectMapper objectMapper) {
		super("feed reader", "regexes/client/feed_readers.yml", objectMapper);
	}

}
