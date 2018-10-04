package org.activiti.activiti_test;

import org.activiti.engine.*;
import org.activiti.engine.form.FormData;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.form.DateFormType;
import org.activiti.engine.impl.form.LongFormType;
import org.activiti.engine.impl.form.StringFormType;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ActivitiBase{

	private final static Logger logger = LoggerFactory.getLogger( ActivitiBase.class );

	public final int VACATION_REQUEST = 1;
	public final int ONBOARDING = 2;

	private ProcessEngine processEngine;
	private RepositoryService repositoryService;

	private RuntimeService runtimeService;
	private ProcessInstance processInstance;

	private TaskService taskService;
	private List<Task> tasks;

	public void startEngine(){
		//		ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
		//						.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault()
						.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		//		processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine = cfg.buildProcessEngine();

		String pName = processEngine.getName();
		String ver = ProcessEngine.VERSION;
		logger.info( "ProcessEngine [" + pName + "] Version: [" + ver + "]" );
	}


	public void createDeployment( String processDefinitionKey, String xmlProcessFile, Boolean newVersion ){
		//		PROCESS DEFINITION
		repositoryService = processEngine.getRepositoryService();
		logger.info( "Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count() );

//		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
//		deploymentBuilder.enableDuplicateFiltering();
//		deploymentBuilder.addClasspathResource( xmlProcessFile );
//		Deployment deployment = deploymentBuilder.deploy();

		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
						.processDefinitionKey( processDefinitionKey ).latestVersion().singleResult();
		if ( processDefinition == null || newVersion ) {

			logger.info( "Deploying process definition..." );

			Deployment deployment = repositoryService.createDeployment().addClasspathResource( xmlProcessFile ).deploy();
			processDefinition = repositoryService.createProcessDefinitionQuery()
							.deploymentId(deployment.getId()).singleResult();

		}
		logger.info( "Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count() );

		if ( processDefinition.isSuspended() ) {
			logger.info( "process definition 'vacation request' suspended. Activating process definition..." );
			repositoryService.activateProcessDefinitionByKey( processDefinition.getKey() );
		}
		logger.info("Found process definition ["
										+ processDefinition.getName() + "] with id ["
										+ processDefinition.getId() + "] is suspended [" + processDefinition.isSuspended() + "]");
	}

	public void createProcessInstance( String instanceKey, Map<String, Object> variables ){

		runtimeService = processEngine.getRuntimeService();
		processInstance = runtimeService
						.startProcessInstanceByKey( instanceKey, variables );
		System.out.println("Onboarding process started with process instance id ["
						+ processInstance.getProcessInstanceId()
						+ "] key [" + processInstance.getProcessDefinitionKey() + "]");

		// Verify that we started a new process instance
		logger.info( "Number of process instances: " + runtimeService.createProcessInstanceQuery().count() );
	}


	public void testUserguideCode2( Scanner scanner ){
		taskService = processEngine.getTaskService();
		FormService formService = processEngine.getFormService();
		HistoryService historyService = processEngine.getHistoryService();
//		Scanner scanner = new Scanner(System.in);
		while (processInstance != null && !processInstance.isEnded()) {
			List<Task> tasks = taskService.createTaskQuery()
							.taskCandidateGroup("managers").list();
			System.out.println("Active outstanding tasks: [" + tasks.size() + "]");
			for (int i = 0; i < tasks.size(); i++) {
				Task task = tasks.get(i);
				System.out.println("Processing Task [" + task.getName() + "]");
				Map<String, Object> variables = new HashMap<String, Object>();
				FormData formData = formService.getTaskFormData(task.getId());
				for (FormProperty formProperty : formData.getFormProperties()) {
					if (StringFormType.class.isInstance(formProperty.getType())) {
						System.out.println(formProperty.getName() + "?");
						String value = scanner.nextLine();
						variables.put(formProperty.getId(), value);
					} else if (LongFormType.class.isInstance(formProperty.getType())) {
						System.out.println(formProperty.getName() + "? (Must be a whole number)");
						Long value = Long.valueOf(scanner.nextLine());
						variables.put(formProperty.getId(), value);
					} else if (DateFormType.class.isInstance(formProperty.getType())) {
						System.out.println(formProperty.getName() + "? (Must be a date m/d/yy)");
						DateFormat dateFormat = new SimpleDateFormat("m/d/yy");
						try {
							Date value = dateFormat.parse(scanner.nextLine());
							variables.put(formProperty.getId(), value);
						} catch ( ParseException ex ) {
							ex.printStackTrace();
						}
					} else {
						System.out.println("<form type not supported>");
					}
				}
				taskService.complete(task.getId(), variables);

				HistoricActivityInstance endActivity = null;
				List<HistoricActivityInstance> activities =
								historyService.createHistoricActivityInstanceQuery()
												.processInstanceId(processInstance.getId()).finished()
												.orderByHistoricActivityInstanceEndTime().asc()
												.list();
				for (HistoricActivityInstance activity : activities) {
					if (activity.getActivityType().equals( "startEvent")) {
						System.out.println("BEGIN " + processInstance.getName()
										+ " [" + processInstance.getProcessDefinitionKey()
										+ "] " + activity.getStartTime());
					}
					if (activity.getActivityType().equals( "endEvent")) {
						// Handle edge case where end step happens so fast that the end step
						// and previous step(s) are sorted the same. So, cache the end step
						//and display it last to represent the logical sequence.
						endActivity = activity;
					} else {
						System.out.println("-- " + activity.getActivityName()
										+ " ( " + activity.getActivityType() + " ) "
										+ " [" + activity.getActivityId() + "] "
										+ activity.getDurationInMillis() + " ms");
					}
				}
				if (endActivity != null) {
					System.out.println("-- " + endActivity.getActivityName()
									+ " [" + endActivity.getActivityId() + "] "
									+ endActivity.getDurationInMillis() + " ms");
					System.out.println("COMPLETE " + processInstance.getName() + " ["
									+ processInstance.getProcessDefinitionKey() + "] "
									+ endActivity.getEndTime());
				}
			}
			// Re-query the process instance, making sure the latest state is available
			processInstance = runtimeService.createProcessInstanceQuery()
							.processInstanceId(processInstance.getId()).singleResult();
		}
		scanner.close();
	}


	public void listTasksForManagement(){
		//		LIST TASKS FOR MANAGMENT
		// Fetch all tasks for the management group
		taskService = processEngine.getTaskService();
		tasks = taskService.createTaskQuery().taskCandidateGroup( "management" ).list();
		for(Task task : tasks){
			logger.info( "Task available: " + task.getName() );
		}
	}

	public void testUserguideCode( Map<String, Object> taskVariables ){
		Task task = tasks.get( 0 );
		taskService.complete( task.getId(), taskVariables );
	}

	public void suspendProcess(){

		//		SUSPEND THE PROCESS
		repositoryService.suspendProcessDefinitionByKey( "vacationRequest" );
		try{
			runtimeService.startProcessInstanceByKey( "vacationRequest" );
		} catch(ActivitiException e){
			e.printStackTrace();
		}
		logger.info( "Number of process instances: " + runtimeService.createProcessInstanceQuery().count() );

	}

	private void suspendAllVersions( String processDefinitionKey ) {
		List<ProcessDefinition> myList = repositoryService.createProcessDefinitionQuery()
						.processDefinitionKey( processDefinitionKey ).list();
		for (ProcessDefinition element : myList) {
			try{
				repositoryService.suspendProcessDefinitionById( element.getId() );
			} catch (ActivitiException ex) {
				logger.error( "Proccess definition " + element.getId() + " not suspended. Perhaps process definition was already suspended ?" );
			}
		}
	}

	private void activateAllVersions( String processDefinitionKey ) {
		List<ProcessDefinition> myList = repositoryService.createProcessDefinitionQuery()
						.processDefinitionKey( processDefinitionKey ).list();
		for (ProcessDefinition element : myList) {
			try{
				repositoryService.activateProcessDefinitionById( element.getId() );
			} catch (ActivitiException ex) {
				logger.error( "Proccess definition " + element.getId() + " not activated. Perhaps process definition was already activated ?" );
			}
		}
	}
}
