/*******************************************************************************
 * Copyright (c) 2014 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.ui;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.eclipsesource.tabris.scanner.Scanner;
import com.eclipsesource.tabris.scanner.ScannerAdapter;
import com.eclipsesource.tabris.scanner.ScannerOptions;
import com.eclipsesource.tabris.scanner.ScannerOptions.ScanMode;
import com.eclipsesource.tabris.scanner.ScannerOptions.TypeMode;
import com.eclipsesource.tabris.scanner.ServiceUtil;
import com.eclipsesource.tabris.ui.AbstractPage;
import com.eclipsesource.tabris.ui.PageData;


public class ScanPage extends AbstractPage {

  private final Scanner scanner;

  public ScanPage() {
    scanner = ServiceUtil.getScanner();
  }

  @Override
  public void createContent( Composite parent, PageData data ) {

    ScannerOptions scannerOptions = new ScannerOptions();
    scannerOptions.setScanMode( ScanMode.SINGLE_SCAN );
    scannerOptions.setTypeMode( TypeMode.CAMERA_ALL );
    scanner.activate( scannerOptions );

    parent.setLayout( GridLayoutFactory.fillDefaults().spacing( 5, 5 ).margins( 5, 5 ).create() );



    Button button = new Button( parent, SWT.NONE );
    button.setText( "Scan" );
    GridDataFactory.fillDefaults().grab( true, false ).align( SWT.FILL, SWT.TOP ).applyTo( button );


    final Label label = new Label( parent, SWT.WRAP );
    Font font = FontDescriptor.createFrom( label.getFont() ).setStyle( SWT.BOLD ).setHeight( 25 ).createFont( Display.getCurrent() );
    label.setFont( font );
    GridDataFactory.fillDefaults().grab( true, true ).align( SWT.CENTER, SWT.FILL ).applyTo( label );

    button.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        scanner.addScannerListener( new ScannerAdapter() {

          @Override
          public void receivedCode( String type, String data ) {
            label.setText( "\n\n" + data );
            label.getParent().layout( true, true );
            scanner.removeScannerListener( this );
          }

        } );
        scanner.startScan();
      }

    } );

  }
}
