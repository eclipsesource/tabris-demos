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
package com.eclipsesource.rap.mobile.demos.entrypoints;

import org.eclipse.rwt.graphics.Graphics;
import org.eclipse.rwt.lifecycle.IEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.eclipsesource.rap.mobile.geolocation.Geolocation;
import com.eclipsesource.rap.mobile.geolocation.GeolocationCallback;
import com.eclipsesource.rap.mobile.geolocation.GeolocationOptions;
import com.eclipsesource.rap.mobile.geolocation.Position;
import com.eclipsesource.rap.mobile.geolocation.PositionError;


public class GeolocationDemo implements IEntryPoint {

  private Label locationLabel;

  public int createUI() {
    Display display = new Display();
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setLayout( new GridLayout( 1, false ) );
    shell.setMaximized( true );
    Button getLocationButton = createGetLocationButton( shell );
    
    final Geolocation geolocation = new Geolocation();
    final GeolocationCallback callback = createCallback();
    getLocationButton.addSelectionListener( new SelectionAdapter() {
      public void widgetSelected( SelectionEvent e ) {
        geolocation.getCurrentPosition( callback, new GeolocationOptions().enableHighAccuracy() );
      }
    } );
    
    final Button watchButton = createStartWatchButton( shell );
    final Button stopWatchButton = createStopWatchButton( shell );
    
    watchButton.addSelectionListener( new SelectionAdapter() {
      public void widgetSelected( SelectionEvent e ) {
        geolocation.watchPosition( callback, 
                                   new GeolocationOptions().setFrequency( 1000 ).enableHighAccuracy() );
        stopWatchButton.setEnabled( true );
        watchButton.setEnabled( false );
      }
    } );
    stopWatchButton.addSelectionListener( new SelectionAdapter() {
      public void widgetSelected( SelectionEvent e ) {
        geolocation.clearWatch();
        watchButton.setEnabled( true );
        stopWatchButton.setEnabled( false );
      }
    } );
    
    createLocationLabel( shell );
    shell.open();
    return 0;
  }

  private GeolocationCallback createCallback() {
    final GeolocationCallback callback = new GeolocationCallback() {
      
      public void onSuccess( Position position ) {
        StringBuilder builder = new StringBuilder();
        builder.append( "Latitude: " + position.getCoords().getLatitude() + "\n" );
        builder.append( "Longitude: " + position.getCoords().getLongitude() + "\n" );
        builder.append( "Altitude: " + position.getCoords().getAltitude() + "\n" );
        builder.append( "Accuracy: " + position.getCoords().getAccuracy() + "\n" );
        builder.append( "Altitude Accuracy: " + position.getCoords().getAltitudeAccuracy() + "\n" );
        builder.append( "Speed: " + position.getCoords().getSpeed() + "\n" );
        builder.append( "Timestamp: " + position.getTimestamp() + "\n" );
        locationLabel.setText( builder.toString() );
      }
      
      public void onError( PositionError error ) {
        StringBuilder builder = new StringBuilder();
        builder.append( "An error occured: \n" );
        builder.append( "Code: " + error.getCode() );
        builder.append( "Message: " + error.getMessage() );
      }
    };
    return callback;
  }

  private Button createGetLocationButton( final Shell shell ) {
    Button getLocationButton = new Button( shell, SWT.PUSH );
    getLocationButton.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
    getLocationButton.setText( "Get Location Once" );
    getLocationButton.setBackground( Graphics.getColor( 60, 60, 60 ) );
    return getLocationButton;
  }

  private Button createStartWatchButton( final Shell shell ) {
    final Button watchButton = new Button( shell, SWT.PUSH );
    watchButton.setBackground( Graphics.getColor( 70, 124, 30 ) );
    watchButton.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
    watchButton.setText( "Start Watching");
    return watchButton;
  }

  private Button createStopWatchButton( final Shell shell ) {
    final Button stopWatchButton = new Button( shell, SWT.PUSH );
    stopWatchButton.setBackground( Graphics.getColor( 191, 37, 39 ) );
    stopWatchButton.setEnabled( false );
    stopWatchButton.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
    stopWatchButton.setText( "Stop Watching" );
    return stopWatchButton;
  }

  private void createLocationLabel( final Shell shell ) {
    locationLabel = new Label( shell, SWT.NONE );
    locationLabel.setForeground( Graphics.getColor( 255, 255, 255 ) );
    locationLabel.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
  }

}
