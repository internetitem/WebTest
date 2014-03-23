package com.internetitem.test.web;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class WebTestOptions {

	@Parameter(names = { "-p", "--port" }, description = "HTTP Port", arity = 1, required = true)
	private int httpPort;

	@Parameter(names = { "-?", "--help" }, description = "Display help", help = true)
	private boolean help;

	public int getHttpPort() {
		return httpPort;
	}

	public boolean isHelp() {
		return help;
	}
	
	static WebTestOptions parseOptions(String[] args) {
		WebTestOptions options = new WebTestOptions();
		JCommander cmdline = new JCommander(options);
		cmdline.setProgramName("WebTest");
		try {
			cmdline.parse(args);
			if (options.isHelp()) {
				cmdline.usage();
				System.exit(0);
			}
		} catch (Exception e) {
			System.err.println("Invalid parameters: " + e.getMessage());
			cmdline.usage();
			System.exit(1);
		}
		return options;
	}

}
