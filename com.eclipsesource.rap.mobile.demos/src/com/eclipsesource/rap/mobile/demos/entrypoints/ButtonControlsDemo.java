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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.eclipsesource.rap.mobile.demos.ExampleUtil;

public class ButtonControlsDemo implements IEntryPoint {

  private Label sideLabel;

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
    Composite parent = new Composite( shell, SWT.NONE );
    parent.setBackground( display.getSystemColor( SWT.COLOR_BLACK ) );
    parent.setLayout( ExampleUtil.createGridLayout( 2, true, 15, 5 ) );
    GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, false );
    layoutData.horizontalSpan = 2;
    createWinterSummerButtons( display, parent, layoutData );
    createDirectionButtons( display, parent, layoutData );
    createCheckButton( display, parent );
  }

  private void createCheckButton( Display display, Composite parent ) {
    Composite container2 = new Composite( parent, SWT.NONE );
    GridData container2Data = new GridData( SWT.FILL, SWT.FILL, true, false );
    container2Data.horizontalSpan = 2;
    container2Data.verticalIndent = 30;
    container2.setForeground( display.getSystemColor( SWT.COLOR_WHITE ) );
    container2.setBackground( display.getSystemColor( SWT.COLOR_DARK_GREEN ) );
    container2.setLayoutData( container2Data );
    container2.setLayout( ExampleUtil.createGridLayout( 1, true, 15, 5 ) );
    final Button check = new Button( container2, SWT.CHECK );
    check.setForeground( display.getSystemColor( SWT.COLOR_WHITE ) );
    check.setText( "From Kings Landing?" );
    check.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        if( check.getSelection() ) {
          check.setText( "The wall is far away!" );
        } else {
          check.setText( "From Kings Landing?" );
        }
      }
    } );
  }

  private void createDirectionButtons( Display display, Composite parent, GridData layoutData ) {
    Composite container = new Composite( parent, SWT.NONE );
    container.setBackground( display.getSystemColor( SWT.COLOR_WHITE ) );
    GridData gridData = new GridData( SWT.FILL, SWT.FILL, true, false );
    gridData.horizontalSpan = 2;
    gridData.verticalIndent = 30;
    container.setLayoutData( gridData );
    container.setLayout( ExampleUtil.createGridLayout( 2, false, 5, 5 ) );
    Button northButton = new Button( container, SWT.RADIO );
    northButton.setForeground( display.getSystemColor( SWT.COLOR_BLACK ) );
    northButton.setText( "North" );
    Button eastButton = new Button( container, SWT.RADIO );
    eastButton.setForeground( display.getSystemColor( SWT.COLOR_BLACK ) );
    eastButton.setText( "East" );
    Button southButton = new Button( container, SWT.RADIO );
    southButton.setForeground( display.getSystemColor( SWT.COLOR_BLACK ) );
    southButton.setText( "South" );
    Button westButton = new Button( container, SWT.RADIO );
    westButton.setForeground( display.getSystemColor( SWT.COLOR_BLACK ) );
    westButton.setText( "West" );
    sideLabel = new Label( container, SWT.NONE );
    sideLabel.setLayoutData( layoutData );
    sideLabel.setForeground( display.getSystemColor( SWT.COLOR_BLACK ) );
    addSelectionListener( northButton );
    addSelectionListener( eastButton );
    addSelectionListener( southButton );
    addSelectionListener( westButton );
    northButton.setSelection( true );
    Event event = new Event();
    event.widget = northButton;
    northButton.notifyListeners( SWT.Selection, event );
  }

  private void addSelectionListener( Button button ) {
    SelectionAdapter selectionAdapter = new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        Button widget = ( Button )e.widget;
        sideLabel.setText( "Arrh, a man from the " + widget.getText() );
      }
    };
    button.addSelectionListener( selectionAdapter );
  }

  private void createWinterSummerButtons( Display display, Composite parent, GridData layoutData ) {
    final Label label = new Label( parent, SWT.NONE );
    label.setForeground( display.getSystemColor( SWT.COLOR_WHITE ) );
    label.setText( "Winter is coming!" );
    FontData fontData = new FontData();
    fontData.setStyle( SWT.BOLD );
    fontData.setHeight( 24 );
    label.setFont( new Font( display, fontData ) );
    GridData labelLayoutData = new GridData( SWT.CENTER, SWT.FILL, true, false );
    labelLayoutData.horizontalSpan = 2;
    label.setLayoutData( labelLayoutData );
    final Button winterButton = new Button( parent, SWT.PUSH );
    winterButton.setBackground( new Color( display, new RGB( 215, 215, 215 ) ) );
    winterButton.setForeground( display.getSystemColor( SWT.COLOR_WHITE ) );
    winterButton.setText( "Winter" );
    winterButton.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
    final Button summerButton = new Button( parent, SWT.PUSH );
    summerButton.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );
    summerButton.setBackground( new Color( display, new RGB( 255, 191, 47 ) ) );
    summerButton.setForeground( display.getSystemColor( SWT.COLOR_WHITE ) );
    summerButton.setText( "Summer" );
    winterButton.setEnabled( false );
    winterButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        label.setText( "Winter is coming!" );
        winterButton.setEnabled( false );
        summerButton.setEnabled( true );
        label.getParent().layout( true, true );
      }
    } );
    summerButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        label.setText( "And it's summer again..." );
        winterButton.setEnabled( true );
        summerButton.setEnabled( false );
        label.getParent().layout( true, true );
      }
    } );
  }
}
