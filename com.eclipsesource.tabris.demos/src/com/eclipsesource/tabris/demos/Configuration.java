/*******************************************************************************
 * Copyright (c) 2012, 2021 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;

import com.eclipsesource.tabris.TabrisClientInstaller;
import com.eclipsesource.tabris.demos.entrypoints.AppEventsDemo;
import com.eclipsesource.tabris.demos.entrypoints.AppLauncherDemo;
import com.eclipsesource.tabris.demos.entrypoints.ButtonControlsDemo;
import com.eclipsesource.tabris.demos.entrypoints.CameraDemo;
import com.eclipsesource.tabris.demos.entrypoints.ClientDeviceDemo;
import com.eclipsesource.tabris.demos.entrypoints.DrawDemo;
import com.eclipsesource.tabris.demos.entrypoints.GalleryDemo;
import com.eclipsesource.tabris.demos.entrypoints.GeolocationDemo;
import com.eclipsesource.tabris.demos.entrypoints.InputControlsDemo;
import com.eclipsesource.tabris.demos.entrypoints.KeyboardDemo;
import com.eclipsesource.tabris.demos.entrypoints.PdfDemo;
import com.eclipsesource.tabris.demos.entrypoints.ScrollDemo;
import com.eclipsesource.tabris.demos.entrypoints.SimpleTreeDemo;
import com.eclipsesource.tabris.demos.entrypoints.SwipeDemo;
import com.eclipsesource.tabris.demos.entrypoints.UiDemo;
import com.eclipsesource.tabris.demos.entrypoints.VideoDemo;
import com.eclipsesource.tabris.demos.entrypoints.VisualGuideDemo;
import com.eclipsesource.tabris.demos.ui.dynamic.DynamicUIDemo;

public class Configuration implements ApplicationConfiguration {

  @Override
  public void configure( Application application ) {
    bootstrapTabris( application );
    application.addEntryPoint( "/input", InputControlsDemo.class, null );
    application.addEntryPoint( "/buttons", ButtonControlsDemo.class, null );
//    application.addEntryPoint( "/virtual-tree", VirtualTreeDemo.class, null );
    application.addEntryPoint( "/simple-tree", SimpleTreeDemo.class, null );
    application.addEntryPoint( "/gallery", GalleryDemo.class, null );
    application.addEntryPoint( "/location", GeolocationDemo.class, null );
    application.addEntryPoint( "/draw", DrawDemo.class, null );
    application.addEntryPoint( "/camera", CameraDemo.class, null );
    application.addEntryPoint( "/keyboard", KeyboardDemo.class, null );
    application.addEntryPoint( "/video", VideoDemo.class, null );
    application.addEntryPoint( "/pdf", PdfDemo.class, null );
    application.addEntryPoint( "/launcher", AppLauncherDemo.class, null );
    application.addEntryPoint( "/appevents", AppEventsDemo.class, null );
    application.addEntryPoint( "/swipe", SwipeDemo.class, null );
    application.addEntryPoint( "/ui", new UiDemo(), null );
    application.addEntryPoint( "/dynamic-ui", new DynamicUIDemo(), null );
    application.addEntryPoint( "/device", ClientDeviceDemo.class, null );
    application.addEntryPoint( "/scroll", ScrollDemo.class, null );
    application.addEntryPoint( "/guide", VisualGuideDemo.class, null );
  }

  private void bootstrapTabris( Application application ) {
    TabrisClientInstaller.install( application );
  }
}
