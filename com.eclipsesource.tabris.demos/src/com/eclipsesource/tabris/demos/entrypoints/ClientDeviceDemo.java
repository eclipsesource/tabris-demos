/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.entrypoints;

import static com.eclipsesource.tabris.device.ClientDevice.Capability.CAMERA;
import static com.eclipsesource.tabris.device.ClientDevice.Capability.LOCATION;
import static com.eclipsesource.tabris.device.ClientDevice.Capability.MAPS;
import static com.eclipsesource.tabris.device.ClientDevice.Capability.MESSAGE;
import static com.eclipsesource.tabris.device.ClientDevice.Capability.PHONE;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.eclipsesource.tabris.app.App;
import com.eclipsesource.tabris.device.ClientDevice;
import com.eclipsesource.tabris.device.ClientDevice.Capability;
import com.eclipsesource.tabris.device.ClientDevice.ConnectionType;
import com.eclipsesource.tabris.device.ClientDevice.Orientation;
import com.eclipsesource.tabris.device.ClientDeviceAdapter;
import com.eclipsesource.tabris.widgets.ScrollingComposite;

public class ClientDeviceDemo implements EntryPoint {

  private static final String CHECK_MARK = "\u2713";

  @Override
  public int createUI() {
    Display display = new Display();
    Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    ClientDevice clientDevice = RWT.getClient().getService( ClientDevice.class );
    App app = RWT.getClient().getService( App.class );
    ScrollingComposite scrollingComposite = new ScrollingComposite( shell, SWT.V_SCROLL );
    scrollingComposite.setLayout( new FillLayout() );
    createContent( clientDevice, app, scrollingComposite );
    shell.open();
    return 0;
  }

  private void createContent( ClientDevice clientDevice, App app, Composite parent ) {
    Composite content = new Composite( parent, SWT.NONE );
    GridLayoutFactory.fillDefaults().spacing( 0, 0 ).applyTo( content );
    createAppGroup( content, app );
    createDeviceGroup( content, clientDevice );
    createDisplayGroup( content, clientDevice );
    createCapabilityGroup( content, clientDevice );
    createConnectionGroup( content, clientDevice );
    createTimeGroup( content, clientDevice );
  }

  private void createAppGroup( Composite parent, App app ) {
    Group group = createGroup( parent, "App" );
    if( app != null ) {
      createLabel( group, "Id" ).setText( stringForText( app.getId() ) );
      createLabel( group, "Version" ).setText( stringForText( app.getVersion() ) );
      createLabel( group, "Tabris Version" ).setText( stringForText( app.getTabrisVersion() ) );
    }
  }

  private void createDeviceGroup( Composite parent, ClientDevice clientDevice ) {
    Group group = createGroup( parent, "Device" );
    if( clientDevice != null ) {
      createLabel( group, "Model" ).setText( stringForText( clientDevice.getModel() ) );
      createLabel( group, "Vendor" ).setText( stringForText( clientDevice.getVendor() ) );
      createLabel( group, "OS Version" ).setText( stringForText( clientDevice.getOSVersion() ) );
      createLabel( group, "Locale" ).setText( stringForText( clientDevice.getLocale().toString() ) );
    }
  }

  private void createDisplayGroup( Composite parent, ClientDevice clientDevice ) {
    Group group = createGroup( parent, "Display" );
    final Label label = createLabel( group, "Orientation" );
    if( clientDevice != null ) {
      label.setText( clientDevice.getOrientation().toString() );
      clientDevice.addClientDeviceListener( new ClientDeviceAdapter() {
  
        @Override
        public void orientationChange( Orientation newOrientation ) {
          label.setText( newOrientation.toString() );
          label.pack();
        }
      } );
      String scaleFactor = Float.toString( clientDevice.getScaleFactor() );
      createLabel( group, "Scale Factor" ).setText( scaleFactor );
    }
  }

  private void createCapabilityGroup( Composite parent, ClientDevice clientDevice ) {
    Group group = createGroup( parent, "Capabilities" );
    createLabel( group, "Phone" ).setText( getHasCapabilityString( clientDevice, PHONE ) );
    createLabel( group, "Message" ).setText( getHasCapabilityString( clientDevice, MESSAGE ) );
    createLabel( group, "Location" ).setText( getHasCapabilityString( clientDevice, LOCATION ) );
    createLabel( group, "Camera" ).setText( getHasCapabilityString( clientDevice, CAMERA ) );
    createLabel( group, "Maps" ).setText( getHasCapabilityString( clientDevice, MAPS ) );
  }

  private void createConnectionGroup( Composite parent, ClientDevice clientDevice ) {
    Group group = createGroup( parent, "Connection" );
    final Label label = createLabel( group, "Type" );
    if( clientDevice != null ) {
      label.setText( clientDevice.getConnectionType().toString() );
      clientDevice.addClientDeviceListener( new ClientDeviceAdapter() {
  
        @Override
        public void connectionTypeChanged( ConnectionType newConnectionType ) {
          label.setText( newConnectionType.toString() );
          label.pack();
        }
      } );
    }
  }

  private void createTimeGroup( Composite parent, ClientDevice clientDevice ) {
    Group group = createGroup( parent, "Time" );
    int offset = 0;
    if( clientDevice != null ) {
      offset = clientDevice.getTimezoneOffset() * -1;
    }
    SimpleDateFormat formatter = new SimpleDateFormat();
    createLabel( group, "Server Time" ).setText( formatter.format( new Date() ) );
    formatter.setTimeZone( TimeZone.getTimeZone( TimeZone.getAvailableIDs( offset * 1000 * 60 )[ 0 ] ) );
    createLabel( group, "Client Time" ).setText( formatter.format( new Date() ) );
    createLabel( group, "Client UTC Offset" ).setText( offset + " Minutes" );
  }

  private String stringForText( String value ) {
    return ( value == null ) ? "" : value;
  }

  private String getHasCapabilityString( ClientDevice clientDevice, Capability capability ) {
    String result = "-";
    if( clientDevice != null && clientDevice.hasCapability( capability ) ) {
      result = CHECK_MARK;
    }
    return result;
  }

  private Group createGroup( Composite parent, String title ) {
    Group group = new Group( parent, SWT.NONE );
    GridLayoutFactory.fillDefaults().numColumns( 2 ).spacing( 12, 4 ).applyTo( group );
    GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).applyTo( group );
    group.setText( title );
    return group;
  }
  private Label createLabel( Composite parent, final String text ) {
    Label label = new Label( parent, SWT.NONE );
    label.setText( text );
    GridDataFactory.fillDefaults().align( SWT.LEFT, SWT.CENTER ).grab( false, true ).applyTo( label );
    Label resultLabel = new Label( parent, SWT.NONE );
    GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).applyTo( resultLabel );
    makeBold( resultLabel );
    return resultLabel;
  }

  private void makeBold( Label resultLabel ) {
    FontDescriptor fontDescriptor = FontDescriptor.createFrom( resultLabel.getFont() );
    Font font = fontDescriptor.setStyle( SWT.BOLD ).createFont( resultLabel.getDisplay() );
    resultLabel.setFont( font );
  }

}