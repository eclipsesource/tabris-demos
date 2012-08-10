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

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.RowLayoutFactory;
import org.eclipse.rwt.lifecycle.IEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.eclipsesource.tabris.widgets.Video;
import com.eclipsesource.tabris.widgets.Video.PlaybackMode;
import com.eclipsesource.tabris.widgets.Video.PresentationMode;
import com.eclipsesource.tabris.widgets.VideoListener;


public class VideoDemo implements IEntryPoint {
  
  public int createUI() {
    Display display = new Display();
    Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    shell.setBackground( display.getSystemColor( SWT.COLOR_BLACK ) );
    createContent( display, shell );
    shell.open();
    shell.setVisible( true );
    return 0;
  }

  private void createContent( Display display, Shell shell ) {
    Composite container = new Composite( shell, SWT.NONE );
    container.setLayout( GridLayoutFactory.fillDefaults().numColumns( 1 ).create() );
    
    Video video = new Video( "https://dl.dropbox.com/s/m6fr5hwaee2bgap/Spherikal.mp4", container );
    video.setLayoutData( GridDataFactory.fillDefaults().grab( true, true ).create() );
    
    Composite controls = new Composite( container, SWT.NONE );
    controls.setLayoutData( GridDataFactory.fillDefaults().grab( true, false ).create() );
    hookControlsAndVideo( video, controls );
  }

  private void hookControlsAndVideo( final Video video, Composite controls ) {
    controls.setLayout( RowLayoutFactory.fillDefaults().create() );
    final Button playButton = createPlayButton( video, controls );
    final Button pauseButton = createPauseButton( video, controls );
    final Button stopButton = createStopButton( video, controls );
    final Button backwardButton = createBackwardButton( video, controls );
    final Button forwardButton = createForwardButton( video, controls );
    final Button fullscreenButton = createFullscreenButton( video, controls );
    final Button showControlsButton = createShowControlsButton( video, controls ); 
    pauseButton.setEnabled( false );
    stopButton.setEnabled( false );
    video.addVideoListener( new VideoListener() {
      
      public void presentationChanged( PresentationMode newMode ) {
        if( newMode == PresentationMode.EMBEDDED ) {
          fullscreenButton.setEnabled( true );
        } else {
          fullscreenButton.setEnabled( false );
        }
      }
      
      public void playbackChanged( PlaybackMode newMode ) {
        if( newMode == PlaybackMode.PLAY ) {
          playButton.setEnabled( false );
          pauseButton.setEnabled( true );
          stopButton.setEnabled( true );
          forwardButton.setEnabled( true );
          backwardButton.setEnabled( true );
        } else if( newMode == PlaybackMode.PAUSE || newMode == PlaybackMode.STOP ) {
          playButton.setEnabled( true );
          pauseButton.setEnabled( false );
          stopButton.setEnabled( false );
          forwardButton.setEnabled( true );
          backwardButton.setEnabled( true );
        } else if( newMode == PlaybackMode.FAST_FORWARD ) {
          playButton.setEnabled( true );
          pauseButton.setEnabled( true );
          stopButton.setEnabled( true );
          forwardButton.setEnabled( false );
          backwardButton.setEnabled( true );
        } else if( newMode == PlaybackMode.FAST_BACKWARD ) {
          playButton.setEnabled( true );
          pauseButton.setEnabled( true );
          stopButton.setEnabled( true );
          forwardButton.setEnabled( true );
          backwardButton.setEnabled( false );
        }
      }
    } );
  }

  private Button createPlayButton( final Video video, Composite controls ) {
    final Button playButton = new Button( controls, SWT.PUSH );
    playButton.setText( "play" );
    playButton.addSelectionListener( new SelectionAdapter() {
      public void widgetSelected( SelectionEvent e ) {
        video.play();
      }
    } );
    return playButton;
  }

  private Button createPauseButton( final Video video, Composite controls ) {
    final Button pauseButton = new Button( controls, SWT.PUSH );
    pauseButton.setText( "pause" );
    pauseButton.addSelectionListener( new SelectionAdapter() {
      public void widgetSelected( SelectionEvent e ) {
        video.pause();
      }
    } );
    return pauseButton;
  }

  private Button createStopButton( final Video video, Composite controls ) {
    final Button stopButton = new Button( controls, SWT.PUSH );
    stopButton.setText( "stop" );
    stopButton.addSelectionListener( new SelectionAdapter() {
      public void widgetSelected( SelectionEvent e ) {
        video.stop();
      }
    } );
    return stopButton;
  }

  private Button createBackwardButton( final Video video, Composite controls ) {
    final Button backwardButton = new Button( controls, SWT.PUSH );
    backwardButton.setText( "backward" );
    backwardButton.addSelectionListener( new SelectionAdapter() {
      public void widgetSelected( SelectionEvent e ) {
        video.fastBackward( -2 );
      }
    } );
    return backwardButton;
  }

  private Button createForwardButton( final Video video, Composite controls ) {
    final Button forwardButton = new Button( controls, SWT.PUSH );
    forwardButton.setText( "forward" );
    forwardButton.addSelectionListener( new SelectionAdapter() {
      public void widgetSelected( SelectionEvent e ) {
        video.fastForward( 2 );
      }
    } );
    return forwardButton;
  }

  private Button createFullscreenButton( final Video video, Composite controls ) {
    final Button fullscreenButton = new Button( controls, SWT.PUSH );
    fullscreenButton.setText( "fullscreen" );
    fullscreenButton.addSelectionListener( new SelectionAdapter() {
      public void widgetSelected( SelectionEvent e ) {
        video.setFullscreen( true );
        fullscreenButton.setEnabled( false );
      }
    } );
    return fullscreenButton;
  }

  private Button createShowControlsButton( final Video video, Composite controls ) {
    final Button fullscreenButton = new Button( controls, SWT.CHECK );
    fullscreenButton.setText( "Controls Visible" );
    fullscreenButton.setSelection( true );
    fullscreenButton.addSelectionListener( new SelectionAdapter() {
      public void widgetSelected( SelectionEvent e ) {
        video.setPlayerControlsVisible( fullscreenButton.getSelection() );
      }
    } );
    return fullscreenButton;
  }
}
