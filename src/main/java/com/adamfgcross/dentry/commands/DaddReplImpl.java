package com.adamfgcross.dentry.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.*;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.adamfgcross.dentry.controller.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DaddReplImpl implements DaddRepl {
	
	private DentryController dentryController;
	private static final Logger logger = LoggerFactory.getLogger(DaddReplImpl.class);
	
	@Value("${commands.EnterSearchTermMessage}")
	private String enterSearchTermMessage;
	
	@Value("${commands.SearchTermEndOption}")
	private String terminateMessage;
	
	@Value("${commands.SearchingMessage}")
	private String searchingMessage;
	
	@Value("${commands.EntriesFoundMessage}")
	private String entiresFoundMessage;
	
	@Value("${commands.NoEntriesMessage}")
	private String noEntriesMessage;
	
	public DaddReplImpl(DentryController dentryController) {
		this.dentryController = dentryController;
	}
	
	@Override
	public void startDaddLoop() {
		Scanner scanner = new Scanner(System.in);
		System.out.println(enterSearchTermMessage);
		System.out.println(terminateMessage);
		System.out.print(">");
		String userInput = scanner.nextLine();
		if (!userInput.equals("!")) {
			System.out.println(searchingMessage);
			System.out.println(entiresFoundMessage);
			List<String> anchors = dentryController.getDataEntityResults(userInput);
			printResults(anchors);
			while (anchors.size() != 1) {
				anchors = getEntity(anchors, scanner);
			}
			String anchor = anchors.get(0);
			System.out.println("found entity: " + anchor);
			String dataToAttach = null;
			while (dataToAttach == null) {
				dataToAttach = getDataToAttach(anchor, scanner);
			}
			if (dataToAttach.equals("")) {
				System.out.println("cancelling");
				startDaddLoop();
			} else {
				attachData(anchor, dataToAttach);
				startDaddLoop();
			}
		} else {
			System.out.println("goodbye");
		}
		scanner.close();
	}
	
	private void printResults(List<String> anchors) {
		if (anchors.isEmpty()) {
			System.out.println(noEntriesMessage);
		}
		anchors.forEach(anchor -> {
			System.out.println(anchor);
		});
	}
	
	private List<String> getEntity(List<String> anchors, Scanner in) {
		System.out.println("Please enter enough to uniquely identify a data entity. Note that if it is possible to uniquely identify an entity you will not receive this prompt again.");
		System.out.print(">");
		String input = in.nextLine();
		return anchors.stream().filter(anchor -> anchor.startsWith(input)).collect(Collectors.toList());
	}
	
	private String getDataToAttach(String anchor, Scanner in) {
		System.out.println("enter data to be attached");
		System.out.println("enter a blank line to cancel");
		System.out.print(">");
		String input = in.nextLine();
		if (input.equals("")) {
			return "";
		}
		System.out.println("attach data as follows? " + anchor + " <-> " + input);
		System.out.println("type Y or y to confirm");
		System.out.print(">");
		String confirmation = in.nextLine();
		if (confirmation.equals("Y") || confirmation.equals("y")) {
			return input;
		}
		else {
			System.out.println("canceled");
			return null;
		}
	}
	
	private Boolean attachData(String anchor, String data) {
		dentryController.attachData(anchor, data);
		logger.info("attached " + anchor + " <-> " + data);
		System.out.println("attached " + anchor + " <-> " + data);
		return true;
	}
}
