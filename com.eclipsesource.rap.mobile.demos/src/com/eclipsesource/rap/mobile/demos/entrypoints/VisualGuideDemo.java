/*******************************************************************************
 * Copyright (c) 2012 EclipseSource and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.rap.mobile.demos.entrypoints;

import org.eclipse.rwt.lifecycle.IEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class VisualGuideDemo implements IEntryPoint {

  private Display display;

  public int createUI() {
    display = new Display();
    createCombo();
    return 0;
  }

  private void createBrowser() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    final Browser browser = new Browser( shell, SWT.NONE );
    browser.setUrl( "http://www.eclipse.org" );
    shell.open();
  }

  private void createButtonCheck() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    final Button buttonCheck = new Button( shell, SWT.CHECK );
    buttonCheck.setText( "One" );
    shell.open();
  }

  private void createButtonPush() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    RowLayout layout = new RowLayout();
    layout.marginTop = 23;
    shell.setLayout( layout );
    final Button buttonCheck = new Button( shell, SWT.PUSH );
    buttonCheck.setText( "One" );
    shell.open();
  }

  private void createButtonRadio() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    RowLayout layout = new RowLayout( SWT.HORIZONTAL );
    layout.marginTop = 23;
    shell.setLayout( layout );
    final Button buttonRadio1 = new Button( shell, SWT.RADIO );
    buttonRadio1.setText( "One" );
    final Button buttonRadio2 = new Button( shell, SWT.RADIO );
    buttonRadio2.setText( "Two" );
    final Button buttonRadio3 = new Button( shell, SWT.RADIO );
    buttonRadio3.setText( "Three" );
    shell.open();
  }

  private void createButtonToggle() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    RowLayout layout = new RowLayout( SWT.HORIZONTAL );
    layout.marginTop = 23;
    shell.setLayout( layout );
    final Button buttonRadio1 = new Button( shell, SWT.TOGGLE );
    buttonRadio1.setText( "One" );
    final Button buttonRadio2 = new Button( shell, SWT.TOGGLE );
    buttonRadio2.setText( "Two" );
    final Button buttonRadio3 = new Button( shell, SWT.TOGGLE );
    buttonRadio3.setText( "Three" );
    shell.open();
  }

  private void createCanvas() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    final Canvas canvas = new Canvas( shell, SWT.NONE );
    canvas.addPaintListener( new PaintListener() {

      public void paintControl( final PaintEvent event ) {
        event.gc.setLineWidth( 3 );
        event.gc.drawLine( 20, 120, 120, 20 );
        event.gc.drawLine( 20, 20, 120, 120 );
        event.gc.drawRectangle( 20, 20, 100, 100 );
        event.gc.drawOval( 20, 20, 100, 100 );
      }
    } );
    canvas.redraw();
    shell.open();
  }

  private void createCombo() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    RowLayout layout = new RowLayout();
    layout.marginTop = 120;
    shell.setLayout( layout );
    final Combo combo = new Combo( shell, SWT.NONE );
    combo.setItems( new String[]{
      "Cat", "House", "Sky", "Sun"
    } );
    combo.select( 1 );
    shell.open();
  }

  private void createComposite() {
    final Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    final Composite compo = new Composite( shell, SWT.NONE );
    shell.open();
  }
}
