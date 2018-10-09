package com.sometotest.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.http.W3CHttpCommandCodec;
import org.openqa.selenium.remote.http.W3CHttpResponseCodec;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Collections;

public class Selenium2Test{

	public static RemoteWebDriver createDriverFromSession(final SessionId sessionId, URL command_executor){
		CommandExecutor executor = new HttpCommandExecutor(command_executor) {

			@Override
			public Response execute(Command command) throws IOException{
				Response response = null;
				if (command.getName() == "newSession") {
					response = new Response();
					response.setSessionId(sessionId.toString());
					response.setStatus(0);
					response.setValue(Collections.<String, String>emptyMap());

					try {
						Field commandCodec = null;
						commandCodec = this.getClass().getSuperclass().getDeclaredField("commandCodec");
						commandCodec.setAccessible(true);
						commandCodec.set(this, new W3CHttpCommandCodec());

						Field responseCodec = null;
						responseCodec = this.getClass().getSuperclass().getDeclaredField("responseCodec");
						responseCodec.setAccessible(true);
						responseCodec.set(this, new W3CHttpResponseCodec());
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}

				} else {
					response = super.execute(command);
				}
				return response;
			}
		};

		return new RemoteWebDriver(executor, new DesiredCapabilities());
	}

	public static void main(String [] args) {

		System.setProperty("webdriver.gecko.driver","/Users/amin/Documents/geckodriver/geckodriver");

		FirefoxOptions opts = new FirefoxOptions();
		opts.setCapability( "moz:webdriverClick", false );

		WebDriver driver = new FirefoxDriver( opts );

		//System.setProperty("webdriver.chrome.driver","/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");

		//ChromeDriver driver = new ChromeDriver();

		System.out.println("test");

		HttpCommandExecutor executor = (HttpCommandExecutor) ((FirefoxDriver)driver).getCommandExecutor();;
		URL url = executor.getAddressOfRemoteServer();
		SessionId session_id = ((FirefoxDriver)driver).getSessionId();


		RemoteWebDriver driver2 = createDriverFromSession(session_id, url);
		driver2.get("http://www.google.de");





	}


}
