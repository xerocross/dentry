package com.adamfgcross.dentry.commands;

import org.springframework.stereotype.Component;

import com.adamfgcross.dentry.controller.AnchorFileIOException;
import com.adamfgcross.dentry.controller.DentryController;
import com.adamfgcross.dentry.controller.DentryControllerImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine;

@Component
@CommandLine.Command(name="", mixinStandardHelpOptions=true)
class DaddCommandImpl implements DaddCommand {
	@CommandLine.Option(names= {"-a", "--anchor"}, description="specify the path to the data anchor file")
	private String anchorFilePath;
	private static final Logger logger = LoggerFactory.getLogger(DaddCommandImpl.class);
	private DentryController dentryController;
	private DaddRepl daddRepl;
	
	public DaddCommandImpl(DentryController dentryController,
			DaddRepl daddRepl) {
		this.dentryController = dentryController;
		this.daddRepl = daddRepl;
	}
	
	@Override
	public void run() {
		try {
			dentryController.createDataFromAnchors(anchorFilePath);
			daddRepl.startDaddLoop();
		}
		catch (AnchorFileIOException e) {
			System.out.println("encountered an error while reading the anchor data file");
			logger.error("IO error", e);
		}
	}
}
