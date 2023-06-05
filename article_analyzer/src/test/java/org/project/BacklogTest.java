package org.project;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Backlog units suit testing.")
@SelectPackages("org.project")
@IncludeTags("Backlog")

public class BacklogTest {}
