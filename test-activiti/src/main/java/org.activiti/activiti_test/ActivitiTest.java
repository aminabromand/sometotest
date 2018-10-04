package org.activiti.activiti_test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ActivitiTest{



	public void do_activiti(){
		ActivitiBase myBase = new ActivitiBase();

		myBase.startEngine();

		int processSelection;
		Boolean newVersion;
		String xmlProcessFile;
		String processKey;

		Scanner scanner = new Scanner(System.in);
		System.out.print( "Choose your process (1 = 'vacation request', 2 = 'onboarding'): " );
		processSelection = Integer.valueOf(scanner.nextLine());

		System.out.print( "New process definition (1 = 'yes', 0 = 'no') ?: " );
		newVersion = Boolean.valueOf( scanner.nextLine() );

		System.out.println( newVersion );


		if (processSelection == myBase.ONBOARDING) {

			processKey = "onboarding";
			xmlProcessFile = "org/activiti/test/onboarding.bpmn20.xml";

			myBase.createDeployment( processKey, xmlProcessFile, newVersion );
			myBase.createProcessInstance( processKey, null );

			myBase.testUserguideCode2( scanner );


		} else if (processSelection == myBase.VACATION_REQUEST) {

			processKey = "vacationRequest";
			xmlProcessFile = "org/activiti/test/VacationRequest.bpmn20.xml";

			myBase.createDeployment( processKey, xmlProcessFile, newVersion );

			String employeeName = "Kermit";
			String numberOfDays = "4";
			String vacationMotivation = "I'm really tired!";
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put( "employeeName", employeeName );
			variables.put( "numberOfDays", new Integer( numberOfDays ) );
			variables.put( "vacationMotivation", vacationMotivation );

			myBase.createProcessInstance( "vacationRequest", variables );

			myBase.listTasksForManagement();

			Map<String, Object> taskVariables = new HashMap<String, Object>();
			taskVariables.put( "vacationApproved", "false" );
			taskVariables.put( "managerMotivation", "We have a tight deadline!" );
			myBase.testUserguideCode( taskVariables );
			myBase.suspendProcess();
		}


		scanner.close();

	}
}