package io.github.mngsk.devicedetector.client.bot;

import java.util.Optional;

import io.github.mngsk.devicedetector.client.Client;

public class Bot extends Client {

	private final String category;
	private final String url;
	private final BotProducer producer;

	public Bot(String name, String category, String url, BotProducer producer) {
		super("bot", name, null);
		this.category = category;
		this.url = url;
		this.producer = producer;
	}

	public Optional<String> getCategory() {
		return Optional.ofNullable(this.category);
	}

	public Optional<String> getUrl() {
		return Optional.ofNullable(this.url);
	}

	public Optional<BotProducer> getProducer() {
		return Optional.ofNullable(this.producer);
	}

}
