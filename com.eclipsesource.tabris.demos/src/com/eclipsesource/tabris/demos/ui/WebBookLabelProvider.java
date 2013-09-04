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

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

final class WebBookLabelProvider extends LabelProvider {

  @Override
  public String getText( Object element ) {
    if( element instanceof Book ) {
      Book book = ( Book )element;
      return "<b>" + book.getTitle() + "</b><br/>" + book.getAuthor();
    }
    return "";
  }

  @Override
  public Image getImage( Object element ) {
    if( element instanceof Book ) {
      return resizeImageToHeight( ( ( Book )element ).getImage(), 48 );
    }
    return null;
  }

  private Image resizeImageToHeight( Image image, int height ) {
    float ratio = ( float )image.getBounds().height / ( float )image.getBounds().width;
    int width = Math.round( height / ratio );
    return new Image( image.getDevice(), image.getImageData().scaledTo( width, height ) );
  }
}