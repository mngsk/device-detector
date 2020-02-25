package io.github.mngsk.devicedetector.client.bot;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.github.mngsk.devicedetector.client.AbstractClientParser;
import io.github.mngsk.devicedetector.client.Client;

public class BotParser extends AbstractClientParser<BotRegex> {

	public BotParser()
			throws JsonParseException, JsonMappingException, IOException {
		this(new ObjectMapper(new YAMLFactory()));
	}

	public BotParser(ObjectMapper objectMapper)
			throws JsonParseException, JsonMappingException, IOException {
		super("bot", "regexes/bots.yml", objectMapper);
	}

	@Override
	public Optional<Client> parse(String userAgent) {
		for (BotRegex regex : super.regexes) {
			Matcher matcher = regex.getPattern().matcher(userAgent);
			if (matcher.find()) {
				String name = regex.getName();
				String category = regex.getCategory();
				String url = regex.getUrl();
				BotProducer producer = regex.getProducer();

				return Optional.of(new Bot(name, category, url, producer));
			}
		}
		return Optional.empty();
	}

}
