/*******************************************************************************
 * Copyright (c) 2013, 2018 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.entrypoints;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.rap.rwt.service.ResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
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
import com.eclipsesource.tabris.camera.CameraListener;
import com.eclipsesource.tabris.camera.CameraOptions;
import com.eclipsesource.tabris.camera.PhotoAlbum;
import com.eclipsesource.tabris.camera.PhotoAlbumListener;
import com.eclipsesource.tabris.camera.PhotoAlbumOptions;
import com.eclipsesource.tabris.widgets.enhancement.Widgets;

public class CameraDemo implements EntryPoint {

  private Label imageLabel;

  @Override
  public int createUI() {
    Display display = new Display();
    final Shell shell = createShell( display );
    createToolBar( shell );
    Composite mainComposite = createMainComp( shell );
    createImageLabel( mainComposite );
    createCameraButton( mainComposite );
    createAlbumButton( mainComposite );
    initCamera();
    initAlbum();
    shell.open();
    return 0;
  }

  private void initCamera() {
    Camera camera = RWT.getClient().getService( Camera.class );
    camera.addCameraListener( new CameraListener() {

      @Override
      public void receivedPicture( Image image ) {
        handleImage( image );
      }
    } );
  }

  private void initAlbum() {
    PhotoAlbum album = RWT.getClient().getService( PhotoAlbum.class );
    album.addPhotoAlbumListener( new PhotoAlbumListener() {

      @Override
      public void receivedImage( Image image ) {
        handleImage( image );
      }
    } );
  }
  private void handleImage( Image image ) {
    if( image == null ) {
      imageLabel.setText( "Could not provide image from camera" );
    } else {
      imageLabel.setImage( image );
    }
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
    toolBar.setLayoutData( GridDataFactory.fillDefaults()
      .align( SWT.FILL, SWT.TOP )
      .grab( true, false )
      .create() );
    ToolItem toolItem = new ToolItem( toolBar, SWT.NONE );
    toolItem.setText( "Camera Demo" );
    Widgets.onToolItem( toolItem ).useAsTitle();
  }

  private Composite createMainComp( final Shell shell ) {
    Composite comp = new Composite( shell, SWT.NONE );
    GridLayout compLayout = new GridLayout( 1, false );
    compLayout.horizontalSpacing = 16;
    comp.setLayout( compLayout );
    comp.setLayoutData( GridDataFactory.fillDefaults()
      .align( SWT.FILL, SWT.FILL )
      .grab( true, true )
      .create() );
    return comp;
  }

  private void createImageLabel( Composite comp ) {
    imageLabel = new Label( comp, SWT.NONE );
    imageLabel.setLayoutData( GridDataFactory.fillDefaults()
      .align( SWT.FILL, SWT.FILL )
      .grab( true, true )
      .create() );
  }

  private void createCameraButton( Composite parent ) {
    Button cameraButton = new Button( parent, SWT.PUSH );
    cameraButton.setText( "Take photo with camera" );
    cameraButton.setLayoutData( GridDataFactory.fillDefaults()
      .align( SWT.FILL, SWT.TOP )
      .grab( true, false )
      .create() );
    cameraButton.addListener( SWT.Selection, new Listener() {

      @Override
      public void handleEvent( Event event ) {
        Camera photoCamera = RWT.getClient().getService( Camera.class );
        photoCamera.takePicture( createCameraOptions() );
      }
    } );
  }

  private CameraOptions createCameraOptions() {
    CameraOptions photosOptions = new CameraOptions();
    Rectangle bounds = imageLabel.getBounds();
    photosOptions.setResolution( bounds.width, bounds.height );
    return photosOptions;
  }

  private void createAlbumButton( Composite parent ) {
    Button albumButton = new Button( parent, SWT.PUSH );
    albumButton.setText( "Choose photo from Album" );
    albumButton.setLayoutData( GridDataFactory.fillDefaults()
                                .align( SWT.FILL, SWT.TOP )
                                .grab( true, false )
                                .create() );
    albumButton.addListener( SWT.Selection, new Listener() {

      @Override
      public void handleEvent( Event event ) {
        PhotoAlbum album = RWT.getClient().getService( PhotoAlbum.class );
        album.open( createPhotoAlbumOptions() );
      }
    } );
  }

  private PhotoAlbumOptions createPhotoAlbumOptions() {
    PhotoAlbumOptions photosOptions = new PhotoAlbumOptions();
    Rectangle bounds = imageLabel.getBounds();
    photosOptions.setResolution( bounds.width, bounds.height );
    return photosOptions;
  }

  String swtImageAsPNGResourceURL( Image image ) {
    ImageLoader loader = new ImageLoader();
    loader.data = new ImageData[] {image.getImageData()};
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    loader.save(outputStream, SWT.IMAGE_PNG);
    ResourceManager manager = RWT.getResourceManager();
    String name = image.hashCode() + ".png";
    manager.register( name, new ByteArrayInputStream( outputStream.toByteArray() ) );
    return manager.getLocation( name );
  }

}
