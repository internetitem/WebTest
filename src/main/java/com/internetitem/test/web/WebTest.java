package com.internetitem.test.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.internetitem.test.web.jersey.CustomResourceConfig;

public class WebTest {

	private static final Logger logger = LoggerFactory.getLogger(WebTest.class);

	public static void main(String[] args) throws Exception {
		WebTestOptions options = WebTestOptions.parseOptions(args);
		setupLogging();

		int httpPort = options.getHttpPort();
		logger.info("Listening on port [" + httpPort + "]");

		Server server = new Server();
		ServerConnector http = new ServerConnector(server);
		http.setPort(httpPort);
		http.setReuseAddress(true);
		server.addConnector(http);

		ContextHandlerCollection chc = new ContextHandlerCollection();

		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setBaseResource(Resource.newClassPathResource("/static"));
		resourceHandler.setWelcomeFiles(new String[] { "index.html" });
		resourceHandler.setDirectoriesListed(true);

		ContextHandler resourcesHandler = new ContextHandler("/");
		resourcesHandler.setHandler(resourceHandler);

		chc.addHandler(resourcesHandler);

		ServletHolder servletHolder = new ServletHolder();
		servletHolder.setServlet(new ServletContainer(new CustomResourceConfig()));
		ServletContextHandler servicesHandler = new ServletContextHandler();
		servicesHandler.setContextPath("/services");
		servicesHandler.addServlet(servletHolder, "/*");
		chc.addHandler(servicesHandler);

		server.setHandler(chc);

		server.start();
		server.join();
	}

	private static void setupLogging() {
		// Needed for Jersey since it uses JUL
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
	}

}
