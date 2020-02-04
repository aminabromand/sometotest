package com.test.felix.bookshelf.service.tui.impl.activator;

import com.test.felix.bookshelf.service.tui.impl.BookshelfServiceProxyImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.Hashtable;

public class BookshelfTuiActivator implements BundleActivator
{
	public void start( BundleContext bc)
	{
		Hashtable props = new Hashtable();
		//props.put("osgi.command.scope", BookshelfServiceProxyImpl.SCOPE);
		//props.put("osgi.command.function", BookshelfServiceProxyImpl.FUNCTIONS);

		bc.registerService(
						BookshelfServiceProxyImpl.class.getName(),
						new BookshelfServiceProxyImpl(), props);
						//new BookshelfServiceProxyImpl(bc), props);
	}
	public void stop(BundleContext bc)
	{
	}
}