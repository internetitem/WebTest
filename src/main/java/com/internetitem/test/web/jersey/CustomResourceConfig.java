package com.internetitem.test.web.jersey;

import javax.inject.Singleton;

import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider;

import com.internetitem.test.web.requestInfo.RemoteInfo;
import com.internetitem.test.web.requestInfo.RequestInfoFactoryProvider;
import com.internetitem.test.web.requestInfo.RequestInfoFactoryProvider.RemoteInfoInjectonResolver;

public class CustomResourceConfig extends ResourceConfig {

	public CustomResourceConfig() {
		packages("com.internetitem.test.web.services");
		register(ObjectMapperResolver.class);
		register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(RequestInfoFactoryProvider.class).to(ValueFactoryProvider.class).in(Singleton.class);
				bind(RemoteInfoInjectonResolver.class).to(new TypeLiteral<InjectionResolver<RemoteInfo>>() {
				}).in(Singleton.class);
			}
		});
	}
}
