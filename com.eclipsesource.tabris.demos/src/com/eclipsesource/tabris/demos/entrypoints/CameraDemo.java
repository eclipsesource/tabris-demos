package com.eclipsesource.tabris.demos.entrypoints;

import org.eclipse.rap.rwt.lifecycle.IEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
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
import com.eclipsesource.tabris.camera.CameraOptions.SourceType;
import com.eclipsesource.tabris.widgets.Widgets;

public class CameraDemo implements IEntryPoint {

  private CameraOptions photosOptions;
  private Camera photoCamera;
  private Camera galleryCamera;
  private CameraOptions galleryOptions;

  public int createUI() {
    Display display = new Display();
    final Shell shell = createShell( display );
    createToolBar( shell );
    Composite comp = createMainComp( shell );
    createPhotoCamera();
    createGalleryCamera();
    final Label imageLabel = createImageLabel( comp );
    createCameraButton( comp, imageLabel );
    createGalleryButton( comp, imageLabel );
    shell.open();
    return 0;
  }

  private Shell createShell( Display display ) {
    final Shell shell = new Shell( display, SWT.NONE );
    shell.setMaximized( true );
    GridLayout shellLayout = new GridLayout( 1, false );
    shellLayout.marginHeight = 0;
    shellLayout.marginWidth = 0;
    shell.setLayout( shellLayout );
    return shell;
  }

  private void createToolBar( final Composite parent ) {
    ToolBar toolBar = new ToolBar( parent, SWT.NONE );
    toolBar.setLayoutData( UiUtil.createFillHori() );
    ToolItem toolItem = new ToolItem( toolBar, SWT.NONE );
    toolItem.setText( "Camera Demo" );
    Widgets.onToolItem( toolItem ).useAsTitle();
  }

  private Composite createMainComp( final Shell shell ) {
    Composite comp = new Composite( shell, SWT.NONE );
    GridLayout compLayout = new GridLayout( 1, false );
    compLayout.horizontalSpacing = 16;
    comp.setLayout( compLayout );
    comp.setLayoutData( UiUtil.createFill() );
    return comp;
  }

  private void createGalleryCamera() {
    galleryOptions = new CameraOptions();
    galleryOptions.setSourceType( SourceType.PHOTO_LIBRARY );
    galleryOptions.setResolution( 300, 300 );
    galleryCamera = new Camera( galleryOptions );
  }

  private void createPhotoCamera() {
    photosOptions = new CameraOptions();
    photosOptions.setSourceType( SourceType.CAMERA );
    photosOptions.setResolution( 300, 300 );
    photoCamera = new Camera( photosOptions );
  }

  private Label createImageLabel( Composite comp ) {
    final Label imageLabel = new Label( comp, SWT.NONE );
    imageLabel.setLayoutData( UiUtil.createFill() );
    imageLabel.addControlListener( new ControlAdapter() {

      @Override
      public void controlResized( ControlEvent e ) {
        Rectangle bounds = imageLabel.getBounds();
        photosOptions.setResolution( bounds.width, bounds.height );
        galleryOptions.setResolution( bounds.width, bounds.height );
      }
    } );
    return imageLabel;
  }

  private void createCameraButton( Composite comp, final Label imageLabel ) {
    Button cameraButton = new Button( comp, SWT.PUSH );
    cameraButton.setText( "Take photo with camera" );
    cameraButton.setLayoutData( UiUtil.createFillHori() );
    cameraButton.addListener( SWT.Selection, new Listener() {

      public void handleEvent( Event event ) {
        photoCamera.takePicture( new CameraCallback() {

          public void onSuccess( Image image ) {
            imageLabel.setImage( image );
          }

          public void onError() {
            imageLabel.setText( "Could not provide image from camera" );
          }
        } );
      }
    } );
  }

  private void createGalleryButton( Composite comp, final Label imageLabel ) {
    Button galleryButton = new Button( comp, SWT.PUSH );
    galleryButton.setText( "Select image from gallery" );
    galleryButton.setLayoutData( UiUtil.createFillHori() );
    galleryButton.addListener( SWT.Selection, new Listener() {

      public void handleEvent( Event event ) {
        galleryCamera.takePicture( new CameraCallback() {

          public void onSuccess( Image image ) {
            imageLabel.setImage( image );
          }

          public void onError() {
            imageLabel.setText( "Could not provide image from gallery" );
          }
        } );
      }
    } );
  }
}
