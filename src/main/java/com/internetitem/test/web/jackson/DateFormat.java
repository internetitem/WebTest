package com.internetitem.test.web.jackson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DateFormat {
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd";

	FormatType type() default FormatType.RFC8601_DATETIME;

	String format() default DEFAULT_FORMAT;

	public enum FormatType {
		CUSTOM,
		NUMERIC,
		RFC8601_DATE,
		RFC8601_DATETIME
	}
}
