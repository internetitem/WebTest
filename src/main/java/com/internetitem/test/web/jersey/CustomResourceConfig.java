package com.internetitem.test.web.jersey;

import org.glassfish.jersey.server.ResourceConfig;

public class CustomResourceConfig extends ResourceConfig {

	public CustomResourceConfig() {
		packages("com.internetitem.test.web.services");
		register(ObjectMapperResolver.class);
	}
}
