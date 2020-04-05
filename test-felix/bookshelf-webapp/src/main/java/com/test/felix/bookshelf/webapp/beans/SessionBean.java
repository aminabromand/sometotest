package com.test.felix.bookshelf.webapp.beans;

import com.test.felix.bookshelf.service.api.BookshelfService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import javax.servlet.ServletContext;

public class SessionBean{
	static final String OSGI_BUNDLECONTEXT = "osgi-bundlecontext";
	private BundleContext ctx;
	private String sessionId;

	public void initialize( ServletContext context ) {
		this.ctx = (BundleContext) context.getAttribute( OSGI_BUNDLECONTEXT );
	}

	public BookshelfService getBookshelf() {
		ServiceReference ref = ctx.getServiceReference(BookshelfService.class.getName());
		BookshelfService bookshelf = (BookshelfService) ctx.getService(ref);
		return bookshelf;
	}

	public boolean sessionIsValid() {
		return getBookshelf().sessionIsValid(getSessionId());
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/*public BundleContext getServletContext() {
		return ctx;
	}*/

	/*public int getServletContext() {
		return 2;
	}*/

}
