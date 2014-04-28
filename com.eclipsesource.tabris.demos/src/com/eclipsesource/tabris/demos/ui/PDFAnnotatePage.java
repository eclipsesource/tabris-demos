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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.eclipsesource.tabris.ui.AbstractPage;
import com.eclipsesource.tabris.ui.PageData;
import com.eclipsesource.tabris.widget.pdf.PDF;
import com.eclipsesource.tabris.widget.pdf.PDF.Mode;
import com.eclipsesource.tabris.widget.pdf.PDFChangeAdapter;


public class PDFAnnotatePage extends AbstractPage {

  private PDF pdf;

  @Override
  public void createContent( Composite parent, PageData data ) {
    parent.setLayout( new FillLayout() );
    pdf = new PDF( parent, SWT.VERTICAL );
    pdf.setMode( Mode.ANNOTATE );
    pdf.setChangeHandler( new PDFChangeAdapter() {

      @Override
      public void changed( InputStream pdf ) {
        FileOutputStream outputStream;
        try {
          outputStream = new FileOutputStream( new File( "/Users/holger/Desktop/markant-demo/annotated-pdf.pdf" ) );
          int read = 0;
          byte[] bytes = new byte[ 1024 ];
          while( ( read = pdf.read( bytes ) ) != -1 ) {
            outputStream.write( bytes, 0, read );
          }
          pdf.close();
          outputStream.close();
        } catch( IOException e ) {
          e.printStackTrace();
        }
      }

    } );
    pdf.setUrl( "http://marvin.local/book.pdf" );
  }

  public PDF getPDF() {
    return pdf;
  }
}
