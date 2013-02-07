/*
 * Copyright(c) 2012 EclipseSource. All Rights Reserved.
 */

package com.eclipsesource.tabris.demos.ui;

import static com.eclipsesource.tabris.demos.ui.ImageUtil.*;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

@SuppressWarnings("serial")
public class BooksLabelProvider implements ITableLabelProvider {

  public Image getColumnImage( Object element, int columnIndex ) {
    if( element instanceof Book ) {
      return resizeImageToHeight( ( ( Book )element ).getImage(), 48 );
    }
    return null;
  }

  public String getColumnText( Object element, int columnIndex ) {
    if( columnIndex == 0 ) {
      if( element instanceof Book ) {
        return ( ( Book )element ).getTitle();
      }
    } else if( columnIndex == 1 ) {
      if( element instanceof Book ) {
        return ( ( Book )element ).getAuthor();
      }
    }
    return null;
  }

  public void addListener( ILabelProviderListener listener ) {
    // nothing to do here
  }

  public void dispose() {
    // nothing to do here
  }

  public boolean isLabelProperty( Object element, String property ) {
    // nothing to do here
    return false;
  }

  public void removeListener( ILabelProviderListener listener ) {
    // nothing to do here
  }

}