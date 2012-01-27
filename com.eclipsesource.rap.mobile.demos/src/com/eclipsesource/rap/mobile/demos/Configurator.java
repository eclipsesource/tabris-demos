/*******************************************************************************
 * Copyright (c) 2012 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.rap.mobile.demos;

import org.eclipse.rwt.application.ApplicationConfiguration;
import org.eclipse.rwt.application.ApplicationConfiguration.OperationMode;
import org.eclipse.rwt.application.ApplicationConfigurator;
import org.eclipse.rwt.lifecycle.IEntryPoint;

import com.eclipsesource.rap.mobile.demos.entrypoints.ButtonControlsDemp;
import com.eclipsesource.rap.mobile.demos.entrypoints.InputControlsDemp;


public class Configurator implements ApplicationConfigurator {

  private static final String THEME_PATH_IOS = "theme/ios.css";
  private static final String THEME_PATH_ANDROID = "theme/theme-android-holo.css";

  public void configure( ApplicationConfiguration configuration ) {
    addApplication( configuration, "input", InputControlsDemp.class );
    addApplication( configuration, "buttons", ButtonControlsDemp.class );
  }

  public void addApplication( ApplicationConfiguration configuration, 
                              String id, 
                              Class<? extends IEntryPoint> type  ) 
  {
    configuration.setOperationMode( OperationMode.JEE_COMPATIBILITY );
    configuration.addEntryPoint( id, type );
    configuration.addStyleSheet( "org.eclipse.rap.rwt.theme.Default", THEME_PATH_ANDROID );
    configuration.addStyleSheet( "org.eclipse.rap.rwt.theme.ios", THEME_PATH_IOS );
    configuration.addBranding( new Branding( id + "-randy" ) );
    configuration.addBranding( new Branding( id + "-rios", "org.eclipse.rap.rwt.theme.ios" ) );
  }

}
