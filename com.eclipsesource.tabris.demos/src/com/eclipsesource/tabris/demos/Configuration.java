/*******************************************************************************
 * Copyright (c) 2012 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.osgi.framework.FrameworkUtil;

import com.eclipsesource.tabris.Bootstrapper;
import com.eclipsesource.tabris.demos.entrypoints.AppEventsDemo;
import com.eclipsesource.tabris.demos.entrypoints.AppLauncherDemo;
import com.eclipsesource.tabris.demos.entrypoints.ButtonControlsDemo;
import com.eclipsesource.tabris.demos.entrypoints.CameraDemo;
import com.eclipsesource.tabris.demos.entrypoints.DrawDemo;
import com.eclipsesource.tabris.demos.entrypoints.GalleryDemo;
import com.eclipsesource.tabris.demos.entrypoints.GeolocationDemo;
import com.eclipsesource.tabris.demos.entrypoints.InputControlsDemo;
import com.eclipsesource.tabris.demos.entrypoints.KeyboardDemo;
import com.eclipsesource.tabris.demos.entrypoints.SimpleTreeDemo;
import com.eclipsesource.tabris.demos.entrypoints.VideoDemo;
import com.eclipsesource.tabris.demos.entrypoints.VirtualTreeDemo;

public class Configuration implements ApplicationConfiguration {

  public void configure( Application application ) {
    bootstrapTabris( application );
    application.addEntryPoint( "/input", InputControlsDemo.class, null );
    application.addEntryPoint( "/buttons", ButtonControlsDemo.class, null );
    application.addEntryPoint( "/virtual-tree", VirtualTreeDemo.class, null );
    application.addEntryPoint( "/simple-tree", SimpleTreeDemo.class, null );
    application.addEntryPoint( "/gallery", GalleryDemo.class, null );
    application.addEntryPoint( "/location", GeolocationDemo.class, null );
    application.addEntryPoint( "/draw", DrawDemo.class, null );
    application.addEntryPoint( "/camera", CameraDemo.class, null );
    application.addEntryPoint( "/keyboard", KeyboardDemo.class, null );
    application.addEntryPoint( "/video", VideoDemo.class, null );
    application.addEntryPoint( "/launcher", AppLauncherDemo.class, null );
    application.addEntryPoint( "/appevents", AppEventsDemo.class, null );
  }

  private void bootstrapTabris( Application application ) {
    Bootstrapper bootstrapper = new Bootstrapper( application );
    bootstrapper.bootstrap();
    bootstrapper.registerEntryPointLookup( FrameworkUtil.getBundle( Configuration.class ),
                                           "/index.json" );
  }
}
