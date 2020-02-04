package com.test.felix.bookshelf.log.impl;

import com.test.felix.bookshelf.log.api.BookshelfLogHelper;
import org.osgi.service.log.LogService;

import java.text.MessageFormat;

public class BookshelfLogHelperImpl implements BookshelfLogHelper{

	LogService log;

	@Override public void debug( String pattern, Object... args ){
		String message = MessageFormat.format(pattern, args);
		this.log.log(LogService.LOG_DEBUG, message);
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
}
