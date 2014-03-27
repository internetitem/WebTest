package com.internetitem.test.web.services;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.internetitem.test.web.dataModel.TestObject;
import com.internetitem.test.web.freemarker.TemplateName;
import com.internetitem.test.web.requestInfo.RemoteInfo;
import com.internetitem.test.web.requestInfo.RequestInfo;

@Path("/test")
public class TestService {

	@GET
	@Path("/testString")
	@Produces(MediaType.TEXT_PLAIN)
	public String testString() {
		return "This is a test";
	}

	@GET
	@Path("/testJson")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_HTML })
	@TemplateName(name = "/test.ftl")
	public TestObject testObject() {
		TestObject obj = new TestObject("Hello", 72, new Date());
		return obj;
	}

	@POST
	@Path("/testJson")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TestObject testObject(TestObject obj) {
		return new TestObject("Hello: string[" + obj.getString() + "], num[" + obj.getNumber() + "], date [" + obj.getDate() + "]", 0, null);
	}

	@GET
	@Path("/whoami")
	@Produces(MediaType.TEXT_PLAIN)
	public String whoAmI(@RemoteInfo RequestInfo requestInfo) {
		return "You are " + requestInfo;

	}
}
