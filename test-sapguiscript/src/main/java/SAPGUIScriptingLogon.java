package com.sapguiscripting;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//-Begin----------------------------------------------------------------
//-
//- How to use SAP GUI Scripting inside Java
//- Example: Logon to an SAP system
//-
//-
//----------------------------------------------------------------------
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class SAPGUIScriptingLogon {



	public static void main(String[] args) {

		//-Variables------------------------------------------------------
		ActiveXComponent SAPROTWr, GUIApp, Connection, Session, Obj;
		Dispatch ROTEntry;
		Variant ScriptEngine;

		ComThread.InitSTA();


		String propertiesLocation = "/properties/sapproperties.properties";
		Properties sapProperties = new Properties();
		InputStream properties_input_stream = SAPGUIScriptingLogon.class.getResourceAsStream( propertiesLocation );
		try{
			sapProperties.load( properties_input_stream );

		} catch( IOException ioex ) {
			ioex.printStackTrace();
		}



		//Opening the SAP Logon
		String sapLogonPath = "C:\\Program Files (x86)\\SAP\\FrontEnd\\SAPgui\\saplogon.exe";
		sapLogonPath = sapProperties.getProperty( "sapLogonPath" );

		Process p;
		try {
			p = Runtime.getRuntime().exec(sapLogonPath);
			Thread.sleep(7000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//-Set SapGuiAuto = GetObject("SAPGUI")---------------------------
		SAPROTWr = new ActiveXComponent("SapROTWr.SapROTWrapper");
		try {
			ROTEntry = SAPROTWr.invoke("GetROTEntry", "SAPGUI").toDispatch();
			//-Set application = SapGuiAuto.GetScriptingEngine------------
			ScriptEngine = Dispatch.call(ROTEntry, "GetScriptingEngine");
			GUIApp = new ActiveXComponent(ScriptEngine.toDispatch());


			//SAP Connection Name
			String sapConnectionName = "SAPServer"; //this is the name of the connection in SAP Logon
			sapConnectionName = sapProperties.getProperty( "sapConnectionName" );

			Connection = new ActiveXComponent(
							GUIApp.invoke("OpenConnection",sapConnectionName).toDispatch());


			//-Set connection = application.Children(0)-------------------
			//Connection = new ActiveXComponent(GUIApp.invoke("Children", 0).toDispatch());

			//-Set session = connection.Children(0)-----------------------
			Session = new ActiveXComponent(
							Connection.invoke("Children", 0).toDispatch());

			//-Set GUITextField Client------------------------------------
			//-
			//- session.findById("wnd[0]/usr/txtRSYST-MANDT").text = "000"
			//-
			//------------------------------------------------------------
			String sapConnectionClient = sapProperties.getProperty( "sapConnectionClient" );

			Obj = new ActiveXComponent(Session.invoke("FindById","wnd[0]/usr/txtRSYST-MANDT").toDispatch());
			Obj.setProperty("Text", sapConnectionClient);

			//-Set GUITextField User--------------------------------------
			//-
			//- session.findById("wnd[0]/usr/txtRSYST-BNAME").text = _
			//-   "BCUSER"
			//-
			//------------------------------------------------------------
			String sapConnectionUser = sapProperties.getProperty( "sapConnectionUser" );

			Obj = new ActiveXComponent(Session.invoke("FindById",
							"wnd[0]/usr/txtRSYST-BNAME").toDispatch());
			Obj.setProperty("Text", sapConnectionUser);

			//-Set GUIPasswordField Password------------------------------
			//-
			//- session.findById("wnd[0]/usr/pwdRSYST-BCODE").text = _
			//-   "minisap"
			//-
			//------------------------------------------------------------
			String sapConnectionPassword = sapProperties.getProperty( "sapConnectionPassword" );

			Obj = new ActiveXComponent(Session.invoke("FindById",
							"wnd[0]/usr/pwdRSYST-BCODE").toDispatch());
			Obj.setProperty("Text", sapConnectionPassword);

			//-Set GUITextField Language----------------------------------
			//-
			//- session.findById("wnd[0]/usr/txtRSYST-LANGU").text = "DE"
			//-
			//------------------------------------------------------------
			String sapConnectionLanguage = sapProperties.getProperty( "sapConnectionLanguage" );

			Obj = new ActiveXComponent(Session.invoke("FindById",
							"wnd[0]/usr/txtRSYST-LANGU").toDispatch());
			Obj.setProperty("Text", sapConnectionLanguage);

			//-Press enter------------------------------------------------
			//-
			//- session.findById("wnd[0]").sendVKey 0
			//-
			//------------------------------------------------------------
			Obj = new ActiveXComponent(Session.invoke("FindById",
							"wnd[0]").toDispatch());
			Obj.invoke("sendVKey", 0);

		}

		catch (Exception e) {

		}

		finally {
			ComThread.Release();
			System.exit(0);
		}

	}

}

//-End------------------------------------------------------------------

