/*
 * "****************************************************************************
 * * * Copyright (c) 2012 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 * ****************************************************************************
 */
package com.eclipsesource.tabris.demos.entrypoints;

import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.eclipsesource.tabris.widgets.PlaybackListener;
import com.eclipsesource.tabris.widgets.Video;
import com.eclipsesource.tabris.widgets.Video.PlayerState;

public class VideoDemo implements EntryPoint {

  private static final String videoUrl = "http://peach.themazzone.com/durian/movies/sintel-1280-stereo.mp4";

  @Override
  public int createUI() {
    Display display = new Display();
    Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    GridLayout layout = new GridLayout( 1, false );
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    shell.setLayout( layout );
    final Video video = new Video( shell, videoUrl );
    video.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    video.setSpeed( Video.PLAY_SPEED );
    video.addPlaybackListener( new PlaybackListener() {

      @Override
      public void playbackChanged( PlayerState state ) {
        System.out.println( "New playback state: " + state );
        System.out.println( "Playback speed: " + video.getSpeed() );
      }
    } );
    createButtons( shell, video );
    shell.open();
    return 0;
  }

  private void createButtons( Shell shell, final Video video ) {
    Composite composite = new Composite( shell, SWT.NONE );
    composite.setLayout( new GridLayout( 3, true ) );
    composite.setLayoutData( new GridData( SWT.FILL, SWT.BOTTOM, true, false ) );
    createButton( composite, "Play" ).addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        video.setSpeed( 1f );
      }
    } );
    createButton( composite, "Pause" ).addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        video.setSpeed( 0f );
      }
    } );
    createButton( composite, "Controls" ).addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        video.setPlayerControlsVisible( !video.hasPlayerControlsVisible() );
      }
    } );
    createButton( composite, "Step to 3:00" ).addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        video.stepToTime( 3 * 60 );
      }
    } );
    createButton( composite, "Skip -20 sec" ).addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        video.skipFromCurrent( -20 );
      }
    } );
    createButton( composite, "Skip + 30 sec" ).addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        video.skipFromCurrent( 30 );
      }
    } );
  }

  private Button createButton( Composite composite, String text ) {
    Button buttonPlay = new Button( composite, SWT.PUSH );
    buttonPlay.setText( text );
    buttonPlay.setLayoutData( new GridData( SWT.FILL, SWT.TOP, true, false ) );
    return buttonPlay;
  }
}
