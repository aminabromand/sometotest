package com.test.felix.bookshelf.log.impl;

import com.test.felix.bookshelf.log.api.BookshelfLogHelper;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

import java.text.MessageFormat;

public class BookshelfLogHelperImpl implements BookshelfLogHelper{


	//BundleContext bundleContext;

	//public BookshelfLogHelperImpl(BundleContext bundleContext) {
	//	this.bundleContext = bundleContext;
	//}

	//LoggerFactory loggerFactory;
	LogService log;

	@Override public void debug( String pattern, Object... args ){


		this.log.log(LogService.LOG_DEBUG, "hallo");

		String message = MessageFormat.format(pattern, args);

		//this.log.log(LogService.LOG_DEBUG, message);

		lookupLogService().log(LogService.LOG_DEBUG, message);
	}

	@Override public void debug( String pattern, Throwable throwable, Object... args ){

	}

	@Override public void info( String pattern, Object... args ){

	}

	@Override public void warn( String pattern, Object... args ){

	}

	@Override public void warn( String pattern, Throwable throwable, Object... args ){

	}

	@Override public void error( String pattern, Object... args ){

	}

	@Override public void error( String pattern, Throwable throwable, Object... args ){

	}

	private LogService lookupLogService() {

		return this.log;

		//String name = LogService.class.getName();
		//ServiceReference ref = this.bundleContext.getServiceReference(name);
		//if (ref == null)
		//{
		//	throw new RuntimeException(name);
		//}
		//return (LogService) this.bundleContext.getService(ref);

	}
}
