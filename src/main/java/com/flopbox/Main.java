package com.flopbox;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Main class.
 *
 */
public class Main {
	// Base URI the Grizzly HTTP server will listen on
	//
	//public static final String BASE_URI = "http://api.givemecoffee.ninja:8080/";
	static final String BASE_URI = "http://localhost:8080/";


	/**
	 * Main method.
	 *
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		final HttpServer server = startServer();
		System.out.println(String
				.format("Jersey app started with endpoints available at " + "%s%nHit Ctrl-C to stop it...", BASE_URI));
		System.in.read();
		server.stop();
	}

	/**
	 * Starts Grizzly HTTP server exposing JAX-RS resource defined in this
	 * application.
	 *
	 * @return Grizzly HTTP server.
	 */
	public static HttpServer startServer() {
		// create a resource config that scans for JAX-RS resource and providers
		// in com.flopbox package
		final ResourceConfig rc = new ResourceConfig().register(MultiPartFeature.class)
				.packages("com.flopbox.resource");

		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
	}
}
