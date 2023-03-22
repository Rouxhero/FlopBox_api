package com.flopbox.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.flopbox.app.util.web.WebResponse;
import org.junit.Test;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class WebResponseTest {
	@Test
	public void accessAttachmentNameTest() {
		WebResponse response = new WebResponse();
		response.setAttachmentName("name");
		assertEquals("name", response.getAttachmentName());
	}

	@Test
	public void accessContentTest() {
		WebResponse response = new WebResponse();
		response.setContent("content");
		assertEquals("content", response.getContent());
	}

	@Test
	public void accessStatusTest() {
		WebResponse response = new WebResponse();
		response.setStatus(Response.Status.BAD_REQUEST);
		assertEquals(Response.Status.BAD_REQUEST, response.getStatus());
	}

	@Test
	public void accessTypeTest() {
		WebResponse response = new WebResponse();
		response.setType(MediaType.TEXT_PLAIN);
		assertEquals(MediaType.TEXT_PLAIN, response.getType());
	}

	@Test
	public void buildResponseTest() {
		Response r = WebResponse.buildResponse(Response.Status.BAD_REQUEST, "content");
		assertEquals(Response.Status.BAD_REQUEST, r.getStatusInfo());
		assertEquals("content", r.getEntity());
	}

	@Test
	public void constructorTest() {
		WebResponse response = new WebResponse();
		assertEquals(MediaType.TEXT_PLAIN, response.getType());
		assertEquals("", response.getContent());
		assertEquals(Response.Status.OK, response.getStatus());
		assertNull(response.getAttachmentName());
	}

}
