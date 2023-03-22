package com.flopbox.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Base64;

import org.apache.commons.net.ftp.FTPFile;
import org.junit.Before;
import org.junit.Test;

import com.flopbox.web.WebRequest;
import com.flopbox.web.WebResponse;

public class ServerCacheTest {

	private final String root = "/directory/of/my/file";
	private final String filename = "file.md";
	private final String CONTENT = "# Ma chaine de caractères, qui est un super contenu";
	private WebRequest request;
	private FTPFile file;

	private ByteArrayOutputStream generateContent() throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(outputStream);
		writer.write(CONTENT);
		writer.flush();
		writer.close();
		return outputStream;
	}

	private String generateUniqueID(String root, FTPFile file) {
		String path = root + "/" + file.getName();
		path = path.replace("//", "/");
		return Base64.getEncoder().encodeToString(path.getBytes());
	}

	@Before
	public void setUp() throws Exception {
		file = new FTPFile();
		file.setName(filename);
		String fid = generateUniqueID(root, file);
		request = new WebRequest();
		request.read();
		request.set("fid", fid);
	}

	@Test
	public void testContent() throws IOException, ClassNotFoundException {
		ServerCache.addToCache(request, generateContent());
		assertTrue(ServerCache.isCached(request));
		WebResponse response = new WebResponse();
		ServerCache.getFromCache(request, response);
		assertEquals(CONTENT, new String((byte[]) response.getContent()));
	}

	@Test
	public void testGet() throws IOException, ClassNotFoundException {
		ServerCache.addToCache(request, new ByteArrayOutputStream());
		assertTrue(ServerCache.isCached(request));
		WebResponse response = new WebResponse();
		ServerCache.getFromCache(request, response);
		assertEquals(root + "/" + filename, response.getAttachmentName());
	}

	@Test
	public void testPut() throws IOException {
		ServerCache.addToCache(request, new ByteArrayOutputStream());
		assertTrue(ServerCache.isCached(request));
	}
}
