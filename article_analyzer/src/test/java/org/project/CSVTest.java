package org.project;


import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("CSV units suit testing.")
@SelectPackages("org.project")
@IncludeTags("CSV")

public class CSVTest {
}
