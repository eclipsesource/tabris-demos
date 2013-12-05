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
package com.eclipsesource.tabris.demos.entrypoints;

import static com.eclipsesource.tabris.widgets.enhancement.Widgets.onToolItem;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.rap.rwt.widgets.DialogUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.eclipsesource.tabris.geolocation.Geolocation;
import com.eclipsesource.tabris.geolocation.GeolocationAdapter;
import com.eclipsesource.tabris.geolocation.GeolocationListener;
import com.eclipsesource.tabris.geolocation.GeolocationOptions;
import com.eclipsesource.tabris.geolocation.Position;
import com.eclipsesource.tabris.geolocation.PositionError;
import com.eclipsesource.tabris.interaction.AppLauncher;
import com.eclipsesource.tabris.interaction.MapsOptions;


public class GeolocationDemo implements EntryPoint {

  private static final double SPRINGFIELD_LAT = 44.050953;
  private static final double SPRINGFIELD_LON = -123.016663;

  private Shell shell;
  private Geolocation geolocation;

  @Override
  public int createUI() {
    createShell();
    createTitle();
    geolocation = RWT.getClient().getService( Geolocation.class );
    createContent();
    shell.open();
    return 0;
  }

  private void createContent() {
    Composite container = new Composite( shell, SWT.NONE );
    container.setLayoutData( GridDataFactory.fillDefaults() .align( SWT.FILL, SWT.FILL ) .grab( true, false ) .create() );
    container.setLayout( GridLayoutFactory.fillDefaults().margins( 5, 5 ).create() );
    createGetLocationButton( container );
    createSpringfieldButton( container );
  }

  private void createShell() {
    Display display = new Display();
    shell = new Shell( display, SWT.NO_TRIM );
    shell.setLayout( GridLayoutFactory.fillDefaults().margins( 0, 0 ).spacing( 0, 0 ).create() );
    shell.setMaximized( true );
  }

  private void createTitle() {
    ToolBar toolBar = new ToolBar( shell, SWT.NONE);
    toolBar.setLayoutData( new GridData( SWT.FILL, SWT.TOP, true, false ) );
    ToolItem title = new ToolItem( toolBar, SWT.NONE );
    onToolItem( title ).useAsTitle();
    title.setText( "D'oh! Where am I?" );
  }

  private Button createGetLocationButton( final Composite container ) {
    Button getLocationButton = new Button( container, SWT.PUSH );
    getLocationButton.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
    getLocationButton.setText( "Open my position in maps" );
    getLocationButton.setBackground( new Color( getLocationButton.getDisplay(), 60, 60, 60 ) );
    getLocationButton.setForeground( new Color( getLocationButton.getDisplay(), 225, 255, 255 ) );
    final GeolocationListener listener = new GeolocationListener() {

      @Override
      public void positionReceived( Position position ) {
        openLocation( position.getCoords().getLatitude(), position.getCoords().getLongitude() );
        geolocation.removeGeolocationListener( this );
      }

      @Override
      public void errorReceived( PositionError error ) {
        openErrorDialog( "Error " + error.getCode(), error.getMessage() );
        geolocation.removeGeolocationListener( this );
      }
    };

    getLocationButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        geolocation.addGeolocationListener( listener );
        geolocation.determineCurrentPosition( new GeolocationOptions().enableHighAccuracy() );
      }
    } );
    return getLocationButton;
  }

  private Button createSpringfieldButton( final Composite container ) {
    Button button = new Button( container, SWT.PUSH );
    button.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.CENTER ).grab( true, false ).create() );
    button.setText( "Head me to Springfield" );
    button.setBackground( new Color( button.getDisplay(), 225, 151, 7 ) );
    button.setForeground( new Color( button.getDisplay(), 225, 255, 255 ) );
    final GeolocationAdapter listener = new GeolocationAdapter() {

      @Override
      public void positionReceived( Position position ) {
        geolocation.removeGeolocationListener( this );
        openLocation( SPRINGFIELD_LAT, SPRINGFIELD_LON );
      }

    };
    button.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        geolocation.addGeolocationListener( listener );
        geolocation.determineCurrentPosition( new GeolocationOptions().enableHighAccuracy() );
      }
    } );
    return button;
  }

  protected void openLocation( double latitude, double longitude ) {
    AppLauncher appLauncher = RWT.getClient().getService( AppLauncher.class );
    if( appLauncher != null ) {
      MapsOptions mapsOptions = new MapsOptions( latitude, longitude );
      appLauncher.open( mapsOptions );
    }
  }

  private void openErrorDialog( String title, String message ) {
    MessageBox box = new MessageBox( shell, SWT.ICON_ERROR );
    box.setText( title );
    box.setMessage( message );
    DialogUtil.open( box, null );
  }

}