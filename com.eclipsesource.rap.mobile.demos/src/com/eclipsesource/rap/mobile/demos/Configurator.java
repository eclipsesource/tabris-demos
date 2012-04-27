/*******************************************************************************
 * Copyright (c) 2012 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.rap.mobile.demos;

import org.eclipse.rwt.application.ApplicationConfiguration;
import org.eclipse.rwt.application.ApplicationConfigurator;

import com.eclipsesource.rap.mobile.demos.entrypoints.ButtonControlsDemo;
import com.eclipsesource.rap.mobile.demos.entrypoints.DrawDemo;
import com.eclipsesource.rap.mobile.demos.entrypoints.GalleryDemo;
import com.eclipsesource.rap.mobile.demos.entrypoints.GeolocationDemo;
import com.eclipsesource.rap.mobile.demos.entrypoints.InputControlsDemo;
import com.eclipsesource.rap.mobile.demos.entrypoints.SimpleTreeDemo;
import com.eclipsesource.rap.mobile.demos.entrypoints.VirtualTreeDemo;

public class Configurator implements ApplicationConfigurator {

  public void configure( ApplicationConfiguration configuration ) {
    configuration.addEntryPoint( "/input", InputControlsDemo.class, null );
    configuration.addEntryPoint( "/buttons", ButtonControlsDemo.class, null );
    configuration.addEntryPoint( "/virtual-tree", VirtualTreeDemo.class, null );
    configuration.addEntryPoint( "/simple-tree", SimpleTreeDemo.class, null );
    configuration.addEntryPoint( "/gallery", GalleryDemo.class, null );
    configuration.addEntryPoint( "/location", GeolocationDemo.class, null );
    configuration.addEntryPoint( "/location", GeolocationDemo.class, null );
    configuration.addEntryPoint( "/draw", DrawDemo.class, null );
  }
}
