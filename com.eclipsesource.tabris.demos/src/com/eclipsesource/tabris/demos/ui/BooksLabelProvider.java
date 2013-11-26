/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.demos.ui;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;


public class BooksLabelProvider implements ITableLabelProvider {

  public Image getColumnImage( Object element, int columnIndex ) {
    if( element instanceof Book ) {
      return resizeImageToHeight( ( ( Book )element ).getImage(), 48 );
    }
    return null;
  }

  private static Image resizeImageToHeight( Image image, int height ) {
    float ratio = ( float )image.getBounds().height / ( float )image.getBounds().width;
    int width = Math.round( height / ratio );
    return new Image( image.getDevice(), image.getImageData().scaledTo( width, height ) );
  }

  public String getColumnText( Object element, int columnIndex ) {
    String result = null;
    if( columnIndex == 1 ) {
      if( element instanceof Book ) {
        result = ( ( Book )element ).getTitle();
      }
    } else if( columnIndex == 2 ) {
      if( element instanceof Book ) {
        result = ( ( Book )element ).getAuthor();
      }
    }
    return result;
  }

  public void addListener( ILabelProviderListener listener ) {
    // nothing to do here
  }

  public void dispose() {
    // nothing to do here
  }

  public boolean isLabelProperty( Object element, String property ) {
    return false;
  }

  public void removeListener( ILabelProviderListener listener ) {
    // nothing to do here
  }
}