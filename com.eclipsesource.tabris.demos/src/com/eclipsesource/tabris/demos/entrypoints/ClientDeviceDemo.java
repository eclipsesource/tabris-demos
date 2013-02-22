/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.entrypoints;

import static com.eclipsesource.tabris.ClientDevice.Capability.CAMERA;
import static com.eclipsesource.tabris.ClientDevice.Capability.LOCATION;
import static com.eclipsesource.tabris.ClientDevice.Capability.MAPS;
import static com.eclipsesource.tabris.ClientDevice.Capability.MESSAGE;
import static com.eclipsesource.tabris.ClientDevice.Capability.PHONE;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.eclipsesource.tabris.ClientDevice;
import com.eclipsesource.tabris.ClientDeviceAdapter;
import com.eclipsesource.tabris.ClientDevice.Capability;
import com.eclipsesource.tabris.ClientDevice.ConnectionType;
import com.eclipsesource.tabris.ClientDevice.Orientation;

public class ClientDeviceDemo implements EntryPoint {

  public int createUI() {
    final Display display = new Display();
    Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    ClientDevice clientDevice = RWT.getClient().getService( ClientDevice.class );
    ScrolledComposite scrolledComposite = createScrolledComposite( shell );
    Composite container = new Composite( scrolledComposite, SWT.NONE );
    addResizeListener( scrolledComposite, container );
    container.setLayout( GridLayoutFactory.fillDefaults().margins( 0, 0 ).spacing( 0, 0 ).equalWidth( false ).create() );
    createContent( clientDevice, scrolledComposite, container );
    shell.layout( true, true );
    shell.open();
    return 0;
  }

  private void createContent( ClientDevice clientDevice, ScrolledComposite scrolledComposite, Composite container ) {
    createDateTimeGroup( container, clientDevice );
    createCapabilityGroup( container, clientDevice );
    createOrientationGroup( container, clientDevice );
    createConnectionTypeGroup( container, clientDevice );
    scrolledComposite.setContent( container );
  }

  private ScrolledComposite createScrolledComposite( Composite parent ) {
    final ScrolledComposite scrolledComposite = new ScrolledComposite( parent, SWT.V_SCROLL );
    scrolledComposite.setExpandHorizontal( true );
    scrolledComposite.setExpandVertical( true );
    return scrolledComposite;
  }

  private void createDateTimeGroup( Composite parent, ClientDevice clientDevice ) {
    Group group = createGroup( parent, "Timezone Offset" );
    int timezoneOffset = clientDevice.getTimezoneOffset();
    SimpleDateFormat formatter = new SimpleDateFormat();
    createLabel( group, "Server Time" ).setText( formatter.format( new Date() ) );
    formatter.setTimeZone( TimeZone.getTimeZone( TimeZone.getAvailableIDs( timezoneOffset * 1000 * 60 )[ 0 ] ) );
    createLabel( group, "Client Time" ).setText( formatter.format( new Date() ) );
    createLabel( group, "Client UTC Offset" ).setText( timezoneOffset + " Minutes" );
  }

  private void createCapabilityGroup( Composite parent, ClientDevice clientDevice ) {
    Group group = createGroup( parent, "Capabilities" );
    createLabel( group, "Phone" ).setText( getHasCapabilityString( clientDevice, PHONE ) );
    createLabel( group, "Message" ).setText( getHasCapabilityString( clientDevice, MESSAGE ) );
    createLabel( group, "Location" ).setText( getHasCapabilityString( clientDevice, LOCATION ) );
    createLabel( group, "Camera" ).setText( getHasCapabilityString( clientDevice, CAMERA ) );
    createLabel( group, "Maps" ).setText( getHasCapabilityString( clientDevice, MAPS ) );
  }

  private String getHasCapabilityString( ClientDevice clientDevice, Capability capability ) {
    return clientDevice.hasCapability( capability ) ? "✓" : "-";
  }

  private void createConnectionTypeGroup( Composite parent, ClientDevice clientDevice ) {
    Group group = createGroup( parent, "Connection Type" );
    final Label label = createLabel( group, "Type" );
    label.setText( clientDevice.getConnectionType().toString() );
    clientDevice.addClientDeviceListener( new ClientDeviceAdapter() {
      @Override
      public void connectionTypeChanged( ConnectionType newConnectionType ) {
        label.setText( newConnectionType.toString() );
        label.pack();
      }
    } );

  }

  private void createOrientationGroup( Composite parent, ClientDevice clientDevice ) {
    Group group = createGroup( parent, "Device Orientation" );
    final Label label = createLabel( group, "Orientation" );
    label.setText( clientDevice.getOrientation().toString() );
    clientDevice.addClientDeviceListener( new ClientDeviceAdapter() {
      @Override
      public void orientationChange( Orientation newOrientation ) {
        label.setText( newOrientation.toString() );
        label.pack();
      }
    } );
  }

  private Label createLabel( Composite parent, String text ) {
    Label label = new Label( parent, SWT.NONE );
    label.setText( text );
    label.setLayoutData( GridDataFactory.fillDefaults().align( SWT.LEFT, SWT.CENTER ).grab( false, true ).create() );
    Label resultLabel = new Label( parent, SWT.NONE );
    resultLabel.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).create() );
    resultLabel.setFont( new Font( label.getDisplay(), new FontData( "Verdana", 16, SWT.BOLD ) ) );
    return resultLabel;
  }

  private Group createGroup( Composite parent, String title ) {
    Group group = new Group( parent, SWT.NONE );
    GridLayout gridLayout = GridLayoutFactory.fillDefaults().numColumns( 2 ).margins( 0, 0 )
                            .spacing( 0, 0 ).equalWidth( false ).create();
    gridLayout.horizontalSpacing = 12;
    gridLayout.verticalSpacing = 4;
    group.setLayout( gridLayout );
    group.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).create() );
    group.setText( title );
    return group;
  }

  private void addResizeListener( final ScrolledComposite scrolledComposite, final Composite container ) {
    container.addControlListener( new ControlAdapter() {
      int storedWidth = 0;

      @Override
      public void controlResized( final ControlEvent e ) {
        updateHeight( scrolledComposite, container );
        container.getShell().layout( true, true );
      }

      private void updateHeight( final ScrolledComposite scrolledComposite, Composite composite ) {
        int newWidth = composite.getSize().x;
        if( newWidth != storedWidth ) {
          scrolledComposite.setMinHeight( composite.computeSize( newWidth, SWT.DEFAULT ).y );
          storedWidth = newWidth;
        }
      }
    } );
  }
}
