/*******************************************************************************
 * Copyright (c) 2012 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.entrypoints;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.text.DecimalFormat;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.lifecycle.IEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eclipsesource.tabris.geolocation.Geolocation;
import com.eclipsesource.tabris.geolocation.GeolocationCallback;
import com.eclipsesource.tabris.geolocation.GeolocationOptions;
import com.eclipsesource.tabris.geolocation.Position;
import com.eclipsesource.tabris.geolocation.PositionError;

@SuppressWarnings("restriction")
public class GeolocationDemo implements IEntryPoint {

  private static final double SPRINGFIELD_LAT = 44.050953;
  private static final double SPRINGFIELD_LON = -123.016663;
  private Shell shell;
  private Geolocation geolocation;
  private Browser browser;
  private double lastLat;
  private double lastLon;
  private String lastLabel;

  public int createUI() {
    lastLat = 49.00612809217996;
    lastLon = 8.400545271791982;
    createShell();
    createTitle();
    createBrowser();
    geolocation = new Geolocation();
    Composite container = new Composite( shell, SWT.NONE );
    container.setLayoutData( GridDataFactory.fillDefaults()
      .align( SWT.FILL, SWT.FILL )
      .grab( true, false )
      .create() );
    container.setLayout( GridLayoutFactory.fillDefaults().margins( 0, 5 ).create() );
    createGetLocationButton( container );
    createSpringfieldButton( container );
    shell.open();
    return 0;
  }

  private void createShell() {
    Display display = new Display();
    shell = new Shell( display, SWT.NO_TRIM );
    shell.setLayout( GridLayoutFactory.fillDefaults().margins( 0, 0 ).spacing( 0, 0 ).create() );
    shell.setMaximized( true );
    shell.setBackground( new Color( display, 0, 0, 0 ) );
    shell.addControlListener( new ControlAdapter() {

      @Override
      public void controlResized( ControlEvent e ) {
        setBrowserUrl( lastLat, lastLon );
      }
    } );
  }

  private void createTitle() {
    ToolBar toolBar = new ToolBar( shell, SWT.FLAT );
    toolBar.setLayoutData( new GridData( SWT.FILL, SWT.TOP, true, false ) );
    ToolItem title = new ToolItem( toolBar, SWT.NONE );
    title.setData( RWT.CUSTOM_VARIANT, "TITLE" );
    title.setText( "D'oh! Where am I?" );
  }

  private void createBrowser() {
    browser = new Browser( shell, SWT.NONE );
    browser.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    setBrowserUrl( lastLat, lastLon );
  }

  private Button createGetLocationButton( final Composite container ) {
    Button getLocationButton = new Button( container, SWT.PUSH );
    getLocationButton.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
    getLocationButton.setText( "Where am I?" );
    getLocationButton.setBackground( new Color( getLocationButton.getDisplay(), 60, 60, 60 ) );
    getLocationButton.setForeground( new Color( getLocationButton.getDisplay(), 225, 255, 255 ) );
    getLocationButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        geolocation.getCurrentPosition( new GeolocationCallback() {

          public void onSuccess( Position position ) {
            lastLabel = "green_1";
            setBrowserUrl( position.getCoords().getLatitude(), position.getCoords().getLongitude() );
            String message = "You are in: \n" + getCity( position );
            openDialog( "Geolocation", message );
          }

          public void onError( PositionError error ) {
            StringBuilder builder = new StringBuilder();
            builder.append( "An error occured: \n" );
            builder.append( "Code: " + error.getCode() );
            builder.append( "Message: " + error.getMessage() );
            openDialog( "Error", builder.toString() );
          }
        },
                                        new GeolocationOptions().enableHighAccuracy() );
      }
    } );
    return getLocationButton;
  }

  private String getCity( Position position ) {
    StringBuilder builder = new StringBuilder();
    builder.append( "https://maps.googleapis.com/maps/api/geocode/json?latlng=" );
    builder.append( position.getCoords().getLatitude() );
    builder.append( "," );
    builder.append( position.getCoords().getLongitude() );
    builder.append( "&sensor=false" );
    try {
      URL url = new URL( builder.toString() );
      String json = readStream( url.openStream() );
      return processJson( json );
    } catch( Exception shouldNotHappen ) {
      throw new IllegalStateException( shouldNotHappen );
    }
  }

  private String processJson( String json ) throws JSONException {
    JSONObject object = new JSONObject( json );
    JSONArray resultArray = object.getJSONArray( "results" );
    for( int i = 0; i < resultArray.length(); i++ ) {
      JSONObject result = resultArray.getJSONObject( i );
      JSONArray addressComponents = result.getJSONArray( "address_components" );
      for( int j = 0; j < addressComponents.length(); j++ ) {
        JSONObject component = addressComponents.getJSONObject( j );
        String type = component.getJSONArray( "types" ).getString( 0 );
        if( type.equals( "locality" ) ) {
          return component.getString( "long_name" );
        }
      }
    }
    return "not found";
  }

  protected String readStream( InputStream stream ) {
    final char[] buffer = new char[ 0x10000 ];
    StringBuilder out = new StringBuilder();
    Reader in;
    try {
      in = new InputStreamReader( stream, "UTF-8" );
      int read;
      do {
        read = in.read( buffer, 0, buffer.length );
        if( read > 0 ) {
          out.append( buffer, 0, read );
        }
      } while( read >= 0 );
      return out.toString();
    } catch( Exception shouldNotHappen ) {
      throw new IllegalStateException( shouldNotHappen );
    }
  }

  private Button createSpringfieldButton( final Composite container ) {
    Button button = new Button( container, SWT.PUSH );
    button.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
    button.setText( "Head me to Springfield" );
    button.setBackground( new Color( button.getDisplay(), 225, 151, 7 ) );
    button.setForeground( new Color( button.getDisplay(), 225, 255, 255 ) );
    button.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        geolocation.getCurrentPosition( new GeolocationCallback() {

          public void onSuccess( Position position ) {
            lastLabel = "yellow_1";
            setBrowserUrl( SPRINGFIELD_LAT, SPRINGFIELD_LON );
            double distance = distFrom( position.getCoords().getLatitude(), position.getCoords()
              .getLongitude(), SPRINGFIELD_LAT, SPRINGFIELD_LON );
            DecimalFormat format = new DecimalFormat( "#0.00" );
            openDialog( "Springfield Locator", "Distance to Moe's: \n"
                                               + format.format( distance )
                                               + " km" );
          }

          public void onError( PositionError error ) {
          }
        },
                                        new GeolocationOptions().enableHighAccuracy() );
      }
    } );
    return button;
  }

  private double distFrom( double lat1, double lon1, double lat2, double lon2 ) {
    double d2r = Math.PI / 180;
    double dlong = ( lon2 - lon1 ) * d2r;
    double dlat = ( lat2 - lat1 ) * d2r;
    double a = Math.pow( Math.sin( dlat / 2.0 ), 2 )
               + Math.cos( lat1 * d2r )
               * Math.cos( lat2 * d2r )
               * Math.pow( Math.sin( dlong / 2.0 ), 2 );
    double c = 2 * Math.atan2( Math.sqrt( a ), Math.sqrt( 1 - a ) );
    return 6367 * c;
  }

  private void openDialog( String title, String message ) {
    final Shell box = new Shell( shell, SWT.APPLICATION_MODAL | SWT.BORDER | SWT.TITLE );
    box.setText( title );
    GridLayout layout = new GridLayout();
    layout.marginWidth = 50;
    box.setLayout( layout );
    box.setBackground( new Color( box.getDisplay(), 0, 0, 0 ) );
    Label label = new Label( box, SWT.NONE );
    label.setText( message );
    label.setLayoutData( GridDataFactory.fillDefaults()
      .align( SWT.CENTER, SWT.CENTER )
      .grab( true, true )
      .create() );
    Button close = new Button( box, SWT.PUSH );
    close.setBackground( new Color( box.getDisplay(), 225, 151, 7 ) );
    close.setText( "Okily dokily!" );
    close.setLayoutData( GridDataFactory.fillDefaults()
      .align( SWT.FILL, SWT.CENTER )
      .grab( true, false )
      .create() );
    close.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        box.close();
      }
    } );
    box.pack();
    box.addControlListener( new ControlAdapter() {

      @Override
      public void controlResized( ControlEvent e ) {
        centerDialog( box );
      }
    } );
    centerDialog( box );
    box.open();
  }

  private void centerDialog( Shell box ) {
    int newX = ( shell.getSize().x - box.getSize().x ) / 2;
    int newY = ( shell.getSize().y - box.getSize().y ) / 2;
    box.setLocation( newX, newY );
  }

  private void setBrowserUrl( double lat, double lon ) {
    StringBuilder builder = new StringBuilder();
    builder.append( "http://open.mapquestapi.com/staticmap/v4/getmap" );
    builder.append( "?size=" + shell.getSize().x + "," + shell.getSize().y );
    builder.append( "&zoom=16" );
    lastLat = lat;
    lastLon = lon;
    builder.append( "&center=" + lat + "," + lon );
    builder.append( "&imageType=png" );
    if( lastLabel != null ) {
      builder.append( "&pois=" + lastLabel + "," + lat + "," + lon + ",0,0" );
    }
    browser.setUrl( builder.toString() );
  }
}