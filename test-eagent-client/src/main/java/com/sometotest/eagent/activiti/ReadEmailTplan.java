package com.sometotest.eagent.activiti;

import com.sometotest.eagent.tplan.TPlanTestScript;
import com.sometotest.eagent.tplan.base.TPlanScriptRunner;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;

public class ReadEmailTplan implements JavaDelegate{

	private final static Logger logger = LoggerFactory.getLogger( ReadEmailTplan.class );

	public void execute(DelegateExecution execution)
	{
		logger.info( "I do TPlan!" );

		try{

			TPlanTestScript my_script = new TPlanTestScript();
			//my_script.getScriptManager().
			TPlanScriptRunner my_runner = new TPlanScriptRunner();
			Thread running_script = my_runner.runScript( my_script );
			running_script.join();

			String email = my_script.getReturnValue();
			execution.setVariable("email", email);

		} catch (URISyntaxException ex){
			logger.info("Something went wrong.");
		} catch(InterruptedException e){
			e.printStackTrace();
		}

		logger.info( "I did TPlan!" );
	}

}