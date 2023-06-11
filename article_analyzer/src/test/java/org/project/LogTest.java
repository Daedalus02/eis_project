package org.project;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/*
* This test suite is used to test all the components related to the cases where the system does not
* retrieve article either from the API endpoint response or the CSV formatted file but get
* the articles for the tokenization directly from the previous research data.
* */
@Suite
@SuiteDisplayName("Log units suit testing.")
@SelectPackages("org.project")
@IncludeTags("Log")

public class LogTest {}
