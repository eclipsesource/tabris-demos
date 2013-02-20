/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.entrypoints;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.eclipsesource.tabris.camera.Camera;
import com.eclipsesource.tabris.camera.CameraCallback;
import com.eclipsesource.tabris.camera.CameraOptions;
import com.eclipsesource.tabris.widgets.enhancement.Widgets;

public class CameraDemo implements EntryPoint {

  private Label imageLabel;

  public int createUI() {
    Display display = new Display();
    final Shell shell = createShell( display );
    createToolBar( shell );
    Composite comp = createMainComp( shell );
    createImageLabel( comp );
    createCameraButton( comp, imageLabel );
    shell.open();
    return 0;
  }

  private Shell createShell( Display display ) {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    GridLayout shellLayout = new GridLayout( 1, false );
    shellLayout.marginHeight = 0;
    shellLayout.marginWidth = 0;
    shell.setLayout( shellLayout );
    return shell;
  }

  private void createToolBar( final Composite parent ) {
    ToolBar toolBar = new ToolBar( parent, SWT.NONE );
    toolBar.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).create() );
    ToolItem toolItem = new ToolItem( toolBar, SWT.NONE );
    toolItem.setText( "Camera Demo" );
    Widgets.onToolItem( toolItem ).useAsTitle();
  }

  private Composite createMainComp( final Shell shell ) {
    Composite comp = new Composite( shell, SWT.NONE );
    GridLayout compLayout = new GridLayout( 1, false );
    compLayout.horizontalSpacing = 16;
    comp.setLayout( compLayout );
    comp.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.FILL ).grab( true, true ).create() );
    return comp;
  }

  private Camera createPhotoCamera() {
    CameraOptions photosOptions = new CameraOptions();
    Rectangle bounds = imageLabel.getBounds();
    photosOptions.setResolution( bounds.width, bounds.height );
    return new Camera( photosOptions );
  }

  private void createImageLabel( Composite comp ) {
    imageLabel = new Label( comp, SWT.NONE );
    imageLabel.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.FILL ).grab( true, true ).create() );
  }

  private void createCameraButton( Composite comp, final Label imageLabel ) {
    Button cameraButton = new Button( comp, SWT.PUSH );
    cameraButton.setText( "Take photo with camera" );
    cameraButton.setLayoutData( GridDataFactory.fillDefaults().align( SWT.FILL, SWT.TOP ).grab( true, false ).create() );
    cameraButton.addListener( SWT.Selection, new Listener() {

      public void handleEvent( Event event ) {
        final Camera photoCamera = createPhotoCamera();
        photoCamera.takePicture( new CameraCallback() {

          public void onSuccess( Image image ) {
            imageLabel.setImage( image );
            photoCamera.dispose();
          }

          public void onError() {
            imageLabel.setText( "Could not provide image from camera" );
            photoCamera.dispose();
          }
        } );
      }
    } );
  }

}
