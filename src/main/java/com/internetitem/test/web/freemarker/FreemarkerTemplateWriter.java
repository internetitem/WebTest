package com.internetitem.test.web.freemarker;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.MessageBodyWriter;

import freemarker.log.Logger;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

@Produces(MediaType.TEXT_HTML)
public class FreemarkerTemplateWriter implements MessageBodyWriter<Object> {

	private Configuration fmConfig;

	public FreemarkerTemplateWriter() {
		try {
			Logger.selectLoggerLibrary(Logger.LIBRARY_SLF4J);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Error setting up logging library: " + e.getMessage(), e);
		}
		fmConfig = new Configuration();
		fmConfig.setClassForTemplateLoading(FreemarkerTemplateWriter.class, "/templates");
		fmConfig.setObjectWrapper(new DefaultObjectWrapper());
		fmConfig.setDefaultEncoding("UTF-8");
		fmConfig.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		fmConfig.setIncompatibleImprovements(new Version(2, 3, 20));

	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		if (TemplateAware.class.isAssignableFrom(type)) {
			return true;
		}
		for (Annotation a : annotations) {
			if (a.annotationType() == TemplateName.class) {
				return true;
			}
		}
		return false;
	}

	@Override
	public long getSize(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
		String templateName = null;

		if (t instanceof TemplateAware) {
			templateName = ((TemplateAware) t).getTemplateName();
		} else {
			for (Annotation a : annotations) {
				if (a.annotationType() == TemplateName.class) {
					templateName = ((TemplateName) a).name();
					break;
				}
			}
		}

		if (templateName == null) {
			throw new NotFoundException("Template not defined");
		}

		Template template = fmConfig.getTemplate(templateName);
		try {
			template.process(t, new OutputStreamWriter(entityStream));
		} catch (TemplateException e) {
			throw new WebApplicationException("Internal error processing template :" + e.getMessage(), e, Status.INTERNAL_SERVER_ERROR);
		}
	}

}
