package com.sometotest.test.tplan_test.tplan;

import com.tplan.robot.scripting.DefaultJavaTestScript;
import com.tplan.robot.scripting.ScriptingContext;

public class PageObject extends DefaultJavaTestScript {
	protected Boolean prepared;

	public PageObject( ScriptingContext context ) {
		super();
		setContext( context );
	}

	public Boolean getPrepared () {
		return prepared;
	}


}
