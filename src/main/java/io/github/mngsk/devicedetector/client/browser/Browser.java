package io.github.mngsk.devicedetector.client.browser;

import java.util.Optional;

import io.github.mngsk.devicedetector.client.Client;

public class Browser extends Client {

	private final Engine engine;
	private final String family;

	public Browser(String name, String version, Engine engine, String family) {
		super("browser", name, version);
		this.engine = engine;
		this.family = family;
	}

	public Optional<Engine> getEngine() {
		return Optional.ofNullable(this.engine);
	}

	public Optional<String> getFamily() {
		return Optional.ofNullable(this.family);
	}

}
