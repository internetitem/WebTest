package com.internetitem.test.web.requestInfo;

public class RequestInfo {
	private String remoteUser;
	private String remoteHost;

	public RequestInfo(String remoteUser, String remoteHost) {
		this.remoteUser = remoteUser;
		this.remoteHost = remoteHost;
	}

	public String getRemoteUser() {
		return remoteUser;
	}

	public String getRemoteHost() {
		return remoteHost;
	}

	@Override
	public String toString() {
		return "RequestInfo [remoteUser=" + remoteUser + ", remoteHost=" + remoteHost + "]";
	}

}
