/*
 * "*****************************************************************************
 * * Copyright (c) 2012 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 * ****************************************************************************
 */
package com.eclipsesource.tabris.demos.entrypoints;

import static com.eclipsesource.tabris.widgets.enhancement.Widgets.onToolItem;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.RowLayoutFactory;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.eclipsesource.tabris.widgets.PlaybackListener;
import com.eclipsesource.tabris.widgets.PresentationListener;
import com.eclipsesource.tabris.widgets.Video;
import com.eclipsesource.tabris.widgets.Video.Playback;
import com.eclipsesource.tabris.widgets.Video.Presentation;

public class VideoDemo implements EntryPoint {

  private static final String videoUrl = "http://mirrorblender.top-ix.org/movies/sintel-1024-surround.mp4";

  public int createUI() {
    Display display = new Display();
    Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    GridLayoutFactory.fillDefaults().applyTo( shell );
    createToolBar( shell );
    createContent( shell );
    shell.open();
    return 0;
  }

  private void createToolBar( Composite parent ) {
    ToolBar toolBar = new ToolBar( parent, SWT.NONE );
    GridDataFactory.fillDefaults().grab( true, false ).align( SWT.FILL, SWT.TOP ).applyTo( toolBar );
    
    ToolItem title = new ToolItem( toolBar, SWT.NONE );
    onToolItem( title ).useAsTitle();
    title.setText( "Video" );
  }


  private void createContent( Composite parent ) {
    Composite content = new Composite( parent, SWT.NONE );
    GridDataFactory.fillDefaults().grab( true, true ).align( SWT.FILL, SWT.FILL ).applyTo( content );
    GridLayoutFactory.fillDefaults().applyTo( content );
    
    Video video = new Video( videoUrl, content );
    GridDataFactory.fillDefaults().grab( true, true ).applyTo( video );
    
    Composite controls = new Composite( content, SWT.NONE );
    GridDataFactory.fillDefaults().grab( true, false ).applyTo( controls );
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
    createShowControlsButton( video, controls );
    createRepeatButton( video, controls );
    pauseButton.setEnabled( false );
    stopButton.setEnabled( false );
    video.addPresentationListener( new PresentationListener() {

      public void presentationChanged( Presentation newMode ) {
        if( newMode == Presentation.EMBEDDED ) {
          fullscreenButton.setEnabled( true );
        } else {
          fullscreenButton.setEnabled( false );
        }
      }
    } );
    video.addPlaybackListener( new PlaybackListener() {

      public void playbackChanged( Playback newMode ) {
        if( newMode == Playback.PLAY ) {
          playButton.setEnabled( false );
          pauseButton.setEnabled( true );
          stopButton.setEnabled( true );
          forwardButton.setEnabled( true );
          backwardButton.setEnabled( true );
        } else if( newMode == Playback.PAUSE || newMode == Playback.STOP ) {
          playButton.setEnabled( true );
          pauseButton.setEnabled( false );
          stopButton.setEnabled( false );
          forwardButton.setEnabled( true );
          backwardButton.setEnabled( true );
        } else if( newMode == Playback.FAST_FORWARD ) {
          playButton.setEnabled( true );
          pauseButton.setEnabled( true );
          stopButton.setEnabled( true );
          forwardButton.setEnabled( false );
          backwardButton.setEnabled( true );
        } else if( newMode == Playback.FAST_BACKWARD ) {
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

      @Override
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

      @Override
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

      @Override
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

      @Override
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

      @Override
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

      @Override
      public void widgetSelected( SelectionEvent e ) {
        video.setFullscreen( true );
        fullscreenButton.setEnabled( false );
      }
    } );
    return fullscreenButton;
  }

  private Button createShowControlsButton( final Video video, Composite controls ) {
    final Button fullscreenButton = new Button( controls, SWT.CHECK );
    fullscreenButton.setText( "Controls" );
    fullscreenButton.setSelection( true );
    fullscreenButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        video.setPlayerControlsVisible( fullscreenButton.getSelection() );
      }
    } );
    return fullscreenButton;
  }

  private Button createRepeatButton( final Video video, Composite controls ) {
    final Button button = new Button( controls, SWT.CHECK );
    button.setText( "Repeat" );
    button.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        video.setRepeat( button.getSelection() );
      }
    } );
    return button;
  }
}
