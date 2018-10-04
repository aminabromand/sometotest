package com.sometotest.eagent.activiti;

import com.sometotest.eagent.selenium.SeleniumTest;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteEmailSelenium implements JavaDelegate{

	private final static Logger logger = LoggerFactory.getLogger( WriteEmailSelenium.class );

	public void execute(DelegateExecution execution) {
		logger.info( "running process!" );
		SeleniumTest my_script = new SeleniumTest( (String)execution.getVariable( "selenium.geckodriver_path" ) );
		String email = (String)execution.getVariable( "email" );
		my_script.doGMX( email );
	}

}