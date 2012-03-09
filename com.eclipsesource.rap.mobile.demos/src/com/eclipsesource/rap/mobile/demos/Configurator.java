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
import com.eclipsesource.rap.mobile.demos.entrypoints.GalleryDemo;
import com.eclipsesource.rap.mobile.demos.entrypoints.InputControlsDemo;
import com.eclipsesource.rap.mobile.demos.entrypoints.SimpleTreeDemo;
import com.eclipsesource.rap.mobile.demos.entrypoints.VirtualTreeDemo;

public class Configurator implements ApplicationConfigurator {

  private static final String DEFAULT_THEME_ID = "org.eclipse.rap.rwt.theme.Default";
  private static final String IOS_THEME_ID = "org.eclipse.rap.rwt.theme.ios";
  private static final String THEME_PATH_IOS = "theme/ios.css";
  private static final String THEME_PATH_ANDROID = "theme/theme-android-holo.css";

  public void configure( ApplicationConfiguration configuration ) {
    setUp( configuration );
    configuration.addEntryPoint( "/android-input", InputControlsDemo.class );
    configuration.addEntryPoint( "/android-buttons", ButtonControlsDemo.class );
    configuration.addEntryPoint( "/android-virtual-tree", VirtualTreeDemo.class );
    configuration.addEntryPoint( "/android-simple-tree", SimpleTreeDemo.class );
    configuration.addEntryPoint( "/android-gallery", GalleryDemo.class );
    configuration.addEntryPoint( "/ios-input", InputControlsDemo.class );
    configuration.addEntryPoint( "/ios-buttons", ButtonControlsDemo.class );
    configuration.addEntryPoint( "/ios-virtual-tree", VirtualTreeDemo.class );
    configuration.addEntryPoint( "/ios-simple-tree", SimpleTreeDemo.class );
    configuration.addEntryPoint( "/ios-gallery", GalleryDemo.class );
  }

  private void setUp( ApplicationConfiguration configuration ) {
    configuration.addStyleSheet( DEFAULT_THEME_ID, THEME_PATH_ANDROID );
    configuration.addStyleSheet( IOS_THEME_ID, THEME_PATH_IOS );
    configuration.addBranding( new Branding( "ios-input", IOS_THEME_ID ) );
    configuration.addBranding( new Branding( "ios-buttons", IOS_THEME_ID ) );
    configuration.addBranding( new Branding( "ios-virtual-tree", IOS_THEME_ID ) );
    configuration.addBranding( new Branding( "ios-simple-tree", IOS_THEME_ID ) );
    configuration.addBranding( new Branding( "ios-gallery", IOS_THEME_ID ) );
  }
}
