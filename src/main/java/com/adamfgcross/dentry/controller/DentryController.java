package com.adamfgcross.dentry.controller;

import java.util.List;

public interface DentryController {
	public List<String> getDataEntityResults(String input);
	public String createDataFromAnchors(String anchorFilePathAddress) throws AnchorFileIOException;
	public Boolean attachData(String anchor, String data);
}
