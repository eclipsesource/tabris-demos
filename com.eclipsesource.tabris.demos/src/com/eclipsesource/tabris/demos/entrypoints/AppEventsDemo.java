/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.entrypoints;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.eclipsesource.tabris.app.App;
import com.eclipsesource.tabris.app.AppEvent;
import com.eclipsesource.tabris.app.AppListener;
import com.eclipsesource.tabris.app.EventType;
import com.eclipsesource.tabris.demos.hal.Hal9000;

public class AppEventsDemo implements EntryPoint {

  private Hal9000 hal;
  private long sleepMillis;

  public int createUI() {
    Display display = new Display();
    Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    shell.setBackground( display.getSystemColor( SWT.COLOR_BLACK ) );
    hal = new Hal9000( shell );
    registerAppStateListener();
    shell.open();
    return 0;
  }

  private void registerAppStateListener() {
    final App app = RWT.getClient().getService( App.class );
    if( app != null ) {
      app.addEventListener( EventType.PAUSE, new AppListener() {

        public void handleEvent( AppEvent event ) {
          System.out.println("Paused");
          Label output = hal.getText();
          output.setText( "I am going to sleep now.\n" );
          output.getParent().layout( true, true );
          sleepMillis = System.currentTimeMillis();
        }
      } );
      app.addEventListener( EventType.RESUME, new AppListener() {

        public void handleEvent( AppEvent event ) {
          System.out.println("Resumed");
          long slept = ( System.currentTimeMillis() - sleepMillis ) / 1000;
          Label output = hal.getText();
          output.setText( "Good morning Dave!\n\nThank for waking me up again. I was sleeping for "
                          + slept
                          + " seconds.\n" );
          output.getParent().layout( true, true );
        }
      } );
      app.addEventListener( EventType.INACTIVE, new AppListener() {

        public void handleEvent( AppEvent event ) {
          System.out.println("Inactive");
          Label output = hal.getText();
          output.setText( "Dave, did you fall asleep?\nYou haven't touched the screen for over 10 seconds.\n" );
          output.getParent().layout( true, true );
        }
      } );
      app.startInactivityTimer( 10000 );
      app.setScreenProtection( true );
    }
  }
}
