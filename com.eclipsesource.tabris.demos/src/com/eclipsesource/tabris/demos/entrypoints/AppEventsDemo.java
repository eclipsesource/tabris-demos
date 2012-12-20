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


import static com.eclipsesource.tabris.widgets.enhancement.Widgets.onText;
import static com.eclipsesource.tabris.widgets.enhancement.Widgets.onToolItem;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.eclipsesource.tabris.event.App;
import com.eclipsesource.tabris.event.AppEvent;
import com.eclipsesource.tabris.event.AppListener;
import com.eclipsesource.tabris.event.EventType;


public class AppEventsDemo implements EntryPoint {

  private Composite passwordContainer;
  private Composite contentContainer;
  private StackLayout stack;

  public int createUI() {
    Display display = new Display();
    Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    stack = new StackLayout();
    shell.setLayout( stack );
    shell.setBackground( display.getSystemColor( SWT.COLOR_WHITE ) );
    createContent( shell );
    attachAppEventListener( shell );
    shell.open();
    shell.setVisible( true );
    return 0;
  }

  private void createContent( Shell shell ) {
    passwordContainer = createPasswordContainer( shell );
    contentContainer = createContentContainer( shell );
    stack.topControl = contentContainer;
  }

  private void attachAppEventListener( final Shell shell ) {
    App app = RWT.getClient().getService( App.class );
    if( app != null ) {
      app.addListener( EventType.PAUSE, new AppListener() {
        public void handleEvent( AppEvent event ) {
          if( event.getType() == EventType.PAUSE ) {
            stack.topControl = passwordContainer;
            shell.layout( true );
          }
        }
      } );
    }
  }

  private Composite createPasswordContainer( Shell shell ) {
    Composite container = new Composite( shell, SWT.NONE );
    container.setBackground( new Color( shell.getDisplay(), 100, 100, 100 ) );
    container.setLayout( GridLayoutFactory.fillDefaults().create() );
    Label label = new Label( container, SWT.NONE );
    label.setLayoutData( GridDataFactory.fillDefaults().grab( true, true ).align( SWT.CENTER, SWT.CENTER ).create() );
    label.setImage( new Image( shell.getDisplay(), AppEventsDemo.class.getResourceAsStream( "/dontPanic.png" ) ) );
    Text password = new Text( container, SWT.SINGLE | SWT.LEAD | SWT.BORDER );
    password.setLayoutData( GridDataFactory.fillDefaults().grab( true, true ).align( SWT.CENTER, SWT.TOP ).create() );
    onText( password ).useDecimalKeyboard();
    password.setMessage( "To get back your secrets tell me what's the meaning of life, the universe and everything?" );
    password.addModifyListener( createPasswordSelectionListener( password ) );
    return container;
  }

  private ModifyListener createPasswordSelectionListener( final Text password ) {
    return new ModifyListener() {
      
      public void modifyText( ModifyEvent event ) {
        if( "42".equals( password.getText() ) ) {
          password.setText( "" );
          stack.topControl = contentContainer;
          password.getShell().layout( true );
          contentContainer.setFocus();
        }
      }
    };
  }

  private Composite createContentContainer( final Shell shell ) {
    Composite container = new Composite( shell, SWT.NONE );
    container.setBackground( new Color( shell.getDisplay(), 187, 234, 14 ) );
    container.setLayout( new FormLayout() );
    Image bgImage = new Image( shell.getDisplay(), AppEventsDemo.class.getResourceAsStream( "/star-bg.jpg" ) );
    container.setBackgroundImage( bgImage );
    createToolBar( container );
    createLabel( shell, container );
    Composite textParent = createTextParent( container );
    createText( shell, textParent );
    return container;
  }

  private void createToolBar( Composite container ) {
    ToolBar toolBar = new ToolBar( container, SWT.NONE );
    ToolItem title = new ToolItem( toolBar, SWT.NONE );
    title.setText( "Welcome Hitchhiker!" );
    onToolItem( title ).useAsTitle();
    FormData toolBarLayoutData = new FormData();
    toolBarLayoutData.left = new FormAttachment( 0, 0 );
    toolBarLayoutData.right = new FormAttachment( 100, 0 );
    toolBar.setLayoutData( toolBarLayoutData );
  }

  private void createLabel( final Shell shell, Composite container ) {
    Label label = new Label( container, SWT.WRAP );
    label.setForeground( new Color( shell.getDisplay(), 255, 255, 255 ) );
    FormData labelLayoutData = new FormData();
    labelLayoutData.left = new FormAttachment( 10, 0 );
    labelLayoutData.top = new FormAttachment( 25, 0 );
    labelLayoutData.right = new FormAttachment( 90, 0 );
    label.setLayoutData( labelLayoutData );
    label.setText( "Tell me your secrets and suspend/resume this app!" );
    FontDescriptor descriptor = JFaceResources.getFontDescriptor( "Arial" );
    Font font = descriptor.setHeight( 30 ).setStyle( SWT.BOLD ).createFont( shell.getDisplay() );
    label.setFont( font );
  }

  private void createText( final Shell shell, Composite textParent ) {
    Text text = new Text( textParent, SWT.MULTI | SWT.LEAD | SWT.BORDER );
    text.setBackground( new Color( shell.getDisplay(), 255, 255, 255 ) );
    text.setLayoutData( GridDataFactory.fillDefaults().grab( true, true ).align( SWT.FILL, SWT.FILL).create() );
    text.setMessage( "" );
  }

  private Composite createTextParent( Composite container ) {
    Composite textParent = new Composite( container, SWT.NONE );
    FormData textLayoutData = new FormData();
    textLayoutData.left = new FormAttachment( 10, 0 );
    textLayoutData.top = new FormAttachment( 35, 0 );
    textLayoutData.right = new FormAttachment( 90, 0 );
    textLayoutData.bottom = new FormAttachment( 90, 0 );
    textParent.setLayoutData( textLayoutData );
    textParent.setLayout( GridLayoutFactory.fillDefaults().create() );
    return textParent;
  }
}
