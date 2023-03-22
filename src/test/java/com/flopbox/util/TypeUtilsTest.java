package com.flopbox.util;

import static org.junit.Assert.assertEquals;

import com.flopbox.app.util.tools.TypeUtils;
import org.junit.Test;

import jakarta.ws.rs.core.MediaType;

public class TypeUtilsTest {

	@Test
	public void get_type() {
		assertEquals(MediaType.TEXT_PLAIN_TYPE, TypeUtils.get_type("txt"));
		assertEquals(MediaType.TEXT_PLAIN_TYPE, TypeUtils.get_type("md"));
		assertEquals(MediaType.TEXT_PLAIN_TYPE, TypeUtils.get_type("js"));
		assertEquals(MediaType.TEXT_HTML_TYPE, TypeUtils.get_type("html"));
		assertEquals(MediaType.APPLICATION_JSON_TYPE, TypeUtils.get_type("json"));
		assertEquals(MediaType.APPLICATION_XML_TYPE, TypeUtils.get_type("xml"));
		assertEquals(MediaType.APPLICATION_OCTET_STREAM_TYPE, TypeUtils.get_type("png"));
		assertEquals(MediaType.APPLICATION_OCTET_STREAM_TYPE, TypeUtils.get_type("jpg"));
		assertEquals(MediaType.APPLICATION_OCTET_STREAM_TYPE, TypeUtils.get_type("jpeg"));
		assertEquals(MediaType.APPLICATION_OCTET_STREAM_TYPE, TypeUtils.get_type("gif"));
		assertEquals(MediaType.APPLICATION_OCTET_STREAM_TYPE, TypeUtils.get_type("pdf"));
	}

	@Test
	public void set_type() {
		assertEquals("text/plain", TypeUtils.set_type("test.txt"));
		assertEquals("text/plain", TypeUtils.set_type("test.md"));
		assertEquals("text/plain", TypeUtils.set_type("test.js"));
		assertEquals("text/html", TypeUtils.set_type("test.html"));
		assertEquals("application/json", TypeUtils.set_type("test.json"));
		assertEquals("application/xml", TypeUtils.set_type("test.xml"));
		assertEquals("application/octet-stream", TypeUtils.set_type("test.png"));
		assertEquals("application/octet-stream", TypeUtils.set_type("test.jpg"));
		assertEquals("application/octet-stream", TypeUtils.set_type("test.jpeg"));
		assertEquals("application/octet-stream", TypeUtils.set_type("test.gif"));
		assertEquals("application/octet-stream", TypeUtils.set_type("test.pdf"));
	}
}
