package com.internetitem.test.web.jersey;

import java.util.Date;

import javax.ws.rs.ext.ContextResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.internetitem.test.web.jackson.CustomDateDeserialization;
import com.internetitem.test.web.jackson.CustomDateSerialization;

public class ObjectMapperResolver implements ContextResolver<ObjectMapper> {

	private ObjectMapper om;

	public ObjectMapperResolver() {
		om = new ObjectMapper();
		SimpleModule dateModule = new SimpleModule();
		dateModule.addSerializer(new CustomDateSerialization());
		dateModule.addDeserializer(Date.class, new CustomDateDeserialization());
		om.registerModule(dateModule);
	}

	@Override
	public ObjectMapper getContext(Class<?> type) {
		return om;
	}

}
