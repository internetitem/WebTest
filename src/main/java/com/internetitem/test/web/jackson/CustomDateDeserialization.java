package com.internetitem.test.web.jackson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.internetitem.test.web.jackson.DateFormat.FormatType;

public class CustomDateDeserialization extends JsonDeserializer<Date> implements ContextualDeserializer {

	private String format;
	private FormatType formatType;

	public CustomDateDeserialization() {
		this.formatType = FormatType.RFC8601_DATETIME;
	}

	public CustomDateDeserialization(String format, FormatType formatType) {
		this.format = format;
		this.formatType = formatType;
	}

	@Override
	public Class<Date> handledType() {
		return Date.class;
	}

	@Override
	public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
		DateFormat ann = null;
		if (property != null) {
			ann = property.getAnnotation(DateFormat.class);
			if (ann == null) {
				ann = property.getContextAnnotation(DateFormat.class);
			}
		}
		if (ann != null) {
			return new CustomDateDeserialization(ann.format(), ann.type());
		} else {
			return new CustomDateDeserialization();
		}
	}

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		FormatType ft = this.formatType;
		if (ft == null) {
			ft = FormatType.RFC8601_DATETIME;
		}

		switch (ft) {
		case RFC8601_DATETIME:
			return DatatypeConverter.parseDateTime(jp.getText()).getTime();
		case RFC8601_DATE:
			return DatatypeConverter.parseDate(jp.getText()).getTime();
		case CUSTOM:
			String format = this.format;
			if (format == null || format.equals("")) {
				format = DateFormat.DEFAULT_FORMAT;
			}
			try {
				return new SimpleDateFormat(format).parse(jp.getText());
			} catch (ParseException e) {
				throw new JsonParseException("Error parsing date: " + e.getMessage(), ctxt.getParser().getCurrentLocation());
			}
		default:
			return new Date(jp.getLongValue());
		}
	}
}
