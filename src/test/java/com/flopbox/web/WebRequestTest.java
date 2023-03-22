package com.flopbox.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class WebRequestTest {

	@Test
	public void accessTest() {
		WebRequest request = new WebRequest();
		request.read();
		request.set("key", "value");
		assertEquals("value", request.get("key"));
		request.set("key2", "value2");
		assertEquals("value2", request.get("key2"));
		assertTrue(request.check(Arrays.asList("key", "key2")));
	}

	@Test(expected = NullPointerException.class)
	public void constructorErrorTest() {
		WebRequest request = new WebRequest();
		request.get("key");
	}

	@Test
	public void constructorTest() {
		WebRequest request = new WebRequest();
		request.read();
		assertNull(request.get("key"));
		request.set("key", "value");
		assertEquals("value", request.get("key"));
	}

}
