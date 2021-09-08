/*******************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.entrypoints;

import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.eclipsesource.tabris.widgets.LoadListener;
import com.eclipsesource.tabris.widgets.PdfView;

public class PdfDemo implements EntryPoint {

  private static final String PDF_URL = "https://tabrisjs.com/downloads/ebook/tabrisjs-3.5.0.pdf";

  @Override
  public int createUI() {
    Display display = new Display();
    Shell shell = new Shell( display, SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    PdfView pdf = new PdfView( shell );
    pdf.setBackground( new Color( display, 239, 239, 239 ) );
    pdf.setZoomEnabled( true );
    pdf.setPageElevation( 4 );
    pdf.setSpacing( 16 );
    pdf.setViewPadding( 24, 16, 24, 16 );
    pdf.addLoadListener( new LoadListener() {
      @Override
      public void loadSucceeded() {
        System.out.println( "PDF loaded successfully" );
      }
      @Override
      public void loadFailed() {
        System.out.println( "Failed to load PDF Document" );
      }
    } );
    pdf.setUrl( PDF_URL );
    shell.open();
    return 0;
  }

}
