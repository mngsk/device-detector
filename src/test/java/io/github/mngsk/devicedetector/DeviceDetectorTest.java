package io.github.mngsk.devicedetector;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.mngsk.devicedetector.DeviceDetector.DeviceDetectorBuilder;

public class DeviceDetectorTest {

	private static DeviceDetector DD;

	@BeforeAll
	public static void beforeAll() {
		DD = new DeviceDetectorBuilder().build();
	}

	@Test
	public void nonNullDetector() {
		assertNotNull(DD);
	}

	@Test
	public void nullUserAgent() {
		assertThrows(IllegalArgumentException.class, () -> DD.detect(null));
	}

	@Test
	public void emptyUserAgent() {
		assertThrows(IllegalArgumentException.class, () -> DD.detect(""));
	}

	@Test
	public void nonNullUserAgent() {
		Detection d = DD.detect("12345");

		assertNotNull(d);
	}

}
