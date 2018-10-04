package com.sometotest.eagent.activiti;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

public class ActivitiBase{

	private final static Logger logger = LoggerFactory.getLogger( ActivitiBase.class );

	private ProcessEngine processEngine;

	public void startEngine(){

		String activitiConf = File.separator + "resources" + File.separator + "conf" + File.separator + "activiti.cfg.xml";
		activitiConf = File.separator + "conf" + File.separator + "activiti.cfg.xml";
		ProcessEngineConfiguration cfg = getProcessEngineConfiguration( activitiConf );
		//ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault()
		//				.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		//ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource( activitiConf )
		//				.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

		processEngine = cfg.buildProcessEngine();

		String pName = processEngine.getName();
		String ver = ProcessEngine.VERSION;
		logger.info( "ProcessEngine [" + pName + "] Version: [" + ver + "]" );

		String processKey = "writeEmailTest";
		String xmlProcessFile = "process/writeEmailTest.bpmn20.xml";
		createDeployment( processKey, xmlProcessFile, false );
	}

	public void createDeployment( String processDefinitionKey, String xmlProcessFile, Boolean newVersion ){

		//		PROCESS DEFINITION
		RepositoryService repositoryService = processEngine.getRepositoryService();
		logger.info( "Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count() );

		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
						.processDefinitionKey( processDefinitionKey ).latestVersion().singleResult();
		if ( processDefinition == null || newVersion ) {

			logger.info( "Deploying process definition..." );

			Deployment deployment = repositoryService.createDeployment().addClasspathResource( xmlProcessFile ).deploy();
			processDefinition = repositoryService.createProcessDefinitionQuery()
							.deploymentId(deployment.getId()).singleResult();

			logger.info( "New Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count() );
		}

		logger.info("Found process definition ["
						+ processDefinition.getName() + "] with id ["
						+ processDefinition.getId() + "] is suspended [" + processDefinition.isSuspended() + "]");
	}

	public void createProcessInstance( String instanceKey, Map<String, Object> variables ){
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey( instanceKey, variables );
	}

	public void stopEngine() {
		processEngine.close();
	}

	private ProcessEngineConfiguration getProcessEngineConfiguration( String propertiesLocation ) {

		try{
			File containing_file = new File( this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI() );
			String temp_out_file_location = containing_file.getParentFile().getPath() + File.separator + "resources" + File.separator + propertiesLocation;
			File temp_out_file = new File( temp_out_file_location );

			InputStream properties_input_stream;
			if ( temp_out_file.exists() ) {
				//InputStream properties_input_stream = new FileInputStream( new File( this.getClass().getResource( propertiesLocation ).getPath() ) );
				properties_input_stream = new FileInputStream( temp_out_file );
			} else {
				properties_input_stream = this.getClass().getResourceAsStream( propertiesLocation );
			}

			return ProcessEngineConfiguration.createProcessEngineConfigurationFromInputStream( properties_input_stream )
							.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);


		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
