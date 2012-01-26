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
import org.eclipse.rwt.application.ApplicationConfigurator;
import org.eclipse.rwt.lifecycle.IEntryPoint;

import com.eclipsesource.rap.mobile.demos.entrypoints.ButtonControlsDemp;
import com.eclipsesource.rap.mobile.demos.entrypoints.InputControlsDemp;


public class Configurator implements ApplicationConfigurator {

  private static final String THEME_PATH = "theme/ios.css";

  public void configure( ApplicationConfiguration configuration ) {
    addApplication( configuration, "input", InputControlsDemp.class );
    addApplication( configuration, "buttons", ButtonControlsDemp.class );
  }

  private void addApplication( ApplicationConfiguration configuration, 
                               String id, 
                               Class<? extends IEntryPoint> type ) 
  {
    configuration.addEntryPoint( id, type );
    configuration.addStyleSheet( id, THEME_PATH );
    configuration.addBranding( new Branding( id ) );
  }
}
