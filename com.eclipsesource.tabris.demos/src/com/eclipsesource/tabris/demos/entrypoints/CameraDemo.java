/*******************************************************************************
 * Copyright (c) 2012 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.entrypoints;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.rap.rwt.lifecycle.IEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.eclipsesource.tabris.camera.Camera;
import com.eclipsesource.tabris.camera.CameraCallback;
import com.eclipsesource.tabris.camera.CameraOptions;
import com.eclipsesource.tabris.camera.CameraOptions.SourceType;

public class CameraDemo implements IEntryPoint {

  public int createUI() {
    Display display = new Display();
    Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( GridLayoutFactory.fillDefaults().numColumns( 1 ).create() );
    shell.setBackground( display.getSystemColor( SWT.COLOR_BLACK ) );
    createContent( shell );
    shell.open();
    return 0;
  }

  private void createContent( Shell shell ) {
    Button pictureButton = new Button( shell, SWT.PUSH );
    pictureButton.setLayoutData( GridDataFactory.fillDefaults().grab( true, false ).create() );
    pictureButton.setText( "Take Photo" );
    final Label imageLabel = new Label( shell, SWT.NONE );
    imageLabel.setLayoutData( GridDataFactory.fillDefaults().grab( true, true ).create() );
    imageLabel.setBackground( shell.getDisplay().getSystemColor( SWT.COLOR_GREEN ) );
    CameraOptions options = new CameraOptions();
    options.setSourceType( SourceType.PHOTO_LIBRARY );
    options.setResolution( 200, 200 );
    final Camera camera = new Camera( options );
    pictureButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        camera.takePicture( new CameraCallback() {

          public void onSuccess( Image image ) {
            imageLabel.setImage( image );
            imageLabel.pack();
          }

          public void onError() {
            System.out.println( "Uups, couldnt take picture" );
          }
        } );
      }
    } );
  }
}
