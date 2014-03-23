package com.internetitem.test.web.jackson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.internetitem.test.web.jackson.DateFormat.FormatType;

public class CustomDateSerialization extends JsonSerializer<Date> implements ContextualSerializer {

	private String format;
	private FormatType formatType;

	public CustomDateSerialization() {
		this.formatType = FormatType.RFC8601_DATETIME;
	}

	public CustomDateSerialization(String format, FormatType formatType) {
		this.format = format;
		this.formatType = formatType;
	}

	@Override
	public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
		DateFormat ann = null;
		if (property != null) {
			ann = property.getAnnotation(DateFormat.class);
			if (ann == null) {
				ann = property.getContextAnnotation(DateFormat.class);
			}
		}
		if (ann != null) {
			return new CustomDateSerialization(ann.format(), ann.type());
		} else {
			return new CustomDateSerialization();
		}
	}

	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		FormatType ft = this.formatType;
		if (ft == null) {
			ft = FormatType.RFC8601_DATETIME;
		}

		switch (ft) {
		case RFC8601_DATETIME:
			jgen.writeString(DatatypeConverter.printDateTime(buildCalendar(value)));
			break;
		case RFC8601_DATE:
			jgen.writeString(DatatypeConverter.printDate(buildCalendar(value)));
			break;
		case CUSTOM:
			String format = this.format;
			if (format == null || format.equals("")) {
				format = DateFormat.DEFAULT_FORMAT;
			}
			jgen.writeString(new SimpleDateFormat(format).format(value));
			break;
		default:
			jgen.writeNumber(value.getTime());
		}
	}

	private Calendar buildCalendar(Date value) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(value);
		return cal;
	}

	@Override
	public Class<Date> handledType() {
		return Date.class;
	}

}
