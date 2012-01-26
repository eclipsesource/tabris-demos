package com.eclipsesource.rap.mobile.demos;

import org.eclipse.rwt.application.ApplicationConfigurator;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

  private ServiceRegistration<?> registration;

	public void start(BundleContext bundleContext) throws Exception {
	  Configurator configurator = new Configurator();
	  registration = bundleContext.registerService( ApplicationConfigurator.class.getName(), 
	                                                configurator, 
	                                                null );
	}

	public void stop(BundleContext bundleContext) throws Exception {
		registration.unregister();
	}

}
