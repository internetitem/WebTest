package com.internetitem.test.web.dataModel;

import java.util.Date;

import com.internetitem.test.web.jackson.DateFormat;
import com.internetitem.test.web.jackson.DateFormat.FormatType;

public class TestObject {

	private String string;
	private int number;

	@DateFormat(type = FormatType.RFC8601_DATETIME)
	private Date date;

	@DateFormat(type = FormatType.NUMERIC)
	private Date dateAsNumber;

	@DateFormat(type = FormatType.CUSTOM)
	private Date dateAsCustom;

	public TestObject() {
	}

	public TestObject(String string, int number, Date date) {
		this.string = string;
		this.number = number;
		this.date = date;
		this.dateAsNumber = date;
		this.dateAsCustom = date;
	}

	public String getString() {
		return string;
	}

	public int getNumber() {
		return number;
	}

	public Date getDate() {
		return date;
	}

	public Date getDateAsNumber() {
		return dateAsNumber;
	}

	public Date getDateAsCustom() {
		return dateAsCustom;
	}

}
