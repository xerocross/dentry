package com.adamfgcross.dentry.commands;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import picocli.CommandLine;
import picocli.CommandLine.IFactory;

@Component
public class CommandLineApp implements CommandLineRunner {

	private DaddCommand daddCommand;
	private IFactory factory;
	
	public CommandLineApp(IFactory factory, 
			DaddCommand daddCommand) {
		this.factory = factory;
		this.daddCommand = daddCommand;
	}
	
	@Override
	public void run(String... args) {
		CommandLine cmd = new CommandLine(daddCommand, factory);
		cmd.execute(args);
	}
}
