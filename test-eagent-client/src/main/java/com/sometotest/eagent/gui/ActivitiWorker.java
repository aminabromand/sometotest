package com.sometotest.eagent.gui;

import com.sometotest.eagent.activiti.ActivitiBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;


public class ActivitiWorker extends SwingWorker<Integer, String>{

	private final static Logger logger = LoggerFactory.getLogger( ButtonListener.class );

	private ActivitiBase activitiBase;

	ActivitiWorker( ActivitiBase activitiBase )
	{
		this.activitiBase = activitiBase;
	}

	protected Integer doInBackground() throws Exception {
		try {
			logger.info( "starting activiti engine" );
			activitiBase.startEngine();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info( "stopping activiti engine" );
			activitiBase.stopEngine();
		}
		return 0;
	}
}