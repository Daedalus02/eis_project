package org.project;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("API units suit testing.")
@SelectPackages("org.project")
@IncludeTags("API")

public class APITest {
}
