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

import static com.eclipsesource.tabris.widgets.enhancement.Widgets.onToolItem;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.rap.rwt.widgets.DialogUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.eclipsesource.tabris.demos.swipe.DictionarySwipeItemProvider;
import com.eclipsesource.tabris.device.ClientDevice;
import com.eclipsesource.tabris.device.ClientDevice.Platform;
import com.eclipsesource.tabris.widgets.swipe.Swipe;

public class SwipeDemo implements EntryPoint {

  public int createUI() {
    Display display = new Display();
    final Shell shell = createShell( display );
    ClientDevice device = RWT.getClient().getService( ClientDevice.class );
    if( device != null && device.getPlatform() != Platform.WEB ) {
      createToolBar( shell );
      Composite container = createParentComposite( shell );
      createSwipeWidget( container );
    } else {
      createWebClientContent( shell );
    }
    shell.open();
    return 0;
  }

  private Composite createParentComposite( final Shell shell ) {
    Composite comp = new Composite( shell, SWT.NONE );
    GridLayout compLayout = new GridLayout( 1, false );
    compLayout.marginWidth = 16;
    compLayout.horizontalSpacing = 16;
    comp.setLayout( compLayout );
    comp.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.FILL ).grab( true, true ).create() );
    return comp;
  }

  private Shell createShell( Display display ) {
    final Shell shell = new Shell( display, SWT.NONE );
    shell.setMaximized( true );
    GridLayoutFactory.fillDefaults().applyTo( shell );
    return shell;
  }

  private void createToolBar( final Composite parent ) {
    ToolBar toolBar = new ToolBar( parent, SWT.NONE );
    toolBar.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).create() );
    ToolItem toolItem = new ToolItem( toolBar, SWT.NONE );
    toolItem.setText( "Klingon Lessons" );
    onToolItem( toolItem ).useAsTitle();
  }

  private void createSwipeWidget( Composite parent ) {
    Swipe result = new Swipe( parent, new DictionarySwipeItemProvider() );
    GridDataFactory.fillDefaults()
      .grab( true, true )
      .align( SWT.FILL, SWT.FILL )
      .applyTo( result.getControl() );
    result.show( 0 );
  }

  private void createWebClientContent( final Shell shell ) {
    MessageBox messageBox = new MessageBox( shell, SWT.ICON_WARNING );
    messageBox.setMessage( "This demo is availaible on mobile devices only." );
    messageBox.setText( "Tabris Demo" );
    shell.open();
    DialogUtil.open( messageBox, null );
  }
}