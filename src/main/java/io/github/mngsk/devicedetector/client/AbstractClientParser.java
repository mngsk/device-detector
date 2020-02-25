package io.github.mngsk.devicedetector.client;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.github.mngsk.devicedetector.util.AbstractParser;

public abstract class AbstractClientParser<T extends ClientRegex>
		extends AbstractParser<Client> {

	protected String type;
	protected List<T> regexes;

	public AbstractClientParser(String type, String fixtureFile)
			throws JsonParseException, JsonMappingException, IOException {
		this(type, fixtureFile, new ObjectMapper(new YAMLFactory()));
	}

	public AbstractClientParser(String type, String fixtureFile,
			ObjectMapper objectMapper)
			throws JsonParseException, JsonMappingException, IOException {
		this.type = type;

		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(fixtureFile);
		Class<?> regexClass = (Class<?>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		CollectionType listType = objectMapper.getTypeFactory()
				.constructCollectionType(List.class, regexClass);
		this.regexes = objectMapper.readValue(inputStream, listType);
	}

	@Override
	public Optional<Client> parse(String userAgent) {
		for (T regex : this.regexes) {
			Matcher matcher = regex.getPattern().matcher(userAgent);
			if (matcher.find()) {
				String name = regex.getName();

				String version = super.buildVersion(regex.getVersion(),
						matcher);

				return Optional.of(new Client(this.type, name, version));
			}
		}

		return Optional.empty();
	}

}
