package com.eclipsesource.tabris.demos;

import org.eclipse.rwt.application.ApplicationConfiguration;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

  private ServiceRegistration<?> registration;

	public void start(BundleContext bundleContext) throws Exception {
	  Configuration configuration = new Configuration();
	  registration = bundleContext.registerService( ApplicationConfiguration.class.getName(), 
	                                                configuration, 
	                                                null );
	}

	public void stop(BundleContext bundleContext) throws Exception {
		registration.unregister();
	}

}
