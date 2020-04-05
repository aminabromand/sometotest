package com.test.felix.bookshelf.log.impl.activator;


import com.test.felix.bookshelf.log.api.BookshelfLogHelper;
import com.test.felix.bookshelf.log.impl.BookshelfLogHelperImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;


public class BookshelfLogHelperImplActivator implements BundleActivator{

	ServiceRegistration reg = null;

	@Override public void start( BundleContext bundleContext ) throws Exception{
		this.reg = bundleContext.registerService(
						BookshelfLogHelper.class.getName(),
						new BookshelfLogHelperImpl(), null);
						//new BookshelfLogHelperImpl(bundleContext), null);
	}



	@Override public void stop( BundleContext bundleContext ) throws Exception{
		if (this.reg!=null) {
			bundleContext.ungetService(reg.getReference());
		}
	}
}
