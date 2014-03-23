package com.internetitem.test.web.requestInfo;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.internal.inject.AbstractContainerRequestValueFactory;
import org.glassfish.jersey.server.internal.inject.AbstractValueFactoryProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.internal.inject.ParamInjectionResolver;
import org.glassfish.jersey.server.model.Parameter;

public class RequestInfoFactoryProvider extends AbstractValueFactoryProvider {

	@Inject
	private HttpServletRequest request;

	@Inject
	public RequestInfoFactoryProvider(MultivaluedParameterExtractorProvider mpep, ServiceLocator injector) {
		super(mpep, injector, Parameter.Source.UNKNOWN);
	}

	@Override
	protected Factory<?> createValueFactory(Parameter parameter) {
		Class<?> classType = parameter.getRawType();

		if (classType == null || (!classType.equals(RequestInfo.class))) {
			return null;
		}

		return new AbstractContainerRequestValueFactory<RequestInfo>() {
			@Override
			public RequestInfo provide() {
				return new RequestInfo(request.getRemoteUser(), request.getRemoteHost());
			}
		};
	}

	public static class RemoteInfoInjectonResolver extends ParamInjectionResolver<RemoteInfo> {

		public RemoteInfoInjectonResolver() {
			super(RequestInfoFactoryProvider.class);
		}

	}
}
