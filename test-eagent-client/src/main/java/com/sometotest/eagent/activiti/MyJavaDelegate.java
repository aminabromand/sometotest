package com.sometotest.eagent.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyJavaDelegate implements JavaDelegate{

	private final static Logger logger = LoggerFactory.getLogger( MyJavaDelegate.class );

	public void execute(DelegateExecution execution) {
		logger.info( "running process!" );
	}

}