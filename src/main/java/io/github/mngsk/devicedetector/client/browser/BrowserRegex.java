package io.github.mngsk.devicedetector.client.browser;

import io.github.mngsk.devicedetector.client.ClientRegex;

public class BrowserRegex extends ClientRegex {

	private BrowserEngine engine;

	public BrowserEngine getEngine() {
		return this.engine;
	}

	public void setEngine(BrowserEngine engine) {
		this.engine = engine;
	}

}
