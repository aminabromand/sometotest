package com.sometotest.eagent.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ButtonListener implements java.awt.event.ActionListener {

	private final static Logger logger = LoggerFactory.getLogger( ButtonListener.class );

	private AgentClientGUI myGUI;

	ButtonListener( AgentClientGUI myGUI ) {
		super();
		this.myGUI = myGUI;
	}

	public void actionPerformed ( java.awt.event.ActionEvent evt ) {

		logger.info( "button pressed" );

		switch( evt.getActionCommand() ) {
			case "Start Client":
				myGUI.start();
				break;
			case "Stop Client":
				myGUI.stop();
				break;
		}

	}
}
